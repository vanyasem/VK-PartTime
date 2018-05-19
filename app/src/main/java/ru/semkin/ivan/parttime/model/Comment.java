package ru.semkin.ivan.parttime.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.vk.sdk.api.model.VKApiComment;

/**
 * Created by Ivan Semkin on 5/10/18
 */
@SuppressWarnings("unused")
@Entity
public class Comment {

    public Comment(int from_id, long date, String text, long post_id) {
        this.from_id = from_id;
        this.date = date;
        this.text = text;
        this.post_id = post_id;
    }

    public Comment(VKApiComment comment, long post_id) {
        this.uid = comment.id;
        this.from_id = comment.from_id;
        this.date = comment.date;
        this.text = comment.text;
        this.post_id = post_id;
    }

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "from_id")
    private int from_id;

    @ColumnInfo(name = "date")
    private long date;

    @ColumnInfo(name = "text")
    private String text;

    @ColumnInfo(name = "post_id")
    private long post_id;

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

    public long getPost_id() {
        return post_id;
    }

    public void setPost_id(long post_id) {
        this.post_id = post_id;
    }
}
