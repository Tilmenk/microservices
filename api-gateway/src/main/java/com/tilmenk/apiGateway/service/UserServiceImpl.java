package com.tilmenk.apiGateway.service;

import com.tilmenk.apiGateway.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private static final String IDENTITYSERVICE_URL =
            "http" + "://localhost" + ":8086/api";


    private final WebClient identityServiceClient;

    @Autowired
    public UserServiceImpl(WebClient.Builder clientBuilder) {
        this.identityServiceClient =
                clientBuilder.baseUrl(IDENTITYSERVICE_URL).build();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User _user =
                identityServiceClient.get().uri("/user/" + email).retrieve().bodyToMono(User.class).block();
        if (_user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the " +
                    "database");
        } else {
            User user = _user;
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            });
            log.info("User found in the database: {}", email);
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
        }

    }

    @Override
    public User saveUser(User user) {
        return identityServiceClient.post().uri("/user").body(BodyInserters.fromPublisher(Mono.just(user), User.class)).retrieve().bodyToMono(User.class).block();
    }
}
