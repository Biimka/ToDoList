package com.startandroid.biimka.todolist;

import java.util.List;

public interface TasksRepo {
    public List<Task> getTasks();
    public void createTask(Task task);
    public void updateTask(Task task);
    public void deleteTask(long id);
}
