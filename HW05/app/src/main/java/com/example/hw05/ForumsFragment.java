package com.example.hw05;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForumsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForumsFragment extends Fragment implements ForumCardAdapter.IForumCard, IStoreAction {

    private IForum mListener;
    private String uuid;
    private ForumCardAdapter adapter;
    private Map<String, String> userIdToName = new HashMap<>();
    List<Forum> list_forum = new ArrayList<>();
    public ForumsFragment() {
        // Required empty public constructor
    }

    public ForumsFragment(IForum mListener, String uuid) {
        this.mListener = mListener;
        this.uuid = uuid;
    }

    public static ForumsFragment newInstance(IForum mListener, String uuid) {
        ForumsFragment fragment = new ForumsFragment(mListener, uuid);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forums, container, false);
        getActivity().setTitle(getString(R.string.title_forums));
        view.findViewById(R.id.forum_create).setOnClickListener(view1 -> mListener.newForum(uuid));
        view.findViewById(R.id.forum_logout).setOnClickListener(view1 -> mListener.onLogout());
        FireStoreConnector.getInstance().getUsers(this);
        FireStoreConnector.getInstance().getForums(this);
        RecyclerView recyclerView = view.findViewById(R.id.forums);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ForumCardAdapter(list_forum, userIdToName, uuid, this);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void forumDeleted(Forum forum) {
         FireStoreConnector.getInstance()
                .deleteForums(forum)
                .addOnSuccessListener(aVoid -> Toast.makeText(getContext(), getString(R.string.delete_success), Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), getString(R.string.delete_failed), Toast.LENGTH_SHORT).show();
                    Log.w(this.getClass().toString(), "Error deleting document", e);
                });
    }

    @Override
    public void forumClicked(Forum forum) {
        mListener.navigateToForum(forum, userIdToName);
    }

    @Override
    public void forumLiked(String documentId, String userId, boolean like) {
        FireStoreConnector.getInstance().likeForum(documentId, userId, like)
                .addOnSuccessListener(o -> Toast.makeText(getContext(), "You liked this forum!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    Log.e(getClass().getSimpleName(), "forumLiked: failed", e);
                });
    }

    @Override
    public void onLoadUsers(Map<String, String> userIdToName) {
        this.userIdToName.clear();
        this.userIdToName.putAll(userIdToName);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadForum(List<Forum> forums) {
        this.list_forum.clear();
        this.list_forum.addAll(forums);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadComment(List<Comment> comments) {
        throw new RuntimeException("Comments not needed in this fragment");
    }

    interface IForum {
        void onLogout();
        void newForum(String uuid);
        void navigateToForum(Forum forum, Map<String, String> userMap);
    }
}