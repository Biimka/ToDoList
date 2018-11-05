package com.startandroid.biimka.todolist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class TaskActivity extends AppCompatActivity {

    final static String TASK_ID = "TASK_ID";

    private TasksRepo tasksRepo = TasksRepoImpl.getInstance();

    private long taskId = 0;
    private String taskName = "";
    private boolean isCompleted = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        final EditText editTextTaskName = (EditText) findViewById(R.id.editTextTaskName);
        final Button butCreateUpdateRemove = (Button) findViewById(R.id.buttonCreateUpdateRemove);
        final Switch switchTaskActivity = (Switch) findViewById(R.id.switchTaskActivity);

        editTextTaskName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                taskName = s.toString();
                butCreateUpdateRemove.setEnabled(s.length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        butCreateUpdateRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (taskId == 0) {
                    tasksRepo.createTask(taskName);
                } else {
                    tasksRepo.updateTask(new Task(taskId, taskName, isCompleted));
                }
                finish();
            }
        });

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(TASK_ID)) {
            butCreateUpdateRemove.setText(R.string.save);
            switchTaskActivity.setVisibility(Switch.VISIBLE);
            taskId = getIntent().getExtras().getLong(TASK_ID);
            final Task task = tasksRepo.getTask(taskId);
            taskName = task.getName();
            isCompleted = task.isCompleted();
            if (isCompleted) {
                switchTaskActivity.setChecked(true);
            }
        }
        editTextTaskName.setText(taskName);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("key1", taskId);
        outState.putString("key", taskName);
        outState.putBoolean("key2", isCompleted);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        taskName = savedInstanceState.getString("key");
        taskId = savedInstanceState.getLong("key1");
        isCompleted = savedInstanceState.getBoolean("key2");
    }
}
