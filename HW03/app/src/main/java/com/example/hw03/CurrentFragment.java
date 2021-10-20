package com.example.hw03;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.hw03.bean.CurrentWeatherResponse;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CurrentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CurrentFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Data.City city;
    private String mParam2;

    public CurrentFragment() {
        // Required empty public constructor
    }

    public CurrentFragment(ICurrent mListener) {
        this.mListener = mListener;
    }

    public static CurrentFragment newInstance(Data.City city, ICurrent mListener) {
        CurrentFragment fragment = new CurrentFragment(mListener);
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            city = (Data.City) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_current, container, false);
        getActivity().setTitle(getString(R.string.curr_weather_title));
        view.findViewById(R.id.curr_weather_layout).setVisibility(View.INVISIBLE);
        view.findViewById(R.id.curr_weather_progress).setVisibility(View.VISIBLE);
        Service.getCurrentWeather(city, new Handler(message -> {
            if(message.arg1 != 1) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(Util.ERROR_TITLE)
                        .setMessage(Util.CURR_WEATHER_FAILED)
                        .setPositiveButton(Util.OK, (dialogInterface, i) -> {
                            dialogInterface.dismiss();
                            mListener.goToMainPage();
                        });
                builder.create().show();
                return false;
            }
            view.findViewById(R.id.curr_weather_layout).setVisibility(View.VISIBLE);
            view.findViewById(R.id.curr_weather_progress).setVisibility(View.INVISIBLE);
            CurrentWeatherResponse currentWeatherResponse = (CurrentWeatherResponse) message
                    .getData()
                    .getSerializable(Util.CURR_WEATHER_RESPONSE_KEY);
            ((TextView) view.findViewById(R.id.curr_name)).setText(city.toString());
            ((TextView) view.findViewById(R.id.curr_temp)).setText(currentWeatherResponse.main.temp + " " + getString(R.string.Fahrenheit));
            ((TextView) view.findViewById(R.id.curr_temp_max)).setText(currentWeatherResponse.main.temp_max+ " " + getString(R.string.Fahrenheit));
            ((TextView) view.findViewById(R.id.curr_temp_min)).setText(currentWeatherResponse.main.temp_min+ " " + getString(R.string.Fahrenheit));
            ((TextView) view.findViewById(R.id.curr_desc)).setText(currentWeatherResponse.weather.get(0).description);
            ((TextView) view.findViewById(R.id.curr_humidity)).setText(currentWeatherResponse.main.humidity+ " " + getString(R.string.percent));
            ((TextView) view.findViewById(R.id.curr_wind_speed)).setText(currentWeatherResponse.wind.speed + " " + getString(R.string.wind_speed));
            ((TextView) view.findViewById(R.id.curr_wind_degree)).setText(currentWeatherResponse.wind.deg+ " " + getString(R.string.wind_degree));
            ((TextView) view.findViewById(R.id.curr_cloudiness)).setText(currentWeatherResponse.clouds.all+ " " + getString(R.string.percent));
            Picasso.get().load(Util.PIC_BASE_URL + Util.WEATHER_ICON + currentWeatherResponse.weather.get(0).icon + ".png").into(((ImageView)view.findViewById(R.id.curr_img)));
            view.findViewById(R.id.check_forecast).setOnClickListener(view1 -> {
                mListener.checkForeCast(city);
            });
            return false;
        }));
        return view;
    }
ICurrent mListener;
    interface ICurrent {
        void checkForeCast(Data.City city);
        void goToMainPage();
    }
}
