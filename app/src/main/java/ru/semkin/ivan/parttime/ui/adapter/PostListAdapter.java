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

import ru.semkin.ivan.parttime.R;
import ru.semkin.ivan.parttime.model.Post;

/**
 * Created by Ivan Semkin on 5/11/18
 */
public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostViewHolder> {

    class PostViewHolder extends RecyclerView.ViewHolder {
        private final TextView textTitle;
        private final TextView textDate;
        private final TextView textBody;
        private final TextView textAuthor;
        private final ImageView image;
        //private final TextView textLikes; todo implement one day
        // private final ToggleButton like; todo implement one day

        private PostViewHolder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.text_title);
            textDate = itemView.findViewById(R.id.text_date);
            textBody = itemView.findViewById(R.id.text_body);
            textAuthor = itemView.findViewById(R.id.text_author);
            image = itemView.findViewById(R.id.image);
            //textLikes = itemView.findViewById(R.id.text_likes);
            //like = itemView.findViewById(R.id.icon_like);
        }
    }

    private final LayoutInflater mInflater;
    private List<Post> mPosts; // Cached copy of posts

    public PostListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_post, parent, false);
        return new PostViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        if (mPosts != null) {
            Post current = mPosts.get(position);
            holder.textTitle.setText(current.getText());
            holder.textDate.setText(String.valueOf(current.getDate()));
            //holder.textBody.setText("No ");
            //holder.textAuthor.setText("Post");
        } else {
            // Covers the case of data not being ready yet.
            holder.textTitle.setText("No ");
            holder.textDate.setText("Post");
            //holder.textBody.setText("No ");
            //holder.textAuthor.setText("Post");
        }
    }

    public void setPosts(List<Post> posts){
        mPosts = posts;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mPosts has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mPosts != null)
            return mPosts.size();
        else return 0;
    }
}