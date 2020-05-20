package com.example.my_test6.edu_module.Homework;

import java.util.List;

public class ClassHomework {
    private int totalCount;
    private List<Homeworks> homeworks;

    public ClassHomework(int totalCount, List<Homeworks> homeworks) {
        this.totalCount = totalCount;
        this.homeworks = homeworks;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<Homeworks> getHomeworks() {
        return homeworks;
    }

    public void setHomeworks(List<Homeworks> homeworks) {
        this.homeworks = homeworks;
    }
}
