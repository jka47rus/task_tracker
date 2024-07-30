package com.example.task_tracker.mapper;

import com.example.task_tracker.dto.TaskResponse;
import com.example.task_tracker.model.Task;
import com.example.task_tracker.model.User;
import com.example.task_tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class TaskMapper {

    private final UserRepository userRepository;

    public TaskResponse taskMapper(Task task) {
        var taskResponseBuilder = TaskResponse.builder();

        if (!task.getObserverIds().isEmpty()) {
            userRepository
                    .findAllById(task.getObserverIds())
                    .map(User::getId)
                    .collectList()
                    .subscribe(userIds -> taskResponseBuilder.observerIds(new HashSet<>(userIds)));
        }

        userRepository
                .findById(task.getAuthorId())
                .subscribe(taskResponseBuilder::author);

        userRepository
                .findById(task.getAssigneeId())
                .subscribe(taskResponseBuilder::assignee);

        taskResponseBuilder
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .createAt(task.getCreateAt())
                .updateAt(task.getUpdateAt())
                .authorId(task.getAuthorId())
                .status(task.getStatus())
                .observerIds(task.getObserverIds())
                .assigneeId(task.getAssigneeId());

        return taskResponseBuilder.build();
    }
}
