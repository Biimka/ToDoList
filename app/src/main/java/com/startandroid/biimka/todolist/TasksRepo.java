package com.startandroid.biimka.todolist;

import java.util.List;

public interface TasksRepo {
    List<Task> getTasks();

    void createTask(Task task);

    void updateTask(Task task);

    void deleteTask(long id);

}
