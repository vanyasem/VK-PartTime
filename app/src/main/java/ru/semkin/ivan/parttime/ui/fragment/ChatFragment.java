package ru.semkin.ivan.parttime.ui.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.vk.sdk.api.model.VKApiMessage;
import com.vk.sdk.api.model.VKList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.semkin.ivan.parttime.R;
import ru.semkin.ivan.parttime.api.request.GenericCallback;
import ru.semkin.ivan.parttime.api.request.Messages;
import ru.semkin.ivan.parttime.api.request.VKListCallback;
import ru.semkin.ivan.parttime.model.Message;
import ru.semkin.ivan.parttime.ui.adapter.EmptyRecyclerView;
import ru.semkin.ivan.parttime.ui.adapter.MessageListAdapter;
import ru.semkin.ivan.parttime.ui.model.MessageViewModel;
import ru.semkin.ivan.parttime.util.ActivityUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {


    public ChatFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.edit_message) TextView editMessage;
    @BindView(R.id.send_message) AppCompatImageButton sendButton;
    @BindView(R.id.swipeRefreshLayout) SwipyRefreshLayout refreshLayout;
    @BindView(R.id.recyclerview) EmptyRecyclerView recyclerView;

    @OnClick(R.id.send_message)
    public void sendMessage() {
        if(!editMessage.getText().toString().trim().isEmpty()) {
            Messages.send(new GenericCallback<Long>() {
                @Override
                public void onFinished(Long id) {
                    editMessage.setText("");
                    refresh();
                }
            }, editMessage.getText().toString());
        }
    }

    private MessageListAdapter mAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this, layout);

        mAdapter = new MessageListAdapter(getContext());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setEmptyView(layout.findViewById(android.R.id.empty));

        MessageViewModel messageViewModel = ViewModelProviders.of(this).get(MessageViewModel.class);
        messageViewModel.getAllMessages().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(@Nullable final List<Message> messages) {
                // Update the cached copy of the messages in the mAdapter.
                mAdapter.setMessages(messages);
            }
        });

        refreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                refresh();
            }
        });

        ActivityUtil.setActionTitle(R.string.nav_chat, (AppCompatActivity)getActivity());

        refresh();

        return layout;
    }

    private void refresh() {
        refreshLayout.setRefreshing(true);
        Messages.getHistory(new VKListCallback<VKApiMessage>() {
            @Override
            public void onFinished(VKList<VKApiMessage> items) {
                refreshLayout.setRefreshing(false);
                recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    public void onGlobalLayout() {
                        recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
                        // Unregister the listener to only call scrollToPosition once
                        recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            }
        }, getContext());
    }
}
