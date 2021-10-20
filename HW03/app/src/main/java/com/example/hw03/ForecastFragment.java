package com.example.hw03;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hw03.bean.ForeCastEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForecastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForecastFragment extends Fragment {

    private static final String CITY_KEY = "city";
    List<ForeCastEntry> forecast = new ArrayList<>();

    private Data.City city;

    public ForecastFragment() {
        // Required empty public constructor
    }

    public ForecastFragment(IForeCast mListener) {
        this.mListener = mListener;
    }

    public static ForecastFragment newInstance(Data.City city, IForeCast mListener) {
        ForecastFragment fragment = new ForecastFragment(mListener);
        Bundle args = new Bundle();
        args.putSerializable(CITY_KEY, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            city = (Data.City) getArguments().getSerializable(CITY_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);
        getActivity().setTitle(getString(R.string.weather_forecast_title));
        RecyclerView recyclerView = view.findViewById(R.id.forecast_list);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter = new CityRecyclerViewAdapter(forecast);
             view.findViewById(R.id.forecast_layout).setVisibility(View.INVISIBLE);
         Service.getForecastWeather(city, new Handler(message -> {
             view.findViewById(R.id.forecast_loader).setVisibility(View.VISIBLE);
             if(message.arg1 != 1) {
                 AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                 builder.setTitle(Util.ERROR_TITLE)
                         .setMessage(Util.FORECAST_WEATHER_FAILED)
                         .setPositiveButton(Util.OK, (dialogInterface, i) -> {
                             dialogInterface.dismiss();
                             mListener.goToBackToCurrentWeather();
                         });
                 builder.create().show();
                 return false;
             }
             view.findViewById(R.id.forecast_layout).setVisibility(View.VISIBLE);
             view.findViewById(R.id.forecast_loader).setVisibility(View.INVISIBLE);
             List<ForeCastEntry> list_forecast = (List<ForeCastEntry>) message.getData().getSerializable(Util.FORECAST_WEATHER_RESPONSE_KEY);
             forecast.addAll(list_forecast);
             adapter.notifyDataSetChanged();
             return false;
         }));
        recyclerView.setAdapter(adapter);
        ((TextView)view.findViewById(R.id.forecast_city)).setText(city.toString());
        return view;
    }

    IForeCast mListener;
    interface IForeCast {
        public void goToBackToCurrentWeather();
    }
}