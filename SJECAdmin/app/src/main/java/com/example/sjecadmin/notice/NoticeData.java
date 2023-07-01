package com.example.sjecadmin.notice;

public class NoticeData {
    String title,image,time,key,date;

    public NoticeData() {

    }

    public NoticeData(String title, String image, String time, String key, String date) {
        this.title = title;
        this.image = image;
        this.time = time;
        this.key = key;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
