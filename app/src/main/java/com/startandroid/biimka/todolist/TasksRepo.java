package com.startandroid.biimka.todolist;

import java.util.List;

public interface TasksRepo {
    public List<Task> getTasks();

    public Task getTask(long id);

    public void createTask(String name);

    public void updateTask(Task task);

    public void deleteTask(long id);
}
