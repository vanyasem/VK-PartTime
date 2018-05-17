package ru.semkin.ivan.parttime.ui.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.semkin.ivan.parttime.R;
import ru.semkin.ivan.parttime.util.ActivityUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostFragment extends Fragment {


    public PostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_post, container, false);

        ActivityUtil.setActionTitle(R.string.view_post, (AppCompatActivity)getActivity());

        return layout;
    }

}
