package com.selen.metcast;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.selen.metcast.data.Constants;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isDarkTheme()) {
            setTheme(R.style.AppDarkTheme);
        } else {
            setTheme(R.style.AppTheme);
        }
    }

    // Чтение настроек, параметр тема
    protected boolean isDarkTheme() {
        // Работаем через специальный класс сохранения и чтения настроек
        SharedPreferences sharedPref = getSharedPreferences(Constants.NAME_SHARED_PREFERENCE, MODE_PRIVATE);
        //Прочитать тему, если настройка не найдена - взять по умолчанию true
        return sharedPref.getBoolean(Constants.IS_DARK_THEME, false);
    }

    // Сохранение настроек
    protected void setDarkTheme(boolean isDarkTheme) {
        SharedPreferences sharedPref = getSharedPreferences(Constants.NAME_SHARED_PREFERENCE, MODE_PRIVATE);
        // Настройки сохраняются посредством специального класса editor.
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(Constants.IS_DARK_THEME, isDarkTheme);
        editor.apply();
    }
}
