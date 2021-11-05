package com.example.hw05;

import java.util.List;
import java.util.Map;

public interface IStoreAction {
    void onLoadUsers(Map<String, String> userIdToName) throws IllegalAccessException;
    void onLoadForum(List<Forum> forums) throws IllegalAccessException;
    void onLoadComment(List<Comment> comments);
}