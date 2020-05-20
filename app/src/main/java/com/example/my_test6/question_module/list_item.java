package com.example.my_test6.question_module;

public class list_item {
    private int Qid;
    private int DealFlag;
    private int ViewCount;
    private String Title;
    private String Content;
    private String Summary;
    private String DateAdded;
    //private String Supply;
    //private String Addition;
    private String ConvertedContent;
    private String Tags;
    private int FormatType;
    private int AnswerCount;
    private int UserId;
    private int Award;
    private int DiggCount;
    private int BuryCount;
    private boolean IsBest;
    private int AnswerUserId;
    private int Flags;
    private String DateEnded;

    private String QUrl;

    private QuestionUserInfo_class QuestionUserInfo;
    public static class QuestionUserInfo_class {
        private int UserID;
        private String UserName;
        private String LoginName;
        private String IconName;
        private String Alias;
        private int QScore;
        private String DateAdded;
        private int UserWealth;
        private int UserReputation;
        private boolean IsActive;
        private String UCUserID;
        private boolean IsPublishHomeActive;
        private String Face;

        public QuestionUserInfo_class(int userID, String userName, String loginName, String iconName, String alias, int QScore, String dateAdded, int userWealth, int userReputation, boolean isActive, String UCUserID, boolean isPublishHomeActive, String face) {
            UserID = userID;
            UserName = userName;
            LoginName = loginName;
            IconName = iconName;
            Alias = alias;
            this.QScore = QScore;
            DateAdded = dateAdded;
            UserWealth = userWealth;
            UserReputation = userReputation;
            IsActive = isActive;
            this.UCUserID = UCUserID;
            IsPublishHomeActive = isPublishHomeActive;
            Face = face;
        }

        public int getUserID() {
            return UserID;
        }

        public void setUserID(int userID) {
            UserID = userID;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String userName) {
            UserName = userName;
        }

        public String getLoginName() {
            return LoginName;
        }

        public void setLoginName(String loginName) {
            LoginName = loginName;
        }

        public String getIconName() {
            return IconName;
        }

        public void setIconName(String iconName) {
            IconName = iconName;
        }

        public String getAlias() {
            return Alias;
        }

        public void setAlias(String alias) {
            Alias = alias;
        }

        public int getQScore() {
            return QScore;
        }

        public void setQScore(int QScore) {
            this.QScore = QScore;
        }

        public String getDateAdded() {
            return DateAdded;
        }

        public void setDateAdded(String dateAdded) {
            DateAdded = dateAdded;
        }

        public int getUserWealth() {
            return UserWealth;
        }

        public void setUserWealth(int userWealth) {
            UserWealth = userWealth;
        }

        public int getUserReputation() {
            return UserReputation;
        }

        public void setUserReputation(int userReputation) {
            UserReputation = userReputation;
        }

        public boolean isActive() {
            return IsActive;
        }

        public void setActive(boolean active) {
            IsActive = active;
        }

        public String getUCUserID() {
            return UCUserID;
        }

        public void setUCUserID(String UCUserID) {
            this.UCUserID = UCUserID;
        }

        public boolean isPublishHomeActive() {
            return IsPublishHomeActive;
        }

        public void setPublishHomeActive(boolean publishHomeActive) {
            IsPublishHomeActive = publishHomeActive;
        }

        public String getFace() {
            return Face;
        }

        public void setFace(String face) {
            Face = face;
        }
    }

    public list_item() {
    }

    public list_item(int qid, int dealFlag, int viewCount, String title, String content, String summary, String dateAdded, String convertedContent, String tags, int formatType, int answerCount, int userId, int award, int diggCount, int buryCount, boolean isBest, int answerUserId, int flags, String dateEnded, String QUrl, QuestionUserInfo_class questionUserInfo) {
        Qid = qid;
        DealFlag = dealFlag;
        ViewCount = viewCount;
        Title = title;
        Content = content;
        Summary = summary;
        DateAdded = dateAdded;
        ConvertedContent = convertedContent;
        Tags = tags;
        FormatType = formatType;
        AnswerCount = answerCount;
        UserId = userId;
        Award = award;
        DiggCount = diggCount;
        BuryCount = buryCount;
        IsBest = isBest;
        AnswerUserId = answerUserId;
        Flags = flags;
        DateEnded = dateEnded;
        this.QUrl = QUrl;
        QuestionUserInfo = questionUserInfo;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public QuestionUserInfo_class getQuestionUserInfo() {
        return QuestionUserInfo;
    }

    public void setQuestionUserInfo(QuestionUserInfo_class questionUserInfo) {
        QuestionUserInfo = questionUserInfo;
    }

    public String getDateAdded() {
        return DateAdded;
    }

    public void setDateAdded(String dateAdded) {
        DateAdded = dateAdded;
    }

    public int getAnswerCount() {
        return AnswerCount;
    }

    public void setAnswerCount(int answerCount) {
        AnswerCount = answerCount;
    }

    public String getQUrl() {
        return QUrl;
    }

    public void setQUrl(String QUrl) {
        this.QUrl = QUrl;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }
}
