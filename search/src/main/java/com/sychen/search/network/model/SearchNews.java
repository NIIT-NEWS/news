package com.sychen.search.network.model;

import com.google.gson.annotations.SerializedName;

public class SearchNews {
    @SerializedName("id")
    private Integer id;
    @SerializedName("newTitle")
    private String newTitle;
    @SerializedName("newTitleImgUrl")
    private String newTitleImgUrl;
    @SerializedName("author")
    private String author;
    @SerializedName("content")
    private String content;
    @SerializedName("date")
    private String date;
    @SerializedName("sourceName")
    private String sourceName;
    @SerializedName("webUrl")
    private String webUrl;
    @SerializedName("favorCount")
    private Integer favorCount;
    @SerializedName("type")
    private Integer type;

    @Override
    public String toString() {
        return "SearchNews{" +
                "id=" + id +
                ", newTitle='" + newTitle + '\'' +
                ", newTitleImgUrl='" + newTitleImgUrl + '\'' +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                ", sourceName='" + sourceName + '\'' +
                ", webUrl='" + webUrl + '\'' +
                ", favorCount=" + favorCount +
                ", type=" + type +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNewTitle() {
        return newTitle;
    }

    public void setNewTitle(String newTitle) {
        this.newTitle = newTitle;
    }

    public String getNewTitleImgUrl() {
        return newTitleImgUrl;
    }

    public void setNewTitleImgUrl(String newTitleImgUrl) {
        this.newTitleImgUrl = newTitleImgUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public Integer getFavorCount() {
        return favorCount;
    }

    public void setFavorCount(Integer favorCount) {
        this.favorCount = favorCount;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
