package ru.semkin.ivan.parttime.ui.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vk.sdk.api.model.VKApiComment;
import com.vk.sdk.api.model.VKList;

import java.util.List;

import ru.semkin.ivan.parttime.R;
import ru.semkin.ivan.parttime.api.request.VKListCallback;
import ru.semkin.ivan.parttime.api.request.Wall;
import ru.semkin.ivan.parttime.model.Comment;
import ru.semkin.ivan.parttime.model.Post;
import ru.semkin.ivan.parttime.ui.adapter.CommentsListAdapter;
import ru.semkin.ivan.parttime.ui.model.CommentViewModel;
import ru.semkin.ivan.parttime.ui.model.PostViewModel;
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

    private CommentViewModel mCommentViewModel;

    private ImageView userpic;
    private TextView author;
    private TextView date;
    private TextView body;
    private ImageView image; //todo implement someday
    private CommentsListAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private int id;

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
        refreshLayout = layout.findViewById(R.id.swipeRefreshLayout);

        RecyclerView recyclerView = layout.findViewById(R.id.recyclerview);
        adapter = new CommentsListAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        setupUi();

        ActivityUtil.setActionTitle(R.string.view_post, (AppCompatActivity)getActivity());

        return layout;
    }

    private void setupUi() {
        if (getArguments() != null) {
            id = getArguments().getInt(EXTRA_ID);

            PostViewModel postViewModel = ViewModelProviders.of(this).get(PostViewModel.class);
            postViewModel.loadById(id).observe(this, new Observer<Post>() {
                @Override
                public void onChanged(@Nullable final Post post) {
                    author.setText(String.valueOf(post.getFrom_id()));
                    date.setText(Util.formatDate(getContext(), post.getDate()));
                    body.setText(post.getText());
                }
            });

            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    refresh();
                }
            });

            refresh();
        }

        mCommentViewModel = ViewModelProviders.of(this).get(CommentViewModel.class);
        mCommentViewModel.getAllComments().observe(this, new Observer<List<Comment>>() {
            @Override
            public void onChanged(@Nullable final List<Comment> comments) {
                // Update the cached copy of the comments in the adapter.
                adapter.setItems(comments);
            }
        });
    }

    private void refresh() {
        refreshLayout.setRefreshing(true);
        Wall.getComments(new VKListCallback<VKApiComment>() {
            @Override
            public void onFinished(VKList<VKApiComment> items) {
                refreshLayout.setRefreshing(false);
            }
        }, id, getContext());
    }
}
