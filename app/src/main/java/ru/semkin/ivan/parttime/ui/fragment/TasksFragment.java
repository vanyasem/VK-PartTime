package ru.semkin.ivan.parttime.ui.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.semkin.ivan.parttime.R;
import ru.semkin.ivan.parttime.model.Task;
import ru.semkin.ivan.parttime.ui.adapter.EmptyRecyclerView;
import ru.semkin.ivan.parttime.ui.adapter.TaskListAdapter;
import ru.semkin.ivan.parttime.ui.model.TaskViewModel;
import ru.semkin.ivan.parttime.util.ActivityUtil;

/**
 * A placeholder fragment containing a simple view.
 */
public class TasksFragment extends Fragment {

    public TasksFragment() {

    }

    @BindView(R.id.recyclerview) EmptyRecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout =  inflater.inflate(R.layout.fragment_tasks, container, false);
        ButterKnife.bind(this, layout);

        final TaskListAdapter adapter = new TaskListAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        TaskViewModel taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        taskViewModel.getAllTasks().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable final List<Task> tasks) {
                // Update the cached copy of the tasks in the mAdapter.
                adapter.setTasks(tasks);
            }
        });

        ActivityUtil.setActionTitle(R.string.nav_tasks, (AppCompatActivity)getActivity());

        return layout;
    }
}
