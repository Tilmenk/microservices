package com.tilmenk.identityService.service;

import com.tilmenk.identityService.model.Role;
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
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoleServiceImpl implements RoleService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Role saveRole(Role role) throws IllegalStateException {
        Optional<Role> roleInDb = roleRepo.findByName(role.getName());
        if (roleInDb.isEmpty()) {
            return roleRepo.save(role);
        } else {
            throw new IllegalStateException("role already in DB");
        }
    }

    @Override
    public List<Role> getRoles() {
        return roleRepo.findAll();
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        Optional<User> _user = userRepo.findByEmail(email);
        Optional<Role> _role = roleRepo.findByName(roleName);
        _role.ifPresent((role ->
        _user.ifPresent((user) -> {
            user.getRoles().add(role);
        })));
    }

}


