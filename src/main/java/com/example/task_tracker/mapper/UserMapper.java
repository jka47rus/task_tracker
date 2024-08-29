package com.example.task_tracker.mapper;

import com.example.task_tracker.dto.UserRequest;
import com.example.task_tracker.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User fromRequestToUser(UserRequest userRequest) {

        return User.builder()
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .email(userRequest.getEmail())
                .build();
    }
}
