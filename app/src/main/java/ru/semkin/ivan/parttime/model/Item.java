package ru.semkin.ivan.parttime.model;

/**
 * Created by Ivan Semkin on 5/10/18
 */
public class Item {

    public Item(long uid, String title, String content, String image) {
        this.uid = uid;
        this.title = title;
        this.content = content;
        this.image = image;
    }

    private long uid;
    private String title;
    private String content;
    private String image;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
