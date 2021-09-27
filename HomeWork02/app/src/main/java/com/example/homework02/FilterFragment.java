package com.example.homework02;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String USERS = "users";

    // TODO: Rename and change types of parameters
    private List<DataServices.User> list_users;

    public FilterFragment() {
        // Required empty public constructor
    }

    public FilterFragment(IFilter mListener) {
        this.mListener = mListener;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param users Parameter 1.
     * @return A new instance of fragment FilterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FilterFragment newInstance(List<DataServices.User> users, IFilter mListener) {
        FilterFragment fragment = new FilterFragment(mListener);
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_filter, container, false);
        List<String> all_states = DataServices.getAllStates();
        ArrayAdapter arrayAdapter = new ArrayAdapter(view.getContext(),android.R.layout.simple_list_item_1, all_states);
        ListView listView = view.findViewById(R.id.list_filter_state);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener((adapterView, view1, i, l) -> {
            String filteredState = all_states.get(i);
            mListener.applyFilter(list_users.stream().filter(user -> user.state.equals(filteredState)).collect(Collectors.toList()));
        });
        return view;
    }

    IFilter mListener;
    public interface IFilter {
        void applyFilter(List<DataServices.User> users);
    }
}