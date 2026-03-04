package com.example.taskManager.service;

import com.example.taskManager.entity.Task;
import com.example.taskManager.entity.User;
import com.example.taskManager.exception.TaskNotFoundException;
import com.example.taskManager.repository.TaskRepository;
import com.example.taskManager.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.springframework.security.access.AccessDeniedException;

@Service
@Data
public class TaskServiceImpl implements TaskService{

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    private String getLoggedInUsername(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null || !authentication.isAuthenticated()){
            throw new IllegalStateException("User not authenticated");
        }
        return authentication.getName();
    }

    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Task createTask(Task task){
        String username=getLoggedInUsername();
        User user=userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
        task.setUser(user);
        return taskRepository.save(task);
    }

    @Override
    public Page<Task> getALlTasks(Pageable pageable){
        String username=getLoggedInUsername();
        return taskRepository.findByUserUsername(username,pageable);
    }
    @Override
    public Task getTaskById(Long id){
        String username=getLoggedInUsername();
        Task task= taskRepository.findById(id).orElseThrow(()->new TaskNotFoundException("Task not found with id :"+id));
        if(!task.getUser().getUsername().equals(username)){
            throw new AccessDeniedException("You can access only your own tasks");
        }
        return task;
    }

    @Override
    public Task updateTask(Long id,Task updatedTask){
        Task existingTask=getTaskById(id);

        String username=getLoggedInUsername();
        if(!existingTask.getUser().getUsername().equals(username)){
            throw new AccessDeniedException("You can only update your own tasks");
        }

        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setStatus(updatedTask.getStatus());
        return taskRepository.save(existingTask);
    }

    @Override
    public void deleteTask(Long id){
        Task existingTask=getTaskById(id);

        String username=getLoggedInUsername();
        if(!existingTask.getUser().getUsername().equals(username)){
            throw new AccessDeniedException("You can only delete your own tasks");
        }

        taskRepository.delete(existingTask);
    }
}
