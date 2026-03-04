package com.example.taskManager.exception;

public class TaskNotFoundException extends RuntimeException{
    public TaskNotFoundException(String msg){
        super(msg);
    }
}
