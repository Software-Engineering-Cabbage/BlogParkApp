package com.example.my_test6.edu_module.Blog;

public class BlogPosts {
    private String id;
    private String body;
    private String title;
    private String url;
    private String description;
    private String viewCount;
    private String diggCount;
    private String commentCount;
    private String author;
    private String displayName;
    private String blogId;
    private String blogUrl;
    private String avatarUrl;
    private String dateAdded;
    private String isHideName;
    private String schoolClassId;
    private String schoolClassUrl;
    private String schoolClassFullName;

    public BlogPosts(String id, String body, String title, String url, String description, String viewCount, String diggCount, String commentCount, String author, String displayName, String blogId, String blogUrl, String avatarUrl, String dateAdded, String isHideName, String schoolClassId, String schoolClassUrl, String schoolClassFullName) {
        this.id = id;
        this.body = body;
        this.title = title;
        this.url = url;
        this.description = description;
        this.viewCount = viewCount;
        this.diggCount = diggCount;
        this.commentCount = commentCount;
        this.author = author;
        this.displayName = displayName;
        this.blogId = blogId;
        this.blogUrl = blogUrl;
        this.avatarUrl = avatarUrl;
        this.dateAdded = dateAdded;
        this.isHideName = isHideName;
        this.schoolClassId = schoolClassId;
        this.schoolClassUrl = schoolClassUrl;
        this.schoolClassFullName = schoolClassFullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getDiggCount() {
        return diggCount;
    }

    public void setDiggCount(String diggCount) {
        this.diggCount = diggCount;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }

    public String getBlogUrl() {
        return blogUrl;
    }

    public void setBlogUrl(String blogUrl) {
        this.blogUrl = blogUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getIsHideName() {
        return isHideName;
    }

    public void setIsHideName(String isHideName) {
        this.isHideName = isHideName;
    }

    public String getSchoolClassId() {
        return schoolClassId;
    }

    public void setSchoolClassId(String schoolClassId) {
        this.schoolClassId = schoolClassId;
    }

    public String getSchoolClassUrl() {
        return schoolClassUrl;
    }

    public void setSchoolClassUrl(String schoolClassUrl) {
        this.schoolClassUrl = schoolClassUrl;
    }

    public String getSchoolClassFullName() {
        return schoolClassFullName;
    }

    public void setSchoolClassFullName(String schoolClassFullName) {
        this.schoolClassFullName = schoolClassFullName;
    }
}
