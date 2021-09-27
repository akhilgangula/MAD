package com.example.inclass05grp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TopPaidAppsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopPaidAppsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TopPaidAppsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment TopPaidAppsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TopPaidAppsFragment newInstance(String param1) {
        TopPaidAppsFragment fragment = new TopPaidAppsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_paid_apps, container, false);
        ListView listView =((ListView)view.findViewById(R.id.topPaidAppsListView));
        List<DataServices.App> data = DataServices.getAppsByCategory(mParam1);
        ArrayAdapter<DataServices.App> adapter = new AppListAdapter(getContext(), R.layout.app_description, data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mListener.onSelectApp(data.get(i));
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof ITopPaidApps) {
            mListener = (ITopPaidApps)context;
        }
        else {
            throw new RuntimeException("ITopPaidApps must be implemented");
        }
    }

    ITopPaidApps mListener;
    interface ITopPaidApps {
        void onSelectApp(DataServices.App app);
    }
}