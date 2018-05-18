package ru.semkin.ivan.parttime.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import ru.semkin.ivan.parttime.GlideApp;
import ru.semkin.ivan.parttime.R;
import ru.semkin.ivan.parttime.api.request.GenericCallback;
import ru.semkin.ivan.parttime.data.UserRepository;
import ru.semkin.ivan.parttime.model.Comment;
import ru.semkin.ivan.parttime.model.User;
import ru.semkin.ivan.parttime.util.Util;

/**
 * Created by Ivan Semkin on 5/11/18
 */
public class CommentsListAdapter extends RecyclerView.Adapter<CommentsListAdapter.ItemViewHolder> {

    private CommentClickListener clickListener;

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView image;
        private final TextView textTitle;
        private final TextView textContent;
        private final TextView textDate;

        private ItemViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            textTitle = itemView.findViewById(R.id.title);
            textContent = itemView.findViewById(R.id.content);
            textDate = itemView.findViewById(R.id.date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(clickListener != null)
                clickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    private final LayoutInflater mInflater;
    private List<Comment> mComments; // Cached copy of comments
    private UserRepository mUserRepository;

    public CommentsListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mUserRepository = new UserRepository(context);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final @NonNull ItemViewHolder holder, int position) {
        if (mComments != null) {
            Comment current = mComments.get(position);
            holder.textContent.setText(String.valueOf(current.getText()));
            holder.textDate.setText(
                    Util.formatDate(holder.textDate.getContext(), current.getDate()));

            mUserRepository.loadById(current.getFrom_id(), new GenericCallback<User>() {
                @Override
                public void onFinished(User user) {
                    holder.textTitle.setText(
                            String.format("%s %s", user.getFirst_name(), user.getLast_name()));
                    GlideApp
                            .with(holder.image.getContext())
                            .load(user.getPhoto_100())
                            .apply(RequestOptions.circleCropTransform())
                            .into(holder.image);
                }
            });
        } else {
            // Covers the case of data not being ready yet.
            holder.textTitle.setText("No title");
            holder.textContent.setText("No content");
            holder.textDate.setText("No date");
        }
    }

    public void setOnItemClickListener(CommentClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setItems(List<Comment> comments){
        mComments = comments;
        notifyDataSetChanged();
    }

    public Comment get(int position) {
        return mComments.get(position);
    }

    // getItemCount() is called many times, and when it is first called,
    // mItems has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mComments != null)
            return mComments.size();
        else return 0;
    }

    public interface CommentClickListener {
        void onItemClick(int position, View v);
    }
}
