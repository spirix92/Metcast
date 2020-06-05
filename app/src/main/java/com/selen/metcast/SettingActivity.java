package com.selen.metcast;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.selen.metcast.data.Constants;
import com.selen.metcast.data.database.App;
import com.selen.metcast.data.database.CitiesDao;
import com.selen.metcast.data.database.CitiesSource;
import com.selen.metcast.data.database.City;

import java.util.List;

public class SettingActivity extends BaseActivity {

    private long back_pressed;
    private Switch darkMode;
    private TextInputEditText currentCity;
    private OnItemCityClickListener itemCityClickListener;
    private CitiesSource citiesSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initCitiesSources();
        darkMode = findViewById(R.id.dark_mode);
        currentCity = findViewById(R.id.input_edit_text);
        MaterialButton save = findViewById(R.id.save_settings);
        save.setOnClickListener(saveClick);
        currentCity.setOnKeyListener(selectCityListenerMK);
        currentCity.setOnEditorActionListener(selectCityListenerPK);
        replaceFragmentCityRecyclerView();

        itemCityClickListener = new OnItemCityClickListener() {
            @Override
            public void onItemClick(String city) {
                intentCreate(city);
            }
        };

    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis())
            super.onBackPressed();
        else
            Snackbar.make(darkMode, getResources()
                    .getString(R.string.exit_without_saving), Snackbar.LENGTH_SHORT)
                    .show();
        back_pressed = System.currentTimeMillis();
    }

    private View.OnClickListener saveClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            intentCreate(currentCity.getText().toString());
        }
    };

    //    нажатие enter на механической клавиатуре
    private View.OnKeyListener selectCityListenerMK = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            boolean result = false;
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                intentCreate(currentCity.getText().toString());
                result = true;
            }
            return result;
        }
    };

    //    нажатие enter на программной клавиатуре
    private TextView.OnEditorActionListener selectCityListenerPK = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            boolean result = false;
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                intentCreate(currentCity.getText().toString());
                result = true;
            }
            return result;
        }
    };

    //    добавляем фрагмент со списком городов
    public void replaceFragmentCityRecyclerView() {
        CityListFragment recyclerViewCityFragment = new CityListFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.settings_fragment_city_recycler_view, recyclerViewCityFragment).commit();
    }

    private void intentCreate(String cityName) {
        addCity(cityName);
        setDarkTheme(darkMode.isChecked());
        recreate();
        Intent intent = new Intent();
        intent.putExtra(Constants.PUT_CURRENT_CITY_MAIN_ACTIVITY, cityName);
        setResult(RESULT_OK, intent);
        finish();
    }


    private void addCity(String cityName) {
        List<City> cities = citiesSource.getCities();
        boolean cityIsListed = false;
        for (City s : cities) {
            if (cityName.equals(s.cityName)) {
                cityIsListed = true;
                break;
            }
        }
        City city = new City();
        city.cityName = cityName;
        if (!cityIsListed) citiesSource.addCities(city);
    }

    // Интерфейс для обработки нажатий для выбора города
    public interface OnItemCityClickListener {
        void onItemClick(String cityName);
    }

    public OnItemCityClickListener getItemCityClickListener() {
        return itemCityClickListener;
    }

    private void initCitiesSources(){
        CitiesDao citiesDao = App
                .getInstance()
                .getCitiesDao();

        citiesSource = new CitiesSource(citiesDao);
    }

}
