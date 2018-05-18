package ru.semkin.ivan.parttime.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.semkin.ivan.parttime.R;
import ru.semkin.ivan.parttime.api.request.GenericCallback;
import ru.semkin.ivan.parttime.data.UserRepository;
import ru.semkin.ivan.parttime.model.Post;
import ru.semkin.ivan.parttime.model.User;
import ru.semkin.ivan.parttime.util.Util;

/**
 * Created by Ivan Semkin on 5/11/18
 */
public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostViewHolder> {

    private PostClickListener clickListener;

    class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.text_date) TextView textDate;
        @BindView(R.id.text_body) TextView textBody;
        @BindView(R.id.text_author) TextView textAuthor;
        @BindView(R.id.comments) TextView comments;
        @BindView(R.id.image) ImageView image;

        private PostViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(clickListener != null)
                clickListener.onPostClick(getAdapterPosition(), v);
        }
    }

    private final LayoutInflater mInflater;
    private final UserRepository mUserRepository;
    private List<Post> mPosts; // Cached copy of posts

    public PostListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mUserRepository = new UserRepository(context);
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_post, parent, false);
        return new PostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final @NonNull PostViewHolder holder, int position) {
        if (mPosts != null) {
            Post current = mPosts.get(position);
            holder.textBody.setText(current.getText());
            holder.textDate.setText(
                    Util.formatDate(holder.textBody.getContext(), current.getDate()));
            holder.comments.setText(String.valueOf(current.getComments_count()));

            mUserRepository.loadById(current.getFrom_id(), new GenericCallback<User>() {
                @Override
                public void onFinished(User user) {
                    holder.textAuthor.setText(
                            String.format("%s %s", user.getFirst_name(), user.getLast_name()));
                }
            });
        } else {
            // Covers the case of data not being ready yet.
            holder.textBody.setText("No ");
            holder.textDate.setText("Post");
            holder.textAuthor.setText("No Author");
            holder.comments.setText("-1");
        }
    }

    public void setOnPostClickListener(PostClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setPosts(List<Post> posts){
        mPosts = posts;
        notifyDataSetChanged();
    }

    public Post get(int position) {
        return mPosts.get(position);
    }


    // getItemCount() is called many times, and when it is first called,
    // mPosts has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mPosts != null)
            return mPosts.size();
        else return 0;
    }

    public interface PostClickListener {
        void onPostClick(int position, View v);
    }
}
