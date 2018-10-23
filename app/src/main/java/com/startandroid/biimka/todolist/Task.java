package com.startandroid.biimka.todolist;

import java.util.Objects;

public class Task {
    private long id;
    private String name;
    private boolean isCompleted;

    public Task(long id, String name, boolean isCompleted) {
        this.id = id;
        this.name = name;
        this.isCompleted = isCompleted;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean completed) {
        isCompleted = completed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Task task = (Task) o;
        return id == task.id &&
                isCompleted == task.isCompleted &&
                Objects.equals(name, task.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, isCompleted);
    }
}
