package com.example.my_test6.Bean;

public class BlogFromSearch {
    private String Title;
    private String Content;
    private  String UserName;
    private String UserAlias;
    private String PublishTime;
    private int VoteTimes;
    private int ViewTimes;
    private int CommentTimes;
    private String Uri;
    private String Id;

    public BlogFromSearch(String title, String content, String userName, String userAlias, String publishTime, int voteTimes, int viewTimes, int commentTimes, String uri, String id) {
        Title = title;
        Content = content;
        UserName = userName;
        UserAlias = userAlias;
        PublishTime = publishTime;
        VoteTimes = voteTimes;
        ViewTimes = viewTimes;
        CommentTimes = commentTimes;
        Uri = uri;
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserAlias() {
        return UserAlias;
    }

    public void setUserAlias(String userAlias) {
        UserAlias = userAlias;
    }

    public String getPublishTime() {
        return PublishTime;
    }

    public void setPublishTime(String publishTime) {
        PublishTime = publishTime;
    }

    public int getVoteTimes() {
        return VoteTimes;
    }

    public void setVoteTimes(int voteTimes) {
        VoteTimes = voteTimes;
    }

    public int getViewTimes() {
        return ViewTimes;
    }

    public void setViewTimes(int viewTimes) {
        ViewTimes = viewTimes;
    }

    public int getCommentTimes() {
        return CommentTimes;
    }

    public void setCommentTimes(int commentTimes) {
        CommentTimes = commentTimes;
    }

    public String getUri() {
        return Uri;
    }

    public void setUri(String uri) {
        Uri = uri;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
