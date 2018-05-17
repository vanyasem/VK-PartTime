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
import ru.semkin.ivan.parttime.model.Item;

/**
 * Created by Ivan Semkin on 5/11/18
 */
public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private final ImageView image;
        private final TextView textTitle;
        private final TextView textContent;

        private ItemViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            textTitle = itemView.findViewById(R.id.title);
            textContent = itemView.findViewById(R.id.content);
        }
    }

    private final LayoutInflater mInflater;
    private List<Item> mItems; // Cached copy of items

    public ItemListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        if (mItems != null) {
            Item current = mItems.get(position);
            holder.textTitle.setText(current.getTitle());
            holder.textContent.setText(String.valueOf(current.getContent()));
            GlideApp
                    .with(holder.image.getContext())
                    .load(current.getImage())
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.image);
        } else {
            // Covers the case of data not being ready yet.
            holder.textTitle.setText("No title");
            holder.textContent.setText("No content");
        }


    }

    public void setItems(List<Item> items){
        mItems = items;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mItems has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mItems != null)
            return mItems.size();
        else return 0;
    }
}
