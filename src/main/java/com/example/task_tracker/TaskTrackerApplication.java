package com.example.task_tracker;

import com.example.task_tracker.model.Task;
import com.example.task_tracker.model.TaskStatus;
import com.example.task_tracker.model.User;
import com.example.task_tracker.repository.TaskRepository;
import com.example.task_tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

import java.time.Instant;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class TaskTrackerApplication {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public static void main(String[] args) {
        SpringApplication.run(TaskTrackerApplication.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner() {
        return args -> {
            var user1 = userRepository.save(new User().setEmail("user1@mail.ru").setUsername("user1")).block();
            log.info("user1 " + user1);
            var user2 = userRepository.save(new User().setEmail("user2@mail.ru").setUsername("user2")).block();
            log.info("user2 " + user2);
            var user3 = userRepository.save(new User().setEmail("user3@mail.ru").setUsername("user3")).block();
            log.info("user3 " + user3);

            var task = taskRepository.save(
                    new Task()
                            .setName("task")
                            .setStatus(TaskStatus.TODO)
                            .setDescription("has to be done Task")
                            .setCreateAt(Instant.now())
                            //.setAuthorId(user3.getId())
                            //.setAssigneeId(user2.getId())
                            //.addObserverId(user1.getId())
            ).block();
            log.info("task " + task);
        };
    }
}
