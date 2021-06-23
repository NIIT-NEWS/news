package com.sychen.collect.network.model;

import java.io.Serializable;

public class CollectNews implements Serializable {


    private NewsBean news;
    private int collectID;

    public NewsBean getNews() {
        return news;
    }

    public void setNews(NewsBean news) {
        this.news = news;
    }

    public int getCollectID() {
        return collectID;
    }

    public void setCollectID(int collectID) {
        this.collectID = collectID;
    }

    @Override
    public String toString() {
        return "CollectNews{" +
                "news=" + news +
                ", collectID=" + collectID +
                '}';
    }

    public static class NewsBean implements Serializable {

        private int id;
        private String newTitle;
        private String newTitleImgUrl;
        private String author;
        private String content;
        private String date;
        private String sourceName;
        private String webUrl;
        private int favorCount;
        private int type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
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

        public int getFavorCount() {
            return favorCount;
        }

        public void setFavorCount(int favorCount) {
            this.favorCount = favorCount;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "NewsBean{" +
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
    }
}
