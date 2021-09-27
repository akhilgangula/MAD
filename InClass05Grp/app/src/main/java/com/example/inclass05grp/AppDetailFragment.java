package com.example.inclass05grp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private DataServices.App mParam1;
    private String mParam2;

    public AppDetailFragment() {
        // Required empty public constructor
    }

    public static AppDetailFragment newInstance(DataServices.App app) {
        AppDetailFragment fragment = new AppDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, app);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (DataServices.App) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_app_detail, container, false);
        ((TextView)view.findViewById(R.id.app_details_app_name)).setText(mParam1.name);
        ((TextView)view.findViewById(R.id.app_details_artist_name)).setText(mParam1.artistName);
        ((TextView)view.findViewById(R.id.app_detail_release_date)).setText(mParam1.releaseDate);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, mParam1.genres);
        ((ListView)view.findViewById(R.id.genres)).setAdapter(adapter);
        return view;
    }

}