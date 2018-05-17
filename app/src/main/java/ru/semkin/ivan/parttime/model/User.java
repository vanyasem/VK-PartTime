package ru.semkin.ivan.parttime.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.vk.sdk.api.model.VKApiUser;

/**
 * Created by Ivan Semkin on 5/10/18
 */
@Entity
public class User {

    public User(String first_name, String last_name, String photo_100) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.photo_100 = photo_100;
    }

    public User(VKApiUser user) {
        this.uid = user.id;
        this.first_name = user.first_name;
        this.last_name = user.last_name;
        this.photo_100 = user.photo_100;
    }

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "first_name")
    private String first_name;

    @ColumnInfo(name = "last_name")
    private String last_name;

    @ColumnInfo(name = "photo_100")
    private String photo_100;


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhoto_100() {
        return photo_100;
    }

    public void setPhoto_100(String photo_100) {
        this.photo_100 = photo_100;
    }
}
