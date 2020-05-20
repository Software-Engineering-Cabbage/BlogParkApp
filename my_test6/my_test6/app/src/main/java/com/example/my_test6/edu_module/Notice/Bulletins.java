package com.example.my_test6.edu_module.Notice;

public class Bulletins {
    private String bulletinId;
    private String content;
    private String publisher;
    private String blogUrl;
    private String dateAdded;

    public Bulletins(String bulletinId, String content, String publisher, String blogUrl, String dateAdded) {
        this.bulletinId = bulletinId;
        this.content = content;
        this.publisher = publisher;
        this.blogUrl = blogUrl;
        this.dateAdded = dateAdded;
    }
    public Bulletins(){

    }

    public String getBulletinId() {
        return bulletinId;
    }

    public void setBulletinId(String bulletinId) {
        this.bulletinId = bulletinId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getBlogUrl() {
        return blogUrl;
    }

    public void setBlogUrl(String blogUrl) {
        this.blogUrl = blogUrl;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }
}
