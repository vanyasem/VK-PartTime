package ru.semkin.ivan.parttime.ui.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vk.sdk.api.model.VKApiCommunity;
import com.vk.sdk.api.model.VKList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.semkin.ivan.parttime.R;
import ru.semkin.ivan.parttime.api.request.Groups;
import ru.semkin.ivan.parttime.api.request.VKListCallback;
import ru.semkin.ivan.parttime.model.Item;
import ru.semkin.ivan.parttime.prefs.LoginDataManager;
import ru.semkin.ivan.parttime.ui.adapter.EmptyRecyclerView;
import ru.semkin.ivan.parttime.ui.activity.MainActivity;
import ru.semkin.ivan.parttime.ui.adapter.ItemListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfigurationGroupFragment extends Fragment {

    public ConfigurationGroupFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.recyclerview) EmptyRecyclerView recyclerView;
    @BindView(R.id.title) TextView title;

    private ItemListAdapter mAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout =  inflater.inflate(R.layout.fragment_items, container, false);
        ButterKnife.bind(this, layout);

        mAdapter = new ItemListAdapter(getContext());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        title.setText(R.string.work_group);

        mAdapter.setOnItemClickListener(new ItemListAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                LoginDataManager.setGroupId(-mAdapter.get(position).getUid());
                LoginDataManager.setLoggedIn(true);
                MainActivity.openMain(getContext());

                if(getActivity() != null)
                    getActivity().finishAffinity();
            }
        });

        return layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Groups.getGroups(new VKListCallback<VKApiCommunity>() {
            @Override
            public void onFinished(VKList<VKApiCommunity> communities) {
                List<Item> adapterItems = new ArrayList<>();
                for (VKApiCommunity community: communities) {
                    adapterItems.add(
                            new Item(community.id, community.name,
                                    community.screen_name, community.photo_100));
                }
                mAdapter.setItems(adapterItems);
            }
        });
    }
}
