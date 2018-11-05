package com.startandroid.biimka.todolist;

import java.util.ArrayList;
import java.util.List;

public class TasksRepoImpl implements TasksRepo {
    private List<Task> tasks = new ArrayList<>();
    private long maxID = 1;
    private static final TasksRepoImpl ourInstance = new TasksRepoImpl();

    public static TasksRepoImpl getInstance() {
        return ourInstance;
    }

    private TasksRepoImpl() {
    }

    @Override
    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public Task getTask(long id) {
        final int position = getIndex(id);
        if (position < 0) {
            return null;
        }
        return tasks.get(position);
    }

    @Override
    public void createTask(String name) {
        tasks.add(new Task(maxID, name, false));
        maxID++;
    }

    @Override
    public void updateTask(Task task) {
        final int position = getIndex(task.getId());
        if (position < 0) {
            return;
        }
        tasks.set(position, task);
    }

    @Override
    public void deleteTask(long id) {
        final int position = getIndex(id);
        if (position < 0) {
            return;
        }
        tasks.remove(position);
    }

    private int getIndex(long id) {
        for (int i = 0; i < tasks.size(); i++) {
            if (id == tasks.get(i).getId()) {
                return i;
            }
        }
        return -1;
    }
}
