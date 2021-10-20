package edu.uncc.midtermapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PostsListFragment extends Fragment {
    private static final String ARG_USER_TOKEN = "ARG_USER_TOKEN";
    UserToken mUserToken;

    public PostsListFragment() {
        // Required empty public constructor
    }

    public static PostsListFragment newInstance(UserToken userToken) {
        PostsListFragment fragment = new PostsListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER_TOKEN, userToken);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserToken = (UserToken)getArguments().getSerializable(ARG_USER_TOKEN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_posts_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Posts");
    }
}