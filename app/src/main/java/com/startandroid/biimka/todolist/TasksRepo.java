package com.startandroid.biimka.todolist;

import java.util.List;

public interface TasksRepo {
    List<Task> getTasks();

    public void createTask(String name);

    public void updateTask(Task task);

    public void deleteTask(long id);

}
