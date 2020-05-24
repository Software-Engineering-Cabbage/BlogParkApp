package com.example.my_test6.blink_module.blinkBean;

public class blinkResponseInfo {
    String Id;
    String Content;
    String DateAdded;
    String StatusId;
    String UserAlias;
    String UserDisplayName;
    String UserIconUrl;
    String UserId;
    String UserGuid;

    public blinkResponseInfo(String id, String content, String dateAdded, String statusId, String userAlias, String userDisplayName, String userIconUrl, String userId, String userGuid) {
        Id = id;
        Content = content;
        DateAdded = dateAdded;
        StatusId = statusId;
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

    public String getDateAdded() {
        String Date_temp = DateAdded.substring(0,16);
        char[] Date = Date_temp.toCharArray();
        Date[10] = ' ';
        return String.valueOf(Date);
    }

    public void setDateAdded(String dateAdded) {
        DateAdded = dateAdded;
    }

    public String getStatusId() {
        return StatusId;
    }

    public void setStatusId(String statusId) {
        StatusId = statusId;
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
