package com.example.apipixabayimagesapp.Model;

public class ModelClass {

    private String imageUrl = "";
    private String tags = "";
    private int likes = 0;
    private int downloads = 0;
    private int views = 0;

    public ModelClass(String imageUrl, String tags, int likes, int downloads, int views) {
        this.imageUrl = imageUrl;
        this.tags = tags;
        this.likes = likes;
        this.downloads = downloads;
        this.views = views;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}
