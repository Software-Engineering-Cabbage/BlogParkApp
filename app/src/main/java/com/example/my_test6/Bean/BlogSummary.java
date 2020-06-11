package com.example.my_test6.Bean;

public class BlogSummary {
    private int Id;
    private String Title;
    private String Url;
    private String Description;
    private String Author;
    private String BlogApp;
    private String Avatar;
    private String PostDate;
    private int ViewCount;
    private int CommentCount;
    private int DiggCount;

    public BlogSummary(int Id, String title, String url, String description, String author, String blogApp, String avatar, String postDate, int viewCount, int commentCount, int diggCount) {
        this.Id = Id;
        this.Title = title;
        Url = url;
        Description = description;
        Author = author;
        BlogApp = blogApp;
        Avatar = avatar;
        PostDate = postDate;
        ViewCount = viewCount;
        CommentCount = commentCount;
        DiggCount = diggCount;
    }

    public BlogSummary() {
    }

    public int getId() {
        return Id;
    }

    public String getTitle() {
        return Title;
    }

    public String getUrl() {
        return Url;
    }

    public String getDescription() {
        return Description;
    }

    public String getAuthor() {
        return Author;
    }

    public String getBlogApp() {
        return BlogApp;
    }

    public String getAvatar() {
        return Avatar;
    }

    public String getPostDate() {
        return PostDate;
    }

    public int getViewCount() {
        return ViewCount;
    }

    public int getCommentCount() {
        return CommentCount;
    }

    public int getDiggCount() {
        return DiggCount;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public void setBlogApp(String blogApp) {
        BlogApp = blogApp;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public void setPostDate(String postDate) {
        PostDate = postDate;
    }

    public void setViewCount(int viewCount) {
        ViewCount = viewCount;
    }

    public void setCommentCount(int commentCount) {
        CommentCount = commentCount;
    }

    public void setDiggCount(int diggCount) {
        DiggCount = diggCount;
    }
}
