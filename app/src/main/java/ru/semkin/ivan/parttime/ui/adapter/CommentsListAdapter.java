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

import butterknife.BindView;
import butterknife.ButterKnife;
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

    private CommentClickListener mClickListener;

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.image) ImageView image;
        @BindView(R.id.title) TextView textTitle;
        @BindView(R.id.content) TextView textContent;
        @BindView(R.id.date) TextView textDate;

        private ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mClickListener != null)
                mClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    private final LayoutInflater mInflater;
    private final UserRepository mUserRepository;
    private List<Comment> mComments; // Cached copy of comments

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
            holder.textTitle.setText(R.string.no_title);
            holder.textContent.setText(R.string.no_content);
            holder.textDate.setText(R.string.no_date);
        }
    }

    public void setOnItemClickListener(CommentClickListener clickListener) {
        this.mClickListener = clickListener; //todo implement
    }

    public void setItems(List<Comment> comments){
        mComments = comments;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mItems has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mComments != null)
            return mComments.size();
        else return 0;
    }

    @SuppressWarnings("WeakerAccess")
    public interface CommentClickListener {
        void onItemClick(int position, View v);
    }
}
