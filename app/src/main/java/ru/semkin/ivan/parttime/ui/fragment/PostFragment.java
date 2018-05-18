package ru.semkin.ivan.parttime.ui.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.vk.sdk.api.model.VKApiComment;
import com.vk.sdk.api.model.VKList;

import java.util.List;

import ru.semkin.ivan.parttime.GlideApp;
import ru.semkin.ivan.parttime.R;
import ru.semkin.ivan.parttime.api.request.GenericCallback;
import ru.semkin.ivan.parttime.api.request.SimpleCallback;
import ru.semkin.ivan.parttime.api.request.VKListCallback;
import ru.semkin.ivan.parttime.api.request.Wall;
import ru.semkin.ivan.parttime.data.UserRepository;
import ru.semkin.ivan.parttime.model.Comment;
import ru.semkin.ivan.parttime.model.Post;
import ru.semkin.ivan.parttime.model.User;
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

    private ImageView mUserPic;
    private TextView mAuthor;
    private TextView mDate;
    private TextView mBody;
    //private ImageView image; //todo implement someday
    private CommentsListAdapter mAdapter;
    private SwipeRefreshLayout mRefreshLayout;
    private TextView mEditMessage;
    private AppCompatImageButton mSendButton;
    private RecyclerView mRecyclerView;
    private UserRepository mUserRepository;
    private int mPostId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_post, container, false);

        mUserPic = layout.findViewById(R.id.userpic);
        mAuthor = layout.findViewById(R.id.author);
        mDate = layout.findViewById(R.id.date);
        mBody = layout.findViewById(R.id.body);
        //image = layout.findViewById(R.id.image);
        mRefreshLayout = layout.findViewById(R.id.swipeRefreshLayout);
        mEditMessage = layout.findViewById(R.id.edit_message);
        mSendButton = layout.findViewById(R.id.send_message);

        mRecyclerView = layout.findViewById(R.id.recyclerview);
        mAdapter = new CommentsListAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mUserRepository = new UserRepository(getContext());

        setupUi();

        ActivityUtil.setActionTitle(R.string.view_post, (AppCompatActivity)getActivity());

        return layout;
    }

    private void setupUi() {
        if (getArguments() != null) {
            mPostId = getArguments().getInt(EXTRA_ID);

            PostViewModel postViewModel = ViewModelProviders.of(this).get(PostViewModel.class);
            postViewModel.loadById(mPostId).observe(this, new Observer<Post>() {
                @Override
                public void onChanged(@Nullable final Post post) {
                    if (post != null) {
                        mDate.setText(Util.formatDate(getContext(), post.getDate()));
                        mBody.setText(post.getText());

                        mUserRepository.loadById(post.getFrom_id(), new GenericCallback<User>() {
                            @Override
                            public void onFinished(User user) {
                                mAuthor.setText(
                                        String.format("%s %s", user.getFirst_name(), user.getLast_name()));
                                if(getContext() != null) {
                                    GlideApp
                                            .with(getContext())
                                            .load(user.getPhoto_100())
                                            .apply(RequestOptions.circleCropTransform())
                                            .into(mUserPic);
                                }
                            }
                        });
                    }
                }
            });

            mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    refresh();
                }
            });

            mSendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!mEditMessage.getText().toString().trim().isEmpty()) {
                        Wall.createComment(new SimpleCallback() {
                            @Override
                            public void onFinished() {
                                mEditMessage.setText("");
                                refresh();
                            }
                        }, mEditMessage.getText().toString(), mPostId);
                    }
                }
            });

            refresh();
        }

        CommentViewModel commentViewModel = ViewModelProviders.of(this).get(CommentViewModel.class);
        commentViewModel.getAllComments().observe(this, new Observer<List<Comment>>() {
            @Override
            public void onChanged(@Nullable final List<Comment> comments) {
                // Update the cached copy of the comments in the adapter.
                mAdapter.setItems(comments);
            }
        });
    }

    private void refresh() {
        mRefreshLayout.setRefreshing(true);
        Wall.getComments(new VKListCallback<VKApiComment>() {
            @Override
            public void onFinished(VKList<VKApiComment> items) {
                mRefreshLayout.setRefreshing(false);
                mRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    public void onGlobalLayout() {
                        mRecyclerView.scrollToPosition(mRecyclerView.getAdapter().getItemCount() - 1);
                        // Unregister the listener to only call scrollToPosition once
                        mRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            }
        }, mPostId, getContext());
    }
}
