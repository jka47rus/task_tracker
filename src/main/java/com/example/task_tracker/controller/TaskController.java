package com.example.task_tracker.controller;

import com.example.task_tracker.dto.TaskResponse;
import com.example.task_tracker.mapper.TaskMapper;
import com.example.task_tracker.model.Task;
import com.example.task_tracker.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/task")
public class TaskController {

    public final TaskService taskService;
    public final TaskMapper taskMapper;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MANAGER')")
    public Flux<TaskResponse> getAllTasks() {
        return taskMapper.fromTaskFluxToTaskResponse(taskService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MANAGER')")
    public Mono<ResponseEntity<TaskResponse>> getById(@PathVariable String id) {
        return taskMapper.fromTaskToResponse(taskService.findById(id))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public Mono<ResponseEntity<Task>> createTask(@RequestBody Task task) {
        return taskService.save(task)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public Mono<ResponseEntity<Task>> updateTask(@PathVariable String id, @RequestBody Task task) {
        return taskService.update(id, task)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/addObserver")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MANAGER')")
    public Mono<ResponseEntity<Task>> addObserver(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String taskId) {

        return taskService.addObserver(userDetails.getUsername(), taskId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public Mono<ResponseEntity<Void>> deleteTask(@PathVariable String id) {
        return taskService.deleteById(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }


}
