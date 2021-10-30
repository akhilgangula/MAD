package com.example.class08;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForumsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForumsFragment extends Fragment implements ForumCardAdapter.IDeletePost, IStoreAction {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forums, container, false);
        getActivity().setTitle(getString(R.string.title_forum));
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
    public void postDeleted(Forum forum) {
        FireStoreConnector.getInstance()
                .deleteForums(forum)
                .addOnSuccessListener(aVoid -> Toast.makeText(getContext(), getString(R.string.delete_success), Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), getString(R.string.delete_failed), Toast.LENGTH_SHORT).show();
                    Log.w(this.getClass().toString(), "Error deleting document", e);
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

    interface IForum {
        void onLogout();
        void newForum(String uuid);
    }
}