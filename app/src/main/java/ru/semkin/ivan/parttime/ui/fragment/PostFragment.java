package ru.semkin.ivan.parttime.ui.fragment;


import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ru.semkin.ivan.parttime.R;
import ru.semkin.ivan.parttime.data.PostRepository;
import ru.semkin.ivan.parttime.model.Post;
import ru.semkin.ivan.parttime.ui.activity.EmptyRecyclerView;
import ru.semkin.ivan.parttime.ui.adapter.ItemListAdapter;
import ru.semkin.ivan.parttime.util.ActivityUtil;
import ru.semkin.ivan.parttime.util.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostFragment extends Fragment {

    public PostFragment() {
        // Required empty public constructor
    }

    final public static String EXTRA_ID = "id";

    private ImageView userpic;
    private TextView author;
    private TextView date;
    private TextView body;
    private ImageView image;
    private ItemListAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_post, container, false);

        userpic = layout.findViewById(R.id.userpic);
        author = layout.findViewById(R.id.author);
        date = layout.findViewById(R.id.date);
        body = layout.findViewById(R.id.body);
        image = layout.findViewById(R.id.image);

        EmptyRecyclerView recyclerView = layout.findViewById(R.id.recyclerview);
        adapter = new ItemListAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        setupUi();

        ActivityUtil.setActionTitle(R.string.view_post, (AppCompatActivity)getActivity());

        return layout;
    }

    private void setupUi() {
        if (getArguments() != null) {
            int id = getArguments().getInt(EXTRA_ID);

            PostRepository postRepository = new PostRepository(getContext());
            postRepository.loadById(id).observe(this, new Observer<Post>() {
                @Override
                public void onChanged(@Nullable final Post post) {
                    date.setText(Util.formatDate(getContext(), post.getDate()));
                    body.setText(post.getText());
                }
            });
        }
    }
}
