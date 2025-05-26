package com.jex.service.imp;

import com.jex.model.Task;
import com.jex.repository.TaskRepository;
import com.jex.service.TaskService;

import java.util.List;

public class TaskServiceImpl implements TaskService {
    private final TaskRepository repository;

    public TaskServiceImpl(TaskRepository repository) {
        this.repository = repository;
    }

    public void createTask(Task task) {
        repository.save(task);
    }

    public Task getTask(int id) {
        return repository.findById(id).orElse(null);
    }

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public void updateTask(Task task) {
        repository.update(task);
    }

    public void deleteTask(int id) {
        repository.delete(id);
    }
}
