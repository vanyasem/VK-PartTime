package ru.semkin.ivan.parttime.ui.fragment;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.semkin.ivan.parttime.R;
import ru.semkin.ivan.parttime.data.TaskRepository;
import ru.semkin.ivan.parttime.model.Task;
import ru.semkin.ivan.parttime.ui.adapter.ChartCard;
import ru.semkin.ivan.parttime.util.ActivityUtil;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeFragment extends Fragment {

    public HomeFragment() {
    }

    @BindView(R.id.done)
    TextView done;
    @BindView(R.id.left)
    TextView left;
    @BindView(R.id.chart_card)
    CardView cardView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout =  inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, layout);

        if(getContext() != null) {
            ChartCard chartCard = new ChartCard(cardView, getContext());
            chartCard.init();

            TaskRepository taskRepository = new TaskRepository(getContext());
            final LiveData<List<Task>> doneDb = taskRepository.getAllDone();
            doneDb.observe(this, new Observer<List<Task>>() {
                @Override
                public void onChanged(@Nullable List<Task> tasks) {
                    if(tasks != null) {
                        Calendar calendar = Calendar.getInstance();
                        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                        int count = 0;
                        for (Task task: tasks) {
                            calendar.setTimeInMillis(task.getDate() * 1000);

                            if(calendar.get(Calendar.DAY_OF_WEEK) == dayOfWeek) {
                                count++;
                            }
                        }
                        done.setText(String.valueOf(count));
                    }
                }
            });

            final LiveData<List<Task>> activeDb = taskRepository.getAllActive();
            activeDb.observe(this, new Observer<List<Task>>() {
                @Override
                public void onChanged(@Nullable List<Task> tasks) {
                    if(tasks != null)
                        left.setText(String.valueOf(tasks.size()));
                }
            });
        }

        ActivityUtil.setActionTitle(R.string.app_name, (AppCompatActivity)getActivity());

        return layout;
    }
}
