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
import com.selen.metcast.data.MainSingleton;

import java.util.List;

public class Setting extends BaseActivity {

    private long back_pressed;
    private Switch darkMode;
    private TextInputEditText currentCity;
    private MaterialButton save;
    private OnItemCityClickListener itemCityClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        darkMode = findViewById(R.id.dark_mode);
        currentCity = findViewById(R.id.input_edit_text);
        save = findViewById(R.id.save_settings);
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

    // Интерфейс для обработки нажатий
    public interface OnItemCityClickListener {
        void onItemClick(String city);
    }

    public OnItemCityClickListener getItemCityClickListener() {
        return itemCityClickListener;
    }

    private void intentCreate(String city) {
        addCity(city);
        setDarkTheme(darkMode.isChecked());
        recreate();
        Intent intent = new Intent();
        intent.putExtra(Constants.PUT_CURRENT_CITY_MAIN_ACTIVITY, city);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void addCity(String city) {
        List<String> cities = MainSingleton.getInstance().getCitiesList();
        boolean cityIsListed = false;
        for (String s : cities) {
            if (city.equals(s)) {
                cityIsListed = true;
                break;
            }
        }
        if (!cityIsListed) cities.add(city);
    }

}
