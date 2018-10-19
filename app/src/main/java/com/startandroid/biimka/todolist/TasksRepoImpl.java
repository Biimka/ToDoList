package com.startandroid.biimka.todolist;

import java.util.List;

public class TasksRepoImpl implements TasksRepo {
    private static final TasksRepoImpl ourInstance = new TasksRepoImpl();

    public static TasksRepoImpl getInstance() {
        return ourInstance;
    }

    private TasksRepoImpl() {
    }

    @Override
    public List<Task> getTasks() {
        return null;
    }

    @Override
    public void createTask(Task task) {

    }

    @Override
    public void updateTask(Task task) {

    }

    @Override
    public void deleteTask(long id) {

    }
}
