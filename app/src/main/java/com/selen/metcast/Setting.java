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

public class Setting extends BaseActivity {

    private long back_pressed;
    private Switch darkMode;
    private TextInputEditText currentCity;
    private MaterialButton save;

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

    private void intentCreate() {
        setDarkTheme(darkMode.isChecked());
        recreate();
        Intent intent = new Intent();
        intent.putExtra(Constants.PUT_CURRENT_CITY_MAIN_ACTIVITY, currentCity.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    private View.OnClickListener saveClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            intentCreate();
        }
    };

    //    нажатие enter на механической клавиатуре
    private View.OnKeyListener selectCityListenerMK = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            boolean result = false;
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                intentCreate();
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
                intentCreate();
                result = true;
            }
            return result;
        }
    };

}
