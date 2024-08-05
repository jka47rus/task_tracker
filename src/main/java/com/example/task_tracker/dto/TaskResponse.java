package com.example.task_tracker.dto;

import com.example.task_tracker.model.TaskStatus;
import com.example.task_tracker.model.User;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Set;

@Data
@Builder
public class TaskResponse {
    private String id;
    private String name;
    private String description;
    private Instant createAt;
    private Instant updateAt;
    private TaskStatus status;
    private String authorId;
    private String assigneeId;
    Set<String> observerIds;

    private User author;
    private User assignee;
    private Set<User> observers;


}
