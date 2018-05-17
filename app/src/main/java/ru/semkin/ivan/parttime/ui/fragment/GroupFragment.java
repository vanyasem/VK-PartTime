package ru.semkin.ivan.parttime.ui.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;
import com.vk.sdk.api.model.VKApiPost;
import com.vk.sdk.api.model.VKList;

import java.util.List;

import ru.semkin.ivan.parttime.R;
import ru.semkin.ivan.parttime.api.request.VKListCallback;
import ru.semkin.ivan.parttime.api.request.Wall;
import ru.semkin.ivan.parttime.model.Post;
import ru.semkin.ivan.parttime.ui.activity.EmptyRecyclerView;
import ru.semkin.ivan.parttime.ui.adapter.PostListAdapter;
import ru.semkin.ivan.parttime.ui.model.PostViewModel;
import ru.semkin.ivan.parttime.util.ActivityUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupFragment extends Fragment {


    public GroupFragment() {
        // Required empty public constructor
    }

    private PostViewModel mPostViewModel;
    private SwipyRefreshLayout refreshLayout;
    private PostListAdapter adapter;
    private EmptyRecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout =  inflater.inflate(R.layout.fragment_group, container, false);

        recyclerView = layout.findViewById(R.id.recyclerview);
        adapter = new PostListAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mPostViewModel = ViewModelProviders.of(this).get(PostViewModel.class);
        mPostViewModel.getAllPosts().observe(this, new Observer<List<Post>>() {
            @Override
            public void onChanged(@Nullable final List<Post> posts) {
                // Update the cached copy of the posts in the adapter.
                adapter.setPosts(posts);
            }
        });

        refreshLayout = layout.findViewById(R.id.swipeRefreshLayout);
        refreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                refresh();
            }
        });

        ActivityUtil.setActionTitle(R.string.nav_group, (AppCompatActivity)getActivity());

        refresh();

        return layout;
    }

    private void refresh() {
        refreshLayout.setRefreshing(true);
        Wall.get(new VKListCallback<VKApiPost>() {
            @Override
            public void onFinished(VKList<VKApiPost> items) {
                refreshLayout.setRefreshing(false);
            }
        }, getContext());
    }
}
