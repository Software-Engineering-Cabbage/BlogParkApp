package com.example.my_test6.blink_module.blinkBean;

public class blinkInfo {
    String Id;
    String Content;
    String IsPrivate;
    String IsLucky;
    String CommentCount;
    String DateAdded;
    String UserAlias;
    String UserDisplayName;
    String UserIconUrl;
    String UserId;
    String UserGuid;

    public blinkInfo(String id, String content, String isPrivate, String isLucky, String commentCount, String dateAdded, String userAlias, String userDisplayName, String userIconUrl, String userId, String userGuid) {
        Id = id;
        Content = content;
        IsPrivate = isPrivate;
        IsLucky = isLucky;
        CommentCount = commentCount;
        DateAdded = dateAdded;
        UserAlias = userAlias;
        UserDisplayName = userDisplayName;
        UserIconUrl = userIconUrl;
        UserId = userId;
        UserGuid = userGuid;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getIsPrivate() {
        return IsPrivate;
    }

    public void setIsPrivate(String isPrivate) {
        IsPrivate = isPrivate;
    }

    public String getIsLucky() {
        return IsLucky;
    }

    public void setIsLucky(String isLucky) {
        IsLucky = isLucky;
    }

    public String getCommentCount() {
        return CommentCount;
    }

    public void setCommentCount(String commentCount) {
        CommentCount = commentCount;
    }

    public String getDateAdded() {
        return DateAdded;
    }

    public void setDateAdded(String dateAdded) {
        DateAdded = dateAdded;
    }

    public String getUserAlias() {
        return UserAlias;
    }

    public void setUserAlias(String userAlias) {
        UserAlias = userAlias;
    }

    public String getUserDisplayName() {
        return UserDisplayName;
    }

    public void setUserDisplayName(String userDisplayName) {
        UserDisplayName = userDisplayName;
    }

    public String getUserIconUrl() {
        return UserIconUrl;
    }

    public void setUserIconUrl(String userIconUrl) {
        UserIconUrl = userIconUrl;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserGuid() {
        return UserGuid;
    }

    public void setUserGuid(String userGuid) {
        UserGuid = userGuid;
    }
}
