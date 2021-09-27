package com.example.homework02;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String USERS = "users";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private List<DataServices.User> list_users;
    private String mParam2;

    public UserFragment() {
    }

    public UserFragment(IHome mListener) {
        // Required empty public constructor
        this.mListener = mListener;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param users Parameter 1.
     * @param mListener Parameter 2.
     * @return A new instance of fragment UserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserFragment newInstance(List<DataServices.User> users, IHome mListener) {
        UserFragment fragment = new UserFragment(mListener);
        Bundle args = new Bundle();
        args.putSerializable(USERS, (Serializable) users);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            list_users = (List<DataServices.User>) getArguments().getSerializable(USERS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        if(list_users == null) {
            list_users = DataServices.getAllUsers();
        }
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new UserRecyclerAdapter(list_users);
        recyclerView.setAdapter(adapter);

        view.findViewById(R.id.filter_btn).setOnClickListener(view1 -> mListener.filterUsers(list_users));

        view.findViewById((R.id.sort_btn)).setOnClickListener(view1 -> mListener.sortUsers(list_users));
        return view;
    }

    IHome mListener;
    public interface IHome {
        void filterUsers(List<DataServices.User> users);
        void sortUsers(List<DataServices.User> users);
    }
}