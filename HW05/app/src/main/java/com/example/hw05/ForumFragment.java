package com.example.hw05;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ForumFragment extends Fragment implements CommentCardAdapter.ICommentCard, IStoreAction {

    private static final String FORUM = "param1";
    private static final String USER_MAP = "param2";
    private List<Comment> comments;

    private Forum forum;

    public void setForum(Forum forum) {
        this.forum = forum;
    }

    private Map<String, String> userIdToNameMap;
    private CommentCardAdapter adapter;
    public ForumFragment() {
        // Required empty public constructor
    }

    public static ForumFragment newInstance(Forum forum, Map<String, String> userIdToNameMap) {
        ForumFragment fragment = new ForumFragment();
        Bundle args = new Bundle();
        args.putSerializable(FORUM, forum);
        args.putSerializable(USER_MAP, (Serializable) userIdToNameMap);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            forum = (Forum) getArguments().getSerializable(FORUM);
            userIdToNameMap = (Map<String, String>) getArguments().getSerializable(USER_MAP);
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        comments = new ArrayList<>();
        getActivity().setTitle(getString(R.string.title_forum));
        View view = inflater.inflate(R.layout.fragment_forum, container, false);
        ((TextView)view.findViewById(R.id.forum_forum_title)).setText(forum.title);
        ((TextView)view.findViewById(R.id.forum_forum_author)).setText(userIdToNameMap.get(forum.uuid));
        ((TextView)view.findViewById(R.id.forum_forum_desc)).setText(forum.desc);

        view.findViewById(R.id.forum_forum_comment_btn).setOnClickListener(view1 -> {
            EditText commentBox = (EditText)view.findViewById(R.id.forum_forum_comment_textfield);
            String comment = commentBox.getText().toString();
            if(comment.isEmpty()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(getString(R.string.error))
                        .setMessage(getString(R.string.error_comment))
                        .setPositiveButton(getString(R.string.ok),(dialogInterface,i)->{
                            dialogInterface.dismiss();
                        });
                builder.create().show();
                return;
            }
            FireStoreConnector.getInstance().addComment(forum.id, new Comment(FirebaseAuth.getInstance().getUid(), comment))
                    .addOnSuccessListener(o -> commentBox.setText(""))
                    .addOnFailureListener(e -> Log.w(this.getClass().getSimpleName(), "Add comment failed", e));
        });
        FireStoreConnector.getInstance().getComment(forum.id, this);
        RecyclerView recyclerView = view.findViewById(R.id.forum_recyler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CommentCardAdapter(comments, userIdToNameMap, forum.id, this);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                ((TextView)view.findViewById(R.id.forum_forum_comment_count)).setText(Integer.toString(comments.size()));
            }
        });
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void commentDeleted(String documentId, String commentId) {
        FireStoreConnector.getInstance().deleteComment(documentId, commentId);
    }

    @Override
    public void onLoadUsers(Map<String, String> userIdToName) throws IllegalAccessException {
        throw new IllegalAccessException("users not needed in this fragment");
    }

    @Override
    public void onLoadForum(List<Forum> forums) throws IllegalAccessException {
        throw new IllegalAccessException("All forums will not be loaded here");
    }

    @Override
    public void onLoadComment(List<Comment> comments) {
        this.comments.clear();
        this.comments.addAll(comments);
        adapter.notifyDataSetChanged();
    }
}