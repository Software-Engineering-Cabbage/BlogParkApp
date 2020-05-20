package com.example.my_test6.edu_module.Notice;

import java.util.List;

public class NoticeList {
    private int totalCount;
    private List<Bulletins> bulletins;

    public NoticeList(int totalCount, List<Bulletins> bulletins) {
        this.totalCount = totalCount;
        this.bulletins = bulletins;
    }
    public NoticeList(){

    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<Bulletins> getBulletins() {
        return bulletins;
    }

    public void setBulletins(List<Bulletins> bulletins) {
        this.bulletins = bulletins;
    }
}
