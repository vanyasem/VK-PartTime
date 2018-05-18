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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vk.sdk.api.model.VKApiPost;
import com.vk.sdk.api.model.VKList;

import java.util.List;

import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.ButterKnife;
import ru.semkin.ivan.parttime.R;
import ru.semkin.ivan.parttime.api.request.VKListCallback;
import ru.semkin.ivan.parttime.api.request.Wall;
import ru.semkin.ivan.parttime.model.Post;
import ru.semkin.ivan.parttime.ui.adapter.EmptyRecyclerView;
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

    @BindView(R.id.swipeRefreshLayout) SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recyclerview) EmptyRecyclerView recyclerView;

    private PostListAdapter mAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout =  inflater.inflate(R.layout.fragment_group, container, false);
        ButterKnife.bind(this, layout);

        mAdapter = new PostListAdapter(getContext());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter.setOnPostClickListener(new PostListAdapter.PostClickListener() {
            @Override
            public void onPostClick(int position, View v) {
                if(getActivity() != null) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(PostFragment.EXTRA_ID, mAdapter.get(position).getUid());
                    Navigation.findNavController(
                            getActivity(), R.id.nav_host_fragment).navigate(R.id.view_post, bundle);
                }
            }
        });

        PostViewModel postViewModel = ViewModelProviders.of(this).get(PostViewModel.class);
        postViewModel.getAllPosts().observe(this, new Observer<List<Post>>() {
            @Override
            public void onChanged(@Nullable final List<Post> posts) {
                // Update the cached copy of the posts in the mAdapter.
                mAdapter.setPosts(posts);
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
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
