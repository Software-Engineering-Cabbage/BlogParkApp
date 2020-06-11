package com.example.my_test6.edu_module.Blog;

public class ClassBlog {
    private String avater;
    private String name;
    private String title;
    private String date;
    public String read;
    public String comment;
    public String detail;

    public ClassBlog(String avater, String name, String title, String date) {
        this.avater = avater;
        this.name = name;
        this.title = title;
        this.date = date;
    }

    public String getAvater() {
        return avater;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }
}
