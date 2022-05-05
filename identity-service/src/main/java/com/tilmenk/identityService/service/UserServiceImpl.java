package com.tilmenk.identityService.service;

import com.tilmenk.identityService.model.User;
import com.tilmenk.identityService.repository.RoleRepo;
import com.tilmenk.identityService.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User saveUser(User user) throws IllegalStateException {
        Optional<User> userWithSameEmailInDb =
                userRepo.findByEmail(user.getEmail());
        if (userWithSameEmailInDb.isPresent()) {
            log.error("User with email {} already exists",
                    userWithSameEmailInDb.get().getEmail());
            throw new IllegalStateException("User with given email already " + "exists");
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(List.of(roleRepo.findByName("user_default").get()));
            return userRepo.save(user);
        }
    }

    @Override
    public User getUser(String email) {
        return userRepo.findByEmail(email).get();
    }

    @Override
    public List<User> getUsers() {
        return userRepo.findAll();
    }

}
