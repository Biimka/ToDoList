package com.startandroid.biimka.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class TasksRepoImpl implements TasksRepo {
    private static final TasksRepoImpl ourInstance = new TasksRepoImpl();
    private DBHelper dbHelper = new DBHelper(App.context);
    private SQLiteDatabase db = dbHelper.getWritableDatabase();

    public static TasksRepoImpl getInstance() {
        return ourInstance;
    }

    private TasksRepoImpl() {
    }

    @Override
    public List<Task> getTasks() {
        final List<Task> tasks = new ArrayList<>();
        final Cursor tasksCursor = db.query("Tasks", null, null,
                null, null, null, null);
        if (tasksCursor.moveToFirst()) {
            final int idColIndex = tasksCursor.getColumnIndex("id");
            final int nameColIndex = tasksCursor.getColumnIndex("taskName");
            final int isCompletedColIndex = tasksCursor.getColumnIndex("isCompleted");
            do {
                final boolean isCompletedBool = tasksCursor.getInt(isCompletedColIndex) == 1;
                tasks.add(new Task(tasksCursor.getInt(idColIndex),
                        tasksCursor.getString(nameColIndex), isCompletedBool));
            } while (tasksCursor.moveToNext());
        }
        tasksCursor.close();
        return tasks;
    }

    @Override
    public Task getTask(long id) {
        final Cursor tasksCursor = db.query("Tasks", null, "id = ?",
                new String[]{String.valueOf(id)}, null, null, null);
        if (tasksCursor.moveToFirst()) {
            final boolean isCompletedBool = tasksCursor
                    .getInt(tasksCursor.getColumnIndex("isCompleted")) == 1;
            return new Task(tasksCursor.getInt(tasksCursor.getColumnIndex("id")),
                    tasksCursor.getString(tasksCursor.getColumnIndex("taskName")),
                    isCompletedBool);
        }
        tasksCursor.close();
        return null;
    }

    @Override
    public void createTask(String name) {
        final ContentValues contentValues = new ContentValues();
        contentValues.put("taskName", name);
        contentValues.put("isCompleted", 0);
        db.insert("Tasks", null, contentValues);
    }

    @Override
    public void updateTask(Task task) {
        final ContentValues contentValues = new ContentValues();
        final int isCompleted;
        if (!task.isCompleted()) isCompleted = 0;
        else isCompleted = 1;
        contentValues.put("id", task.getId());
        contentValues.put("taskName", task.getName());
        contentValues.put("isCompleted", isCompleted);
        db.update("Tasks", contentValues, "id = ?",
                new String[]{String.valueOf(task.getId())});
    }

    @Override
    public void deleteTask(long id) {
        db.delete("Tasks", "id = " + id, null);
    }
}

class DBHelper extends SQLiteOpenHelper {

    DBHelper(Context context) {
        super(context, context.getString(R.string.myDataBase), null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table Tasks("
                + "id integer primary key autoincrement,"
                + "taskName text,"
                + "isCompleted integer" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }
}

