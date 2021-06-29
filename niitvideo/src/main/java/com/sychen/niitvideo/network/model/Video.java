package com.sychen.niitvideo.network.model;

import java.io.Serializable;

public class Video implements Serializable {

    private int id;
    private String url;
    private String title;
    private String date;
    private String visitCount;
    private String author;

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", visitCount='" + visitCount + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(String visitCount) {
        this.visitCount = visitCount;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
