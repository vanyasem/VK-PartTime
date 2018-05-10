package ru.semkin.ivan.parttime.ui.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.semkin.ivan.parttime.R;
import ru.semkin.ivan.parttime.db.entity.Task;
import ru.semkin.ivan.parttime.ui.adapter.TaskListAdapter;
import ru.semkin.ivan.parttime.ui.model.TaskViewModel;

/**
 * A placeholder fragment containing a simple view.
 */
public class TasksFragment extends Fragment {

    public TasksFragment() {
    }

    private TaskViewModel mTaskViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout =  inflater.inflate(R.layout.fragment_tasks, container, false);

        RecyclerView recyclerView = layout.findViewById(R.id.recyclerview);
        final TaskListAdapter adapter = new TaskListAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mTaskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        mTaskViewModel.getAllTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable final List<Task> tasks) {
                // Update the cached copy of the tasks in the adapter.
                adapter.setTasks(tasks);
            }
        });

        return layout;
    }
}
