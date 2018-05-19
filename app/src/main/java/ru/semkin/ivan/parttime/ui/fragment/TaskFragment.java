package ru.semkin.ivan.parttime.ui.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vk.sdk.api.model.VKApiPost;
import com.vk.sdk.api.model.VKList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.semkin.ivan.parttime.R;
import ru.semkin.ivan.parttime.api.request.Messages;
import ru.semkin.ivan.parttime.api.request.VKListCallback;
import ru.semkin.ivan.parttime.api.request.Wall;
import ru.semkin.ivan.parttime.model.Task;
import ru.semkin.ivan.parttime.ui.adapter.EmptyRecyclerView;
import ru.semkin.ivan.parttime.ui.adapter.TaskListAdapter;
import ru.semkin.ivan.parttime.ui.model.TaskViewModel;
import ru.semkin.ivan.parttime.util.ActivityUtil;

/**
 * A placeholder fragment containing a simple view.
 */
public class TaskFragment extends Fragment {

    public TaskFragment() {

    }

    @BindView(R.id.recyclerview) EmptyRecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout) SwipeRefreshLayout refreshLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View layout =  inflater.inflate(R.layout.fragment_tasks, container, false);
        ButterKnife.bind(this, layout);

        final TaskListAdapter adapter = new TaskListAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        final TaskViewModel taskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        taskViewModel.getAllActive().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable final List<Task> tasks) {
                // Update the cached copy of the tasks in the mAdapter.
                adapter.setTasks(tasks);
            }
        });

        ItemTouchHelper.SimpleCallback swipeCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                        final Task swiped = adapter.get(viewHolder.getAdapterPosition());
                        swiped.setDone(true);
                        postDone(swiped);
                        taskViewModel.markDone(swiped);

                        Snackbar.make(layout,R.string.marked_done, Snackbar.LENGTH_LONG).
                                setAction(R.string.undo, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        swiped.setDone(false);
                                        undoDone(swiped);
                                        taskViewModel.markDone(swiped);
                                    }

                                }).show();
                    }
                };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        refresh();

        ActivityUtil.setActionTitle(R.string.nav_tasks, (AppCompatActivity)getActivity());

        return layout;
    }

    private void postDone(Task task) {
        if(task.getType() == Task.TYPE_MESSAGE) {
            Messages.send(null, "+");
        } else {
            Wall.createComment(null, "+", task.getUid());
        }
    }

    private void undoDone(Task task) {

    }

    private void refresh() {
        refreshLayout.setRefreshing(true);
        Messages.getHistory(null, getContext());
        Wall.get(new VKListCallback<VKApiPost>() {
            @Override
            public void onFinished(VKList<VKApiPost> items) {
                refreshLayout.setRefreshing(false);
            }
        }, getContext());
    }
}
