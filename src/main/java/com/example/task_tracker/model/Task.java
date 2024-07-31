package com.example.task_tracker.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Document
public class Task {
    private String id;
    private String name;
    private String description;
    private Instant createAt;
    private Instant updateAt;
    private TaskStatus status;
    private String authorId;
    private String assigneeId;
    @Setter(value = AccessLevel.NONE)
    Set<String> observerIds = new HashSet<>();
    public Task addObserverId(String id) {
        observerIds.add(id);
        return this;
    }
}
