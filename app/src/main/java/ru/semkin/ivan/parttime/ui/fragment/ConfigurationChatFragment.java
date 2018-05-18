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

import com.vk.sdk.api.model.VKApiDialog;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.semkin.ivan.parttime.R;
import ru.semkin.ivan.parttime.api.request.Dialogs;
import ru.semkin.ivan.parttime.api.request.Users;
import ru.semkin.ivan.parttime.api.request.VKListCallback;
import ru.semkin.ivan.parttime.model.Item;
import ru.semkin.ivan.parttime.prefs.LoginDataManager;
import ru.semkin.ivan.parttime.ui.adapter.EmptyRecyclerView;
import ru.semkin.ivan.parttime.ui.activity.MainIntroActivity;
import ru.semkin.ivan.parttime.ui.adapter.ItemListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConfigurationChatFragment extends Fragment {

    public ConfigurationChatFragment() {
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

        title.setText(R.string.work_chat);

        mAdapter.setOnItemClickListener(new ItemListAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                LoginDataManager.setChatId(mAdapter.get(position).getUid());
                if(getActivity() != null)
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

    private final VKListCallback<VKApiDialog> dialogVKListCallback = new VKListCallback<VKApiDialog>() {
        @Override
        public void onFinished(VKList<VKApiDialog> dialogs) {
            ConfigurationChatFragment.this.dialogs = dialogs;
            adapterItems = new ArrayList<>();

            long[] userIds = new long[dialogs.size()];
            for (int i = 0; i < dialogs.size(); i++) {
                userIds[i] = dialogs.get(i).message.user_id;
            }

            Users.getUsersBrief(userFullVKListCallback, getContext(), userIds);
        }
    };
    private final VKListCallback<VKApiUser> userFullVKListCallback = new VKListCallback<VKApiUser>() {
        @Override
        public void onFinished(VKList<VKApiUser> users) {
            for (int i = 0; i < dialogs.size(); i++) {
                String title;
                String content = dialogs.get(i).message.body;
                long id;
                VKApiUser user = users.getById(dialogs.get(i).message.user_id);

                if(!dialogs.get(i).message.title.isEmpty())
                    title = dialogs.get(i).message.title;
                else {
                    title = user.first_name + " " + user.last_name;
                }
                if(content.isEmpty() && getContext() != null) {
                    content = getContext().getString(R.string.unsupported_media);
                }
                if(dialogs.get(i).chatId != null)
                    id = Long.valueOf(dialogs.get(i).chatId) + 2000000000;
                else
                    id = dialogs.get(i).message.user_id;

                adapterItems.add(new Item(id, title, content, user.photo_100));
            }

            mAdapter.setItems(adapterItems);
        }
    };
}
