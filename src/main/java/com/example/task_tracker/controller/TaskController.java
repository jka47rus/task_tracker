package com.example.task_tracker.controller;

import com.example.task_tracker.dto.TaskResponse;
import com.example.task_tracker.mapper.TaskMapper;
import com.example.task_tracker.model.Task;
import com.example.task_tracker.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public Flux<TaskResponse> getAllTasks() {
        return taskService.findAll()
                .flatMap(task -> taskMapper.map(task).flux());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<TaskResponse>> getById(@PathVariable String id) {
        return taskService
                .findById(id)
                .flatMap(taskMapper::map)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Task>> createUser(@RequestBody Task task) {
        return taskService.save(task)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Task>> updateTask(@PathVariable String id, @RequestBody Task task) {
        return taskService.update(id, task)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/addObserver")
    public Mono<ResponseEntity<Task>> addObserver(@RequestParam String userId, @RequestParam String taskId) {
        return taskService.addObserver(userId, taskId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteTask(@PathVariable String id) {
        return taskService.deleteById(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }


}
