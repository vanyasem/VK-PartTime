package ru.semkin.ivan.parttime.ui.adapter;

import android.animation.PropertyValuesHolder;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.BounceInterpolator;

import com.db.chart.animation.Animation;
import com.db.chart.model.LineSet;
import com.db.chart.renderer.AxisRenderer;
import com.db.chart.tooltip.Tooltip;
import com.db.chart.util.Tools;
import com.db.chart.view.LineChartView;

import java.util.Calendar;
import java.util.List;

import ru.semkin.ivan.parttime.R;
import ru.semkin.ivan.parttime.data.TaskRepository;
import ru.semkin.ivan.parttime.model.Task;


public class ChartCard {

    private final LineChartView mChart;
    private final Context mContext;
    private final String[] mLabels;
    private final Calendar mCalendar = Calendar.getInstance();
    private final int mWeekOfYear = mCalendar.get(Calendar.WEEK_OF_YEAR);
    private final int mDayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK) - 1;
    private float[] mValues = {0, 0, 0, 0, 0, 0, 0};
    private Tooltip mTip;
    private Runnable mBaseAction;

    public ChartCard(CardView card, Context context) {
        mContext = context;
        mLabels = context.getResources().getStringArray(R.array.chart_days);
        mChart = card.findViewById(R.id.chart);
    }

    public void init() {
        gatherData();
    }

    private void gatherData() {
        TaskRepository taskRepository = new TaskRepository(mContext);
        final LiveData<List<Task>> db = taskRepository.getAllDone();
        db.observeForever(new Observer<List<Task>>() {
            @Override
            public void onChanged(@Nullable List<Task> tasks) {
                if(tasks != null) {
                    for (Task task: tasks) {

                        mCalendar.setTimeInMillis(task.getDate() * 1000);
                        if(mCalendar.get(Calendar.WEEK_OF_YEAR) == mWeekOfYear) {
                            int dow = mCalendar.get(Calendar.DAY_OF_WEEK);
                            mValues[dow - 1]++;
                        }
                    }
                    show(new Runnable() {@Override public void run() { }});
                }
                db.removeObserver(this);
            }
        });
    }

    private void show(Runnable action) {

        // Tooltip
        mTip = new Tooltip(mContext, R.layout.linechart_three_tooltip, R.id.value);

        mTip.setVerticalAlignment(Tooltip.Alignment.BOTTOM_TOP);
        mTip.setDimensions((int) Tools.fromDpToPx(58), (int) Tools.fromDpToPx(25));

        mTip.setEnterAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 1),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f),
                PropertyValuesHolder.ofFloat(View.SCALE_X, 1f)).setDuration(200);

        mTip.setExitAnimation(PropertyValuesHolder.ofFloat(View.ALPHA, 0),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f),
                PropertyValuesHolder.ofFloat(View.SCALE_X, 0f)).setDuration(200);

        mTip.setPivotX(Tools.fromDpToPx(65) / 2);
        mTip.setPivotY(Tools.fromDpToPx(25));

        // Data
        LineSet dataset = new LineSet(mLabels, mValues);
        dataset.setColor(Color.parseColor("#758cbb"))
                .setFill(mContext.getResources().getColor(R.color.colorPrimaryDark))
                .setDotsColor(Color.parseColor("#758cbb"))
                .setThickness(4)
                .setDashed(new float[]{10f, 10f})
                .beginAt(mDayOfWeek - 1);
        mChart.addData(dataset);

        dataset = new LineSet(mLabels, mValues);
        dataset.setColor(Color.parseColor("#b3b5bb"))
                .setFill(mContext.getResources().getColor(R.color.colorPrimaryDark))
                .setDotsColor(Color.parseColor("#ffc755"))
                .setThickness(4)
                .endAt(mDayOfWeek);
        mChart.addData(dataset);

        mBaseAction = action;
        Runnable chartAction = new Runnable() {
            @Override
            public void run() {
                mBaseAction.run();
                mTip.prepare(mChart.getEntriesArea(0).get(mDayOfWeek), mValues[mDayOfWeek]);
                mChart.showTooltip(mTip, true);
            }
        };

        mChart.setAxisBorderValues(0, 20)
                .setYLabels(AxisRenderer.LabelPosition.NONE)
                .setTooltips(mTip)
                .show(new Animation().setInterpolator(new BounceInterpolator())
                        .fromAlpha(0)
                        .withEndAction(chartAction));
    }
}
