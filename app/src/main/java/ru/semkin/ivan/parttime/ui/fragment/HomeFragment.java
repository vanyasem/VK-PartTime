package ru.semkin.ivan.parttime.ui.fragment;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.semkin.ivan.parttime.R;
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

        ActivityUtil.setActionTitle(R.string.app_name, (AppCompatActivity)getActivity());

        return layout;
    }
}
