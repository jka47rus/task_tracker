package com.example.task_tracker.mapper;

import com.example.task_tracker.dto.TaskResponse;
import com.example.task_tracker.model.Task;
import com.example.task_tracker.model.User;
import com.example.task_tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class TaskMapper {

    private final UserRepository userRepository;

    public Mono<TaskResponse> fromTaskToResponse(Mono<Task> taska) {
        if (taska == null) return Mono.empty();

        return taska.flatMap(this::taskResponse);
    }

    public Mono<TaskResponse> taskResponse(Task task) {

        return Mono.zip(
                userRepository.findAllById(task.getObserverIds()).collectList(),
                findUser(task.getAuthorId()),
                findUser(task.getAssigneeId())
        ).map(value ->

                TaskResponse.builder()
                        .id(task.getId())
                        .name(task.getName())
                        .description(task.getDescription())
                        .createAt(task.getCreateAt())
                        .updateAt(task.getUpdateAt())
                        .authorId(task.getAuthorId())
                        .status(task.getStatus())
                        .assigneeId(task.getAssigneeId())
                        .observerIds(task.getObserverIds())
                        .observers(new HashSet<>(value.getT1()))
                        .author(value.getT2())
                        .assignee(value.getT3())
                        .build()
        );


    }

    private Mono<User> findUser(String id) {
        return userRepository.findById(id);
    }


    public Flux<TaskResponse> fromTaskFluxToTaskResponse(Flux<Task> tasks) {

        return tasks.flatMap(task -> {
            return fromTaskToResponse(Mono.just(task));
        });
    }
}
