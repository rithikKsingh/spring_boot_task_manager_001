package com.example.taskManager.service;

import com.example.taskManager.entity.Task;
import com.example.taskManager.exception.TaskNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface TaskService {
    Task createTask(Task task);
    Page<Task> getALlTasks(Pageable pageable);
    Task getTaskById(Long id);
    Task updateTask(Long id,Task updatedTask);
    void deleteTask(Long id);
}
