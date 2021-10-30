package com.example.class08;

import java.util.List;
import java.util.Map;

public interface IStoreAction {
    void onLoadUsers(Map<String, String> userIdToName);
    void onLoadForum(List<Forum> forums);
}