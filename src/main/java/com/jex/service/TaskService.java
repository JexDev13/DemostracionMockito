package com.jex.service;

import com.jex.model.Task;

import java.util.List;

public interface TaskService {
    void createTask(Task task);
    Task getTask(int id);
    List<Task> getAllTasks();
    void updateTask(Task task);
    void deleteTask(int id);
}
