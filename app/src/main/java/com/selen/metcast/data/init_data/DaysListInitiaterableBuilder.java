package com.selen.metcast.data.init_data;

import com.selen.metcast.MainActivity;

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

    private boolean isCityCorrect(DaysListInitiaterable daysList){
        return daysList.checkCity(city);
    }
}
