package com.example.my_test6.edu_module.Notice;

public class ClassNotice {
    private String context;
    private String date;
    public String detail;
    public String submit;
    public String head;
    public int src;
    public String author;

    public ClassNotice(String context, String date) {
        this.context = context;
        this.date = date;
    }

    public String getContext() {
        return context;
    }

    public String getDate() {
        return date;
    }
}
