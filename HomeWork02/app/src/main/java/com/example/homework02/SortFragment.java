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
 * Use the {@link SortFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SortFragment extends Fragment {

    private static final String CONFIG = "config";
    private Config config;

    public SortFragment() {
    }

    public SortFragment(IFilterSort mListener) {
        this.mListener = mListener;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param config Parameter 1.
     * @param mListener Parameter 2.
     * @return A new instance of fragment SortFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SortFragment newInstance(Config config, IFilterSort mListener) {
        SortFragment fragment = new SortFragment(mListener);
        Bundle args = new Bundle();
        args.putSerializable(CONFIG, config);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            config = (Config) getArguments().getSerializable(CONFIG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sort, container, false);
        getActivity().setTitle(getString(R.string.sort_title));
        RecyclerView recyclerView = view.findViewById(R.id.sort_recycler_view);
        recyclerView.setHasFixedSize(true);
        List<DataServices.User> list_users = DataServices.getAllUsers();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter = new SortRecyclerAdapter(list_users, config, mListener);
        recyclerView.setAdapter(adapter);
        return view;
    }

    IFilterSort mListener;

}