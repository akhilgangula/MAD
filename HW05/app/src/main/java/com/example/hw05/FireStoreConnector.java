package com.example.hw05;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class FireStoreConnector {
    static FireStoreConnector instance;
    FirebaseFirestore db;
    Map<String, Forum> mapIdToForum = new HashMap<>();
    Map<String, Comment> mapIdToComment = new HashMap<>();
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
        Map<String, Object> user = new HashMap<>();
        user.put(Constants.UID, uid);
        user.put(Constants.DISPLAY_NAME, displayName);

        return db.collection(Constants.USERS_COLLECTION).add(user);
    }

    public Task<DocumentReference> addForum(String title, String desc, String uuid) {
        Forum forum = new Forum(uuid, title, desc);
        return db.collection(Constants.FORUM_COLLECTION).add(forum);
    }

    public Task<Object> likeForum(String docId, String uid, Boolean like) {
        final DocumentReference documentReference = db.collection(Constants.FORUM_COLLECTION).document(docId);
        return db.runTransaction(transaction -> {
            DocumentSnapshot snapshot = transaction.get(documentReference);
            Map<String, Boolean> likeMap = (HashMap)snapshot.getData().get(Constants.LIKE);
            likeMap.put(uid, like);
            transaction.update(documentReference, Constants.LIKE, likeMap);
            return null;
        });
    }

    public Task<DocumentReference> addComment(String docId, Comment comment) {
        final DocumentReference documentReference = db.collection(Constants.FORUM_COLLECTION).document(docId);
        return documentReference.collection(Constants.COMMENTS).add(comment);
    }

    public Task<Void> deleteComment(String docId, String commentId) {
        return db.collection(Constants.FORUM_COLLECTION)
                .document(docId).collection(Constants.COMMENTS).document(commentId)
                .delete();
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
                    try {
                        storeAction.onLoadUsers(userIdToName);
                    } catch (IllegalAccessException illegalAccessException) {
                        illegalAccessException.printStackTrace();
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getComment(String documentId, IStoreAction storeAction) {
        db.collection(Constants.FORUM_COLLECTION).document(documentId).collection(Constants.COMMENTS)
                .addSnapshotListener((snapshots, e) -> {
                    if (e != null) {
                        Log.w(this.getClass().toString(), "getForums listen:error", e);
                        return;
                    }
                    for (DocumentChange dc : snapshots.getDocumentChanges()) {
                        switch (dc.getType()) {
                            case ADDED:
                            case MODIFIED:
                                Comment comment = dc.getDocument().toObject(Comment.class);
                                comment.id = dc.getDocument().getId();
                                mapIdToComment.put(comment.id, comment);
                                break;
                            case REMOVED:
                                mapIdToComment.remove(dc.getDocument().getId());
                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + dc.getType());
                        }
                    }
                    storeAction.onLoadComment(mapIdToComment.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toList()));
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getForums(IStoreAction storeAction) {
        db.collection(Constants.FORUM_COLLECTION).orderBy(Constants.DATE)
                .addSnapshotListener((snapshots, e) -> {
                    if (e != null) {
                        Log.w(this.getClass().toString(), "getForums listen:error", e);
                        return;
                    }

                    for (DocumentChange dc : snapshots.getDocumentChanges()) {
                        switch (dc.getType()) {
                            case ADDED:
                            case MODIFIED:
                                Forum forum = dc.getDocument().toObject(Forum.class);
                                forum.id = dc.getDocument().getId();
                                mapIdToForum.put(forum.id, forum);
                                break;
                            case REMOVED:
                                mapIdToForum.remove(dc.getDocument().getId());
                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + dc.getType());
                        }
                    }
                    try {
                        storeAction.onLoadForum(mapIdToForum.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toList()));
                    } catch (IllegalAccessException illegalAccessException) {
                        illegalAccessException.printStackTrace();
                    }
                });
    }

    public Task<Void> deleteForums(Forum forum) {
        return db.collection(Constants.FORUM_COLLECTION)
                .document(forum.id)
                .delete();
    }

}