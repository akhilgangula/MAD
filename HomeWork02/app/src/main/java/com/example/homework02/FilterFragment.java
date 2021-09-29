package com.example.homework02;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String CONFIG = "config";

    // TODO: Rename and change types of parameters
    private Config config;

    public FilterFragment() {
        // Required empty public constructor
    }

    public FilterFragment(IFilterSort mListener) {
        this.mListener = mListener;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param config Parameter 1.
     * @return A new instance of fragment FilterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FilterFragment newInstance(Config config, IFilterSort mListener) {
        FilterFragment fragment = new FilterFragment(mListener);
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_filter, container, false);
        List<String> all_states = new ArrayList<>(Collections.singletonList(Utils.ALL_STATES));
        all_states.addAll(DataServices.getAllStates());
        ArrayAdapter arrayAdapter = new ArrayAdapter(view.getContext(),android.R.layout.simple_list_item_1, all_states);
        ListView listView = view.findViewById(R.id.list_filter_state);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener((adapterView, view1, i, l) -> {
            String filteredState = all_states.get(i);
            config.setFilterBy(filteredState);
            List<DataServices.User> users =Utils
                    .manipulate(config.getFilterBy(), config.getSortBy(), config.isOrder());
            mListener.applyConfig(users, config);
        });
        return view;
    }

    IFilterSort mListener;
}