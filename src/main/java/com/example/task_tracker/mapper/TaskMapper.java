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
import java.util.Objects;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class TaskMapper {

    private final UserRepository userRepository;

    public Mono<TaskResponse> map(Task task) {
        var taskResponseBuilder = TaskResponse.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .createAt(task.getCreateAt())
                .updateAt(task.getUpdateAt())
                .authorId(task.getAuthorId())
                .status(task.getStatus())
                .observerIds(task.getObserverIds())
                .assigneeId(task.getAssigneeId());

        return Mono.zip(
                        findUsers(task.getObserverIds())
                                .map(User::getId)
                                .collectList(),
                        findUser(task.getAuthorId()),
                        findUser(task.getAssigneeId())
                ).map(value ->
                        taskResponseBuilder
                                .observerIds(new HashSet<>(value.getT1()))
                                .author(value.getT2())
                                .assignee(value.getT3())
                                .build()
                )
                .defaultIfEmpty(taskResponseBuilder.build());
    }

    private Mono<User> findUser(String id) {
        if (Objects.isNull(id)) return Mono.empty();
        return userRepository.findById(id);
    }

    private Flux<User> findUsers(Set<String> ids) {
        if (Objects.isNull(ids) || ids.isEmpty()) return Flux.empty();
        return userRepository.findAllById(ids);
    }
}
