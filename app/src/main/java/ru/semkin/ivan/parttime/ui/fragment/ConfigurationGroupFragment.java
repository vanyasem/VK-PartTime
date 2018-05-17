package ru.semkin.ivan.parttime.ui.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.semkin.ivan.parttime.R;
import ru.semkin.ivan.parttime.prefs.LoginDataManager;
import ru.semkin.ivan.parttime.ui.activity.EmptyRecyclerView;
import ru.semkin.ivan.parttime.ui.activity.MainActivity;
import ru.semkin.ivan.parttime.ui.adapter.ItemListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfigurationGroupFragment extends Fragment {

    public ConfigurationGroupFragment() {
        // Required empty public constructor
    }

    private ItemListAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout =  inflater.inflate(R.layout.fragment_items, container, false);

        EmptyRecyclerView recyclerView = layout.findViewById(R.id.recyclerview);
        adapter = new ItemListAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.setOnItemClickListener(new ItemListAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                LoginDataManager.setGroupId(adapter.get(position).getUid());
                LoginDataManager.setLoggedIn(true);
                MainActivity.openMain(getContext());
            }
        });

        return layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
