package com.example.task_tracker.service;

import com.example.task_tracker.model.RoleType;
import com.example.task_tracker.model.User;
import com.example.task_tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    public Mono<User> findById(String id) {
        return userRepository.findById(id);
    }

    public Mono<User> save(User user, RoleType roleType) {
        user.setId(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.addRole(roleType);

        return userRepository.save(user);
    }

    public Mono<User> update(String id, User user) {
        return findById(id).flatMap(updateUser -> {
            if (StringUtils.hasText(user.getUsername())) updateUser.setUsername(user.getUsername());
            if (user.getEmail() != null) updateUser.setEmail(user.getEmail());
            return userRepository.save(updateUser);
        });
    }

    public Mono<Void> deleteById(String id) {
        return userRepository.deleteById(id);
    }


    public Mono<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
