package com.example.task_tracker.service;

import com.example.task_tracker.model.Task;
import com.example.task_tracker.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    public Flux<Task> findAll() {
        return taskRepository.findAll();
    }

    public Mono<Task> findById(String id) {
        return taskRepository.findById(id);
    }

    public Mono<Task> save(Task task) {
        task.setId(UUID.randomUUID().toString());
        task.setCreateAt(Instant.now());
        return taskRepository.save(task);
    }

    public Mono<Task> update(String id, Task task) {
        return findById(id).flatMap(updateTask -> {
            if (StringUtils.hasText(task.getName())) updateTask.setName(task.getName());
            if (task.getDescription() != null) updateTask.setDescription(task.getDescription());
            updateTask.setUpdateAt(Instant.now());
            return taskRepository.save(updateTask);
        });
    }

    public Mono<Task> addObserver(String userId, String taskId) {
        if (userService.findById(userId) == null) return null;
        return findById(taskId).flatMap(task -> {
            task.addObserverId(userId);
            taskRepository.save(task);
            return taskRepository.save(task);
        });
    }

    public Mono<Void> deleteById(String id) {
        return taskRepository.deleteById(id);
    }
}
