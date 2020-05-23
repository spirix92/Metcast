package com.selen.metcast.data.init_data;

import android.app.Activity;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;

public class DaysListInitiaterableBuilder {
    private String city;
    private int number;

    public DaysListInitiaterableBuilder(String city, int number) {
        this.city = city;
        this.number = number;
    }

    //    Если город есть в апи - скачать с сервера. если город введен неверно - сгенерировать рандомно
    public boolean build() {
        DaysListInitiaterable daysList = new OpenWeatherMapDaysListInitiator();
        boolean result = isCityCorrect(daysList);
        if (!result) {
            daysList = new RandomDaysListInitiator();
        } else {
            number = 1;
        }
        daysList.initMainSingleton(city, number);
        return result;
    }

    public boolean isCityCorrect(DaysListInitiaterable daysList){
        return daysList.checkCity(city);
    }
}
