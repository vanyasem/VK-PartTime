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

    public final static int TYPE_MESSAGE = 0;
    public final static int TYPE_POST = 1;

    public final static int TS_MESSAGE = 86400;
    public final static int TS_POST = 172800;

    public Task(String body, long date, int type, boolean done) {
        this.body = body;
        this.date = date;
        this.type = type;
        this.done = done;
    }

    public Task(VKApiMessage message) {
        this.uid = message.id;
        this.body = message.body;
        this.type = TYPE_MESSAGE;
        this.date = message.date;
    }

    public Task(VKApiPost post) {
        this.uid = post.id;
        this.body = post.text;
        this.type = TYPE_POST;
        this.date = post.date;
    }

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "body")
    private String body;

    @ColumnInfo(name = "date")
    private long date;

    @ColumnInfo(name = "type")
    private int type;

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

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
