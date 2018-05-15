package ru.semkin.ivan.parttime.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Ivan Semkin on 5/10/18
 */
@Entity
public class Message {

    public Message(String body, long user_id, long date, int read_state, int out) {
        this.body = body;
        this.user_id = user_id;
        this.date = date;
        this.read_state = read_state;
        this.out = out;
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
    private int read_state;

    @ColumnInfo(name = "out")
    private int out;

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

    public int getRead_state() {
        return read_state;
    }

    public void setRead_state(int read_state) {
        this.read_state = read_state;
    }

    public int getOut() {
        return out;
    }

    public void setOut(int out) {
        this.out = out;
    }
}
