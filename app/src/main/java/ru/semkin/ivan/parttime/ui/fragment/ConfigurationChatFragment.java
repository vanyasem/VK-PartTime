package ru.semkin.ivan.parttime.ui.fragment;


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
import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKList;

import java.util.ArrayList;
import java.util.List;

import ru.semkin.ivan.parttime.R;
import ru.semkin.ivan.parttime.api.request.Dialogs;
import ru.semkin.ivan.parttime.api.request.Users;
import ru.semkin.ivan.parttime.api.request.VKListCallback;
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
        Dialogs.getDialogs(dialogVKListCallback);
    }

    private VKList<VKApiDialog> dialogs;
    private List<Item> adapterItems;

    private VKListCallback<VKApiDialog> dialogVKListCallback = new VKListCallback<VKApiDialog>() {
        @Override
        public void onFinished(VKList<VKApiDialog> dialogs) {
            ConfigurationChatFragment.this.dialogs = dialogs;
            adapterItems = new ArrayList<>();

            long[] userIds = new long[dialogs.size()];
            for (int i = 0; i < dialogs.size(); i++) {
                userIds[i] = dialogs.get(i).message.user_id;
            }

            Users.getUsersBrief(userFullVKListCallback, userIds);
        }
    };
    private VKListCallback<VKApiUserFull> userFullVKListCallback = new VKListCallback<VKApiUserFull>() {
        @Override
        public void onFinished(VKList<VKApiUserFull> users) {
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

                adapterItems.add(new Item(dialogs.get(i).getId(), title, content, user.photo_100));
            }

            adapter.setItems(adapterItems);
        }
    };
}
