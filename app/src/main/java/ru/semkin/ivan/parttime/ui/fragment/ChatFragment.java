package ru.semkin.ivan.parttime.ui.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.semkin.ivan.parttime.R;
import ru.semkin.ivan.parttime.model.Message;
import ru.semkin.ivan.parttime.ui.activity.EmptyRecyclerView;
import ru.semkin.ivan.parttime.ui.adapter.MessageListAdapter;
import ru.semkin.ivan.parttime.ui.model.MessageViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {


    public ChatFragment() {
        // Required empty public constructor
    }

    private MessageViewModel mMessageViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_chat, container, false);

        EmptyRecyclerView recyclerView = layout.findViewById(R.id.recyclerview);
        final MessageListAdapter adapter = new MessageListAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setEmptyView(layout.findViewById(android.R.id.empty));

        mMessageViewModel = ViewModelProviders.of(this).get(MessageViewModel.class);
        mMessageViewModel.getAllMessages().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(@Nullable final List<Message> messages) {
                // Update the cached copy of the messages in the adapter.
                adapter.setMessages(messages);
            }
        });

        return layout;
    }
}
