package com.example.task_tracker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Document
@AllArgsConstructor
@Builder
public class Task {
    private String id;
    private String name;
    private String description;
    private Instant createAt;
    private Instant updateAt;
    private TaskStatus status;
    private String authorId;
    private String assigneeId;
    @Builder.Default
    private Set<String> observerIds = new HashSet<>();

    public void addObservers(String id) {
        observerIds.add(id);
    }

}
