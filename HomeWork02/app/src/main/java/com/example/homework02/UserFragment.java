package com.example.homework02;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    private static final String CONFIG = "config";
    private static final String USERS = "users";

    public void setConfig(Config config) {
        this.config = config;
    }

    public void setUsers(List<DataServices.User> users) {
        this.users = users;
    }

    private Config config;
    private List<DataServices.User> users;
    public UserFragment() {
    }

    public UserFragment(Config config, IHome mListener) {
        this.mListener = mListener;
        this.config = config;
        this.users = DataServices.getAllUsers();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param config Parameter 2.
     * @param mListener Parameter 1.
     * @return A new instance of fragment UserFragment.
     */
    public static UserFragment newInstance(List<DataServices.User> users, Config config, IHome mListener) {
        UserFragment fragment = new UserFragment(config, mListener);
        Bundle args = new Bundle();
        args.putSerializable(CONFIG, config);
        args.putSerializable(USERS, (Serializable) users);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            config = (Config) getArguments().getSerializable(CONFIG);
            users = (List<DataServices.User>) getArguments().getSerializable(USERS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new UserRecyclerAdapter(users);
        recyclerView.setAdapter(adapter);
        view.findViewById(R.id.filter_btn).setOnClickListener(view1 -> mListener.filterUsers(config));
        view.findViewById((R.id.sort_btn)).setOnClickListener(view1 -> mListener.sortUsers(config));
        return view;
    }

    IHome mListener;
    public interface IHome {
        void filterUsers(Config config);
        void sortUsers(Config config);
    }
}