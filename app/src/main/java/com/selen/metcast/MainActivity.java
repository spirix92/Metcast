package com.selen.metcast;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;
import com.selen.metcast.data.Constants;
import com.selen.metcast.data.Day;
import com.selen.metcast.data.MainSingleton;
import com.selen.metcast.data.Precipitation;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends BaseActivity {

    private int startPosition;
    private String savedCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            SharedPreferences sharedPref = getSharedPreferences(Constants.NAME_SHARED_PREFERENCE_CITY, MODE_PRIVATE);
            savedCity = sharedPref.getString(Constants.GET_CITY_NAME, getResources().getString(R.string.start_city));
            initMainSingleton();
            addFragmentCurrentDay(startPosition, savedCity);
            addFragmentRecyclerView(savedCity);
        } else {
            savedCity = savedInstanceState.getString(Constants.CURRENT_CITY_MAIN_ACTIVITY);
        }

    }

    //    добавляем фрагмент текущего дня
    public void addFragmentCurrentDay(int startPosition, String savedCity) {
        CurrentDayFragment currentDayFragment = CurrentDayFragment.newInstance(startPosition, savedCity);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_fragment_current_day, currentDayFragment).commit();
    }

    //    добавляем фрагмент со списком дней
    public void addFragmentRecyclerView(String savedCity) {
        FragmentDaysRecyclerView recyclerViewDayFragment = FragmentDaysRecyclerView.newInstance(savedCity);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_fragment_days_recycler_view, recyclerViewDayFragment).commit();
    }

    //    заполняем синглтон со списком дней данными
    private void initMainSingleton() {
        MainSingleton data = MainSingleton.getInstance();
        data.clearDaysList();
        List<Day> dayList = data.getDaysList();
        GregorianCalendar currentDate = new GregorianCalendar();

        for (int i = 0; i < 30; i++) {
            dayList.add(dayGenerator(currentDate, i));
        }
    }

    private Day dayGenerator(GregorianCalendar currentDate, int i) {
        //    генерируем дату
        //    (так, как заполняем не более одного месяца этот велосипед работает)
        GregorianCalendar date = new GregorianCalendar();
        date.roll(Calendar.DAY_OF_MONTH, i);
        if (date.get(Calendar.DAY_OF_MONTH) < currentDate.get(Calendar.DAY_OF_MONTH))
            date.roll(Calendar.MONTH, 1);
        if (date.get(Calendar.MONTH) < currentDate.get(Calendar.MONTH))
            date.roll(Calendar.YEAR, 1);
//    генерируем температуру
        int temperature;
        temperature = (int) (Math.random() * 20) - 10;
//    генерируем силу ветра
        int wind;
        wind = (int) (Math.random() * 20);
//    генерируем давление
        int pressure;
        pressure = (int) (Math.random() * 300) + 500;
//    генерируем влажность
        int humidity;
        humidity = (int) (Math.random() * 100);
//    генерируем атмосферные осадки
        Precipitation precipitation;
        precipitation = (Precipitation.values())[(int) (Math.random() * 4)];

        return new Day(date, temperature, wind, pressure, humidity, precipitation);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        CoordinatorLayout coordinatorLayout = findViewById(R.id.coordinator_layout);

        if (id == R.id.action_settings) {
            openSettings();
        }
        if (id == R.id.action_about_the_program) {
            Snackbar.make(coordinatorLayout, "О программе", Snackbar.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void openSettings() {
        Intent intent = new Intent(getApplicationContext(), Setting.class);
        startActivityForResult(intent, Constants.OPEN_SETTINGS_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.OPEN_SETTINGS_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                savedCity = data.getStringExtra(Constants.PUT_CURRENT_CITY_MAIN_ACTIVITY);
                SharedPreferences sharedPref = getSharedPreferences(Constants.NAME_SHARED_PREFERENCE_CITY, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(Constants.GET_CITY_NAME, savedCity);
                editor.apply();
                initMainSingleton();
                addFragmentCurrentDay(startPosition, savedCity);
                addFragmentRecyclerView(savedCity);
                recreate();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.CURRENT_CITY_MAIN_ACTIVITY, savedCity);
    }

}
