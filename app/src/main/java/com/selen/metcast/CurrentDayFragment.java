package com.selen.metcast;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.selen.metcast.data.Constants;
import com.selen.metcast.data.Day;
import com.selen.metcast.data.MainSingleton;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;

public class CurrentDayFragment extends Fragment {

    private int dayPosition;
    private String currentCIty;
    private List<Day> days;

    public CurrentDayFragment() {
        days = MainSingleton.getInstance().getDaysList();
    }

    static CurrentDayFragment newInstance(int dayPosition, String currentCIty) {
        CurrentDayFragment fragment = new CurrentDayFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.CURRENT_DAY_POSITION_IN_LIST, dayPosition);
        args.putString(Constants.PUT_CURRENT_CITY_MAIN_ACTIVITY, currentCIty);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dayPosition = getArguments().getInt(Constants.CURRENT_DAY_POSITION_IN_LIST);
            currentCIty = getArguments().getString(Constants.PUT_CURRENT_CITY_MAIN_ACTIVITY);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(Constants.CURRENT_DAY_POSITION_IN_LIST, dayPosition);
        outState.putString(Constants.PUT_CURRENT_CITY_MAIN_ACTIVITY, currentCIty);
        super.onSaveInstanceState(outState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_current_day, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((TextView) view.findViewById(R.id.current_city_name)).setText(currentCIty);
        if (days.size() > 0) {
            Day day = days.get(dayPosition);

            GregorianCalendar calendar = day.getDate();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy, EEEE");
            ((TextView) view.findViewById(R.id.current_day)).setText(dateFormat.format(calendar.getTime()));

            int temp = day.getTemperature();
            ((TextView) view.findViewById(R.id.current_temperature)).setText(String.valueOf(temp));

            view.findViewById(R.id.current_background).setBackgroundColor((temp > 0)
                    ? view.getResources().getColor(R.color.sun)
                    : view.getResources().getColor(R.color.snow));

            ((ImageView) view.findViewById(R.id.current_temperature_picture)).setImageDrawable(day
                    .getPrecipitation().getPicture(view.getResources()));

            ((TextView) view.findViewById(R.id.current_wind)).setText(String.format("%.2f", day.getWind()));
            ((TextView) view.findViewById(R.id.current_pressure)).setText(String.format("%d", day.getPressure()));
            ((TextView) view.findViewById(R.id.current_humidity)).setText(String.format("%d", day.getHumidity()));
        }
    }

}
