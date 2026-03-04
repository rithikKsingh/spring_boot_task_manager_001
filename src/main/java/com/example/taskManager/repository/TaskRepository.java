package com.example.taskManager.repository;

import com.example.taskManager.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    Page<Task> findByUserUsername(String username, Pageable pageable);
}
