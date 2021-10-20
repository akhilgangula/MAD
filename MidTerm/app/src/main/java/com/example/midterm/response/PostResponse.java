package com.example.midterm.response;

import java.util.List;

public class PostResponse {
    List<PostPOJO> posts;
    String page;

    public List<PostPOJO> getPosts() {
        return posts;
    }

    public String getPage() {
        return page;
    }

    public String getPageSize() {
        return pageSize;
    }

    public String getTotalCount() {
        return totalCount;
    }

    String pageSize;
    String totalCount;
}
