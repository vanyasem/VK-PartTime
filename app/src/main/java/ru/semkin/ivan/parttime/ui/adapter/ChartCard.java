package ru.semkin.ivan.parttime.ui.adapter;

import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.BounceInterpolator;

import com.db.chart.animation.Animation;
import com.db.chart.model.LineSet;
import com.db.chart.renderer.AxisRenderer;
import com.db.chart.tooltip.Tooltip;
import com.db.chart.util.Tools;
import com.db.chart.view.LineChartView;

import ru.semkin.ivan.parttime.R;


public class ChartCard {

    private final LineChartView mChart;
    private final Context mContext;
    private final String[] mLabels;
    private final float[] mValues = {3, 4, 4, 8, 6, 9, 7};
    private Tooltip mTip;
    private Runnable mBaseAction;

    public ChartCard(CardView card, Context context) {
        mContext = context;
        mLabels = context.getResources().getStringArray(R.array.chart_days);
        mChart = card.findViewById(R.id.chart);
    }

    public void init() {
        show(new Runnable() {@Override public void run() { }});
    }

    public void show(Runnable action) {
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
        dataset.setColor(Color.parseColor("#b3b5bb"))
                .setFill(mContext.getResources().getColor(R.color.colorPrimaryDark))
                .setDotsColor(Color.parseColor("#ffc755"))
                .setThickness(4)
                .endAt(5);
        mChart.addData(dataset);

        dataset = new LineSet(mLabels, mValues);
        dataset.setColor(Color.parseColor("#758cbb"))
                .setFill(mContext.getResources().getColor(R.color.colorPrimaryDark))
                .setDotsColor(Color.parseColor("#758cbb"))
                .setThickness(4)
                .setDashed(new float[]{10f, 10f})
                .beginAt(4);
        mChart.addData(dataset);

        mBaseAction = action;
        Runnable chartAction = new Runnable() {
            @Override
            public void run() {
                mBaseAction.run();
                mTip.prepare(mChart.getEntriesArea(0).get(3), mValues[3]);
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
