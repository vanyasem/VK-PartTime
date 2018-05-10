package ru.semkin.ivan.parttime.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.semkin.ivan.parttime.R;
import ru.semkin.ivan.parttime.db.entity.Task;

/**
 * Created by Ivan Semkin on 5/11/18
 */
public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder> {

    class TaskViewHolder extends RecyclerView.ViewHolder {
        private final TextView taskItemView;
        private final TextView taskItemView2;

        private TaskViewHolder(View itemView) {
            super(itemView);
            taskItemView = itemView.findViewById(R.id.textView);
            taskItemView2 = itemView.findViewById(R.id.textView2);
        }
    }

    private final LayoutInflater mInflater;
    private List<Task> mTasks; // Cached copy of tasks

    public TaskListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        if (mTasks != null) {
            Task current = mTasks.get(position);
            holder.taskItemView.setText(current.getFirstName());
            holder.taskItemView2.setText(current.getLastName());
        } else {
            // Covers the case of data not being ready yet.
            holder.taskItemView.setText("No ");
            holder.taskItemView2.setText("Task");
        }
    }

    public void setTasks(List<Task> tasks){
        mTasks = tasks;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mTasks has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mTasks != null)
            return mTasks.size();
        else return 0;
    }
}