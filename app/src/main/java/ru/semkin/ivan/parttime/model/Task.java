package ru.semkin.ivan.parttime.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.vk.sdk.api.model.VKApiMessage;
import com.vk.sdk.api.model.VKApiPost;

/**
 * Created by Ivan Semkin on 5/10/18
 */
@Entity
public class Task {

    public Task(String body, long due, boolean done) {
        this.body = body;
        this.due = due;
        this.done = done;
    }

    public Task(VKApiMessage message) {
        this.uid = message.id;
        this.body = message.body;
        this.due = message.date + 216000000;
    }

    public Task(VKApiPost post) {
        this.uid = post.id;
        this.body = post.text;
        this.due = post.date + 216000000;
    }

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "body")
    private String body;

    @ColumnInfo(name = "due")
    private long due;

    @ColumnInfo(name = "done")
    private boolean done;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getDue() {
        return due;
    }

    public void setDue(long due) {
        this.due = due;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
