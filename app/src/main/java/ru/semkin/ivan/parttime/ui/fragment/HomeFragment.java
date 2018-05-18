package ru.semkin.ivan.parttime.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.semkin.ivan.parttime.R;
import ru.semkin.ivan.parttime.ui.adapter.ChartCard;
import ru.semkin.ivan.parttime.util.ActivityUtil;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeFragment extends Fragment {

    public HomeFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout =  inflater.inflate(R.layout.fragment_home, container, false);

        if(getContext() != null) {
            CardView cardView = layout.findViewById(R.id.chart_card);
            (new ChartCard(cardView, getContext())).init();
        }

        ActivityUtil.setActionTitle(R.string.app_name, (AppCompatActivity)getActivity());

        return layout;
    }
}
