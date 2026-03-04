package com.example.taskManager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="tasks")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Title is required")
    @Size(max=100,message = "Title cannot exceed 100 characters")
    private String title;

    @Size(max=100,message = "Description cannot exceed 100 characters")
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @PrePersist
    public void prePersist(){
        this.createdAt=LocalDateTime.now();
        if(this.status==null){
            this.status=TaskStatus.TODO;
        }
    }

}
