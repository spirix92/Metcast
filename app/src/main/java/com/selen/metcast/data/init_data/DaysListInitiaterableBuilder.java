package com.selen.metcast.data.init_data;

import com.selen.metcast.MainActivity;

public class DaysListInitiaterableBuilder {
    private String city;
    private int number;
    private MainActivity activity;

    public DaysListInitiaterableBuilder(String city, int number) {
        this.city = city;
        this.number = number;
        this.activity = activity;
    }

    //    Если город есть в апи - скачать с сервера. если город введен неверно - сгенерировать рандомно
    public void build() {
        DaysListInitiaterable daysList = new OpenWeatherMapDaysListInitiator();
        daysList.checkCity(city);
        if (!daysList.checkCity(city)) {
            daysList = new RandomDaysListInitiator();
        } else {
            number = 1;
        }
        daysList.initMainSingleton(city, number);
    }
}
