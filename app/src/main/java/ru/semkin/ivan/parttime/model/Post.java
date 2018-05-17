package ru.semkin.ivan.parttime.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.vk.sdk.api.model.VKApiPost;

/**
 * Created by Ivan Semkin on 5/10/18
 */
@Entity
public class Post {

    public Post(String text, long date) {
        this.text = text;
        this.date = date;
    }

    public Post(VKApiPost post) {
        this.uid = post.id;
        this.text = post.text;
        this.date = post.date;
    }

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "text")
    private String text;

    @ColumnInfo(name = "date")
    private long date;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
