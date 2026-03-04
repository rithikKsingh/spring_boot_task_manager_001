package com.example.taskManager.controller;

import com.example.taskManager.entity.Task;
import com.example.taskManager.service.TaskService;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@Data
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task){
        return ResponseEntity.ok(taskService.createTask(task));
    }

//    http://localhost:8080/tasks?page=0&size=5
    @GetMapping
    public ResponseEntity<Page<Task>> getALlTasks(Pageable pageable){
        return ResponseEntity.ok(taskService.getALlTasks(pageable));
    }

//    http://localhost:8080/tasks/1
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id){
        return ResponseEntity.ok(taskService.getTaskById(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id,@Valid @RequestBody Task task){
        return ResponseEntity.ok(taskService.updateTask(id,task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
        return ResponseEntity.ok("Task deleted successfully");
    }
}
