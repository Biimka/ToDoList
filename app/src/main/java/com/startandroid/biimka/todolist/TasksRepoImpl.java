package com.startandroid.biimka.todolist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TasksRepoImpl implements TasksRepo {
    private List<Task> tasks = new ArrayList<>();
    private long maxID = 1;
    private static final TasksRepoImpl ourInstance = new TasksRepoImpl();
    private DBHelper dbHelper = new DBHelper(App.context);
    private ContentValues cv = new ContentValues();
    private SQLiteDatabase db = dbHelper.getWritableDatabase();
    private Cursor cursor = db.query("mytable", null, null,
            null, null, null, null);

    public static TasksRepoImpl getInstance() {
        return ourInstance;
    }

    private TasksRepoImpl() {
    }

    @Override
    public List<Task> getTasks() {
        if (cursor.moveToFirst()) {
            int idColIndex = cursor.getColumnIndex("id");
            int nameColIndex = cursor.getColumnIndex("taskName");
            int isCompletedColIndex = cursor.getColumnIndex("isCompleted");
            boolean isCompletedBool = isCompletedColIndex == 1;
            do {
                tasks.add(new Task(cursor.getInt(idColIndex),
                        cursor.getString(nameColIndex), isCompletedBool));
            } while (cursor.moveToNext());
        }
        cursor.close();
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
        cv.put("taskName", name);
        cv.put("isCompleted", false);
        db.insert("mytable", null, cv);
    }

    @Override
    public void updateTask(Task task) {
        final int position = getIndex(task.getId());
        if (position < 0) {
            return;
        }
        tasks.set(position, task);
        int isCompleted;
        if (!task.isCompleted()) isCompleted = 0;
        else isCompleted = 1;
        cv.put("id", task.getId());
        cv.put("taskName", task.getName());
        cv.put("isCompleted", isCompleted);
        db.update("mytable", cv, "id = ?",
                new String[]{String.valueOf(task.getId())});
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
