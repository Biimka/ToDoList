package com.startandroid.biimka.todolist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.Collections;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<Task> tasks = Collections.emptyList();
    private Listener listener;

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    void setListener(Listener listener) {
        this.listener = listener;
    }

    interface Listener {
        void onItemClicked(Task task);

        void onItemChecked(long taskId, boolean isChecked);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox checkBox;

        private MyViewHolder(View v) {
            super(v);
            checkBox = (CheckBox) v.findViewById(R.id.checkbox);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClicked(tasks.get(getAdapterPosition()));
                }
            });
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    listener.onItemChecked(tasks.get(getAdapterPosition()).getId(), isChecked);
                }
            });
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.task_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        myViewHolder.checkBox.setText(tasks.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}
