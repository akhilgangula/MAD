package com.example.class08;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FireStoreConnector {
    static FireStoreConnector instance;
    FirebaseFirestore db;
    Map<String, Forum> mapIdToForum = new HashMap<>();

    public static FireStoreConnector getInstance() {
        if (instance == null) {
            instance = new FireStoreConnector();
            instance.init();
        }
        return instance;
    }

    public void init() {
        db = FirebaseFirestore.getInstance();
    }

    public Task<DocumentReference> addUser(String uid, String displayName) {
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put(Constants.UID, uid);
        user.put(Constants.DISPLAY_NAME, displayName);

        return db.collection(Constants.USERS_COLLECTION).add(user);
    }

    public Task<DocumentReference> addForum(String title, String desc, String uuid, String dateTime) {
        // Create a new user with a first and last name
        Map<String, Object> forum = new HashMap<>();
        forum.put(Constants.TITLE, title);
        forum.put(Constants.UUID, uuid);
        forum.put(Constants.DESC, desc);
        forum.put(Constants.DATE, dateTime);

        return db.collection(Constants.FORUM_COLLECTION).add(forum);
    }

    public void getUsers(IStoreAction storeAction) {
        Map<String, String> userIdToName = new HashMap<>();
        db.collection(Constants.USERS_COLLECTION)
                .addSnapshotListener((snapshots, e) -> {
                    if (e != null) {
                        Log.w(this.getClass().toString(), "getUsers listen:error", e);
                        return;
                    }

                    for (DocumentChange dc : snapshots.getDocumentChanges()) {
                        String uid = dc.getDocument().getData().get(Constants.UID).toString();
                        String name = dc.getDocument().getData().get(Constants.DISPLAY_NAME).toString();
                        userIdToName.put(uid, name);
                    }
                    storeAction.onLoadUsers(userIdToName);
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getForums(IStoreAction storeAction) {
        new ArrayList<>();
        db.collection(Constants.FORUM_COLLECTION)
                .addSnapshotListener((snapshots, e) -> {
                    if (e != null) {
                        Log.w(this.getClass().toString(), "getForums listen:error", e);
                        return;
                    }

                    for (DocumentChange dc : snapshots.getDocumentChanges()) {
                        switch (dc.getType()) {
                            case ADDED:
                            case MODIFIED:
                                String title = dc.getDocument().getData().get(Constants.TITLE).toString();
                                String desc = dc.getDocument().getData().get(Constants.DESC).toString();
                                String uuid = dc.getDocument().getData().get(Constants.UUID).toString();
                                String date = dc.getDocument().getData().get(Constants.DATE).toString();
                                Forum forum = new Forum(dc.getDocument().getId(), uuid, title, desc, date);
                                mapIdToForum.put(dc.getDocument().getId(), forum);
                                break;
                            case REMOVED:
                                mapIdToForum.remove(dc.getDocument().getId());
                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + dc.getType());
                        }
                    }
                    List<Forum> forums = mapIdToForum.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toList());
                    storeAction.onLoadForum(forums);
                });
    }

    public Task<Void> deleteForums(Forum forum) {
        return db.collection(Constants.FORUM_COLLECTION)
                .document(forum.id)
                .delete();
    }

}