package com.example.my_test6.edu_module.Blog;

import java.util.List;

public class Blogs {
    private int totalCount;
    private List<BlogPosts>  blogPosts;

    public Blogs(int totalCount, List<BlogPosts> blogPosts) {
        this.totalCount = totalCount;
        this.blogPosts = blogPosts;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<BlogPosts> getBlogPosts() {
        return blogPosts;
    }

    public void setBlogPosts(List<BlogPosts> blogPosts) {
        this.blogPosts = blogPosts;
    }
}
