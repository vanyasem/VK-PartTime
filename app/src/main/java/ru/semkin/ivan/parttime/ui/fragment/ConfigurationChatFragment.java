package ru.semkin.ivan.parttime.ui.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vk.sdk.api.model.VKApiDialog;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKList;

import java.util.ArrayList;
import java.util.List;

import ru.semkin.ivan.parttime.R;
import ru.semkin.ivan.parttime.api.request.GetDialogs;
import ru.semkin.ivan.parttime.api.request.GetUsers;
import ru.semkin.ivan.parttime.model.Item;
import ru.semkin.ivan.parttime.prefs.LoginDataManager;
import ru.semkin.ivan.parttime.ui.activity.EmptyRecyclerView;
import ru.semkin.ivan.parttime.ui.activity.MainIntroActivity;
import ru.semkin.ivan.parttime.ui.adapter.ItemListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfigurationChatFragment extends Fragment {

    public ConfigurationChatFragment() {
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
                LoginDataManager.setChatId(adapter.get(position).getUid());
                ((MainIntroActivity)getActivity()).next();
            }
        });

        return layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GetDialogs getDialogs = new GetDialogs(getContext());
        getDialogs.getDialogs();
    }

    private VKList<VKApiDialog> dialogs;
    private List<Item> items;

    private final BroadcastReceiver briefSyncFinishedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, Intent intent) {
            VKList<VKApiUser> users = intent.getParcelableExtra(GetUsers.EXTRA_BRIEF);

            for (int i = 0; i < dialogs.size(); i++) {
                String title;
                String content = dialogs.get(i).message.body;
                VKApiUser user = users.getById(dialogs.get(i).message.user_id);

                if(!dialogs.get(i).message.title.isEmpty())
                    title = dialogs.get(i).message.title;
                else {
                    title = user.first_name + " " + user.last_name;
                }
                if(content.isEmpty()) {
                    content = getContext().getString(R.string.unsupported_media);
                }

                items.add(new Item(dialogs.get(i).getId(), title, content, user.photo_100));
            }

            adapter.setItems(items);
        }
    };

    private final BroadcastReceiver dialogsSyncFinishedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, Intent intent) {
            dialogs = intent.getParcelableExtra(GetDialogs.EXTRA_DIALOGS);
            items = new ArrayList<>();

            long[] userIds = new long[dialogs.size()];
            for (int i = 0; i < dialogs.size(); i++) {
                userIds[i] = dialogs.get(i).message.user_id;
            }

            GetUsers getUsers = new GetUsers(getContext());
            getUsers.getUsersBrief(userIds);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        try {
            getContext().registerReceiver(briefSyncFinishedReceiver,
                    new IntentFilter(GetUsers.USER_BRIEF_GET_SYNC_FINISHED));
            getContext().registerReceiver(dialogsSyncFinishedReceiver,
                    new IntentFilter(GetDialogs.DIALOGS_GET_SYNC_FINISHED));
        } catch (Exception ignored) { }
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            getContext().unregisterReceiver(briefSyncFinishedReceiver);
            getContext().unregisterReceiver(dialogsSyncFinishedReceiver);
        } catch (Exception ignored) { }
    }
}
