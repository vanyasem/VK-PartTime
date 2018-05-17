package ru.semkin.ivan.parttime.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.vk.sdk.api.model.VKApiMessage;

/**
 * Created by Ivan Semkin on 5/10/18
 */
@Entity
public class Message {

    public Message(String body, long user_id, long date, boolean read_state, boolean out) {
        this.body = body;
        this.user_id = user_id;
        this.date = date;
        this.read_state = read_state;
        this.out = out;
    }

    public Message(VKApiMessage message) {
        this.uid = message.id;
        this.body = message.body;
        this.user_id = message.user_id;
        this.date = message.date;
        this.read_state = message.read_state;
        this.out = message.out;
    }

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "body")
    private String body;

    @ColumnInfo(name = "user_id")
    private long user_id;

    @ColumnInfo(name = "date")
    private long date;

    @ColumnInfo(name = "read_state")
    private boolean read_state;

    @ColumnInfo(name = "out")
    private boolean out;

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

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public boolean isRead_state() {
        return read_state;
    }

    public void setRead_state(boolean read_state) {
        this.read_state = read_state;
    }

    public boolean isOut() {
        return out;
    }

    public void setOut(boolean out) {
        this.out = out;
    }
}
