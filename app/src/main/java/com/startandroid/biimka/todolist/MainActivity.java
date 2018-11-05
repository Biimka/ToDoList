package com.startandroid.biimka.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private MyAdapter adapter;
    private TasksRepo tasksRepo = TasksRepoImpl.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new MyAdapter();
        adapter.setListener(new MyAdapter.Listener() {
            @Override
            public void onItemClicked(Task task) {
                startActivity(new Intent(MainActivity.this, TaskActivity.class)
                        .putExtra(TaskActivity.TASK_ID, task.getId()));
            }

            @Override
            public void onItemChecked(long taskId, boolean isChecked) {
                final Task task = tasksRepo.getTask(taskId);

                if (task == null) return;

                task.setCompleted(isChecked);
                tasksRepo.updateTask(task);
            }
        });

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        (findViewById(R.id.addTaskFAB)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, TaskActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.setTasks(tasksRepo.getTasks());
    }
}
