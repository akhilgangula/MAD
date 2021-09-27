package com.example.homework02;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SortFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SortFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private List<DataServices.User> list_users;
    private String mParam2;

    public SortFragment(ISort mListener) {
        this.mListener = mListener;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param users Parameter 1.
     * @param mListener Parameter 2.
     * @return A new instance of fragment SortFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SortFragment newInstance(List<DataServices.User> users, ISort mListener) {
        SortFragment fragment = new SortFragment(mListener);
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, (Serializable) users);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            list_users = (List<DataServices.User>) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sort, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.sort_recycler_view);
        recyclerView.setHasFixedSize(true);
        if(list_users == null) {
            list_users = DataServices.getAllUsers();
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter = new SortRecyclerAdapter(list_users, mListener);
        recyclerView.setAdapter(adapter);
        return view;
    }

    ISort mListener;
    interface ISort {
        void sort(List<DataServices.User> users);
    }
}