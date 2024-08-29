package com.example.task_tracker.dto;

import lombok.Data;

@Data
public class UserRequest {

    private String username;
    private String password;
    private String email;
}
