package com.example.hw03;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CitiesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CitiesFragment extends Fragment {

    public CitiesFragment() {
        // Required empty public constructor
    }

    public static CitiesFragment newInstance(ICity mListener) {
        CitiesFragment fragment = new CitiesFragment(mListener);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cities, container, false);
        getActivity().setTitle(getString(R.string.city_title));
        ListView listView = view.findViewById(R.id.city_list_view);
        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, Data.cities);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((adapterView, view1, i, l) -> {
            mListener.onCitySelected(Data.cities.get(i));
        });
        return view;
    }

    public CitiesFragment(ICity mListener) {
        this.mListener = mListener;
    }

    ICity mListener;
    interface ICity {
        void onCitySelected(Data.City city);
    }
}