package com.selen.metcast.data.init_data;

public class DaysListInitiaterableBuilder {
    private String city;
    private int number;

    public DaysListInitiaterableBuilder(String city, int number) {
        this.city = city;
        this.number = number;
    }

    //    Если город есть в апи - скачать с сервера. если город введен неверно - сгенерировать рандомно
    public void build() {
        DaysListInitiaterable daysList = new OpenWeatherMapDaysListInitiator();
        daysList.checkCity(city);
        if (!daysList.checkCity(city)) {
            daysList = new RandomDaysListInitiator();
            daysList.initMainSingleton(city, number);
        } else {
            daysList.initMainSingleton(city, 1);
        }
    }
}
