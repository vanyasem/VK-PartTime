package ru.semkin.ivan.parttime.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.vk.sdk.api.model.VKApiPost;

/**
 * Created by Ivan Semkin on 5/10/18
 */
@SuppressWarnings("unused")
@Entity
public class Post {

    public Post(int from_id, long date, String text, int comments_count) {
        this.from_id = from_id;
        this.date = date;
        this.text = text;
        this.comments_count = comments_count;
    }

    public Post(VKApiPost post) {
        this.uid = post.id;
        this.from_id = post.from_id;
        this.date = post.date;
        this.text = post.text;
        this.comments_count = post.comments_count;
    }

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "from_id")
    private int from_id;

    @ColumnInfo(name = "date")
    private long date;

    @ColumnInfo(name = "text")
    private String text;

    @ColumnInfo(name = "comments_count")
    private int comments_count;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getFrom_id() {
        return from_id;
    }

    public void setFrom_id(int from_id) {
        this.from_id = from_id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }
}
