package com.jex.repository;

import com.jex.model.Task;

import java.util.*;

public class InMemoryTaskRepository implements TaskRepository {
    private final Map<Integer, Task> tasks = new HashMap<>();

    public void save(Task task) {
        tasks.put(task.getId(), task);
    }

    public Optional<Task> findById(int id) {
        return Optional.ofNullable(tasks.get(id));
    }

    public List<Task> findAll() {
        return new ArrayList<>(tasks.values());
    }

    public void delete(int id) {
        tasks.remove(id);
    }

    public void update(Task task) {
        tasks.put(task.getId(), task);
    }
}
