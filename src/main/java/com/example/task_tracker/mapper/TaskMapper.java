package com.example.task_tracker.mapper;

import com.example.task_tracker.dto.TaskResponse;
import com.example.task_tracker.model.Task;
import com.example.task_tracker.model.User;
import com.example.task_tracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class TaskMapper {

    private final UserService userService;

    public Mono<TaskResponse> fromTaskToResponse(Mono<Task> taska) {
        if (taska == null) return null;

        return taska.map(task -> {
            Set<User> observers = new HashSet<>();
            task.getObserverIds().forEach(id -> {
                observers.add(userService.findById(id).block());
            });
            return TaskResponse.builder()
                    .id(task.getId())
                    .name(task.getName())
                    .description(task.getDescription())
                    .createAt(task.getCreateAt())
                    .updateAt(task.getUpdateAt())
                    .authorId(task.getAuthorId())
                    .status(task.getStatus())
                    .observerIds(task.getObserverIds())
                    .assigneeId(task.getAssigneeId())
                    .author(userService.findById(task.getAuthorId()).block())
                    .assignee(userService.findById(task.getAssigneeId()).block())
                    .observers(observers)
                    .build();


        });

    }

    public Flux<TaskResponse> fromTaskFluxToTaskResponse(Flux<Task> tasks) {
        return tasks.map(task -> {
            return fromTaskToResponse(Mono.just(task)).block();
        });
    }
}
