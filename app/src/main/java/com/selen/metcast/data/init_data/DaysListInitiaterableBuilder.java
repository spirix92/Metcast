package com.selen.metcast.data.init_data;

public class DaysListInitiaterableBuilder {
    private String city;
    private int number;
    float lat;
    float lon;
    private FragmentsInitiator fragmentsInitiator;


    public DaysListInitiaterableBuilder(String city, int number) {
        this.city = city;
        this.number = number;
    }

    public DaysListInitiaterableBuilder(float lat, float lon, int number) {
        this.lat = lat;
        this.lon = lon;
        this.number = number;
    }

    //    Если город есть в апи - скачать с сервера. если город введен неверно - сгенерировать рандомно
    public void buildWithAPI() {
        DaysListInitiaterable daysList = new OpenWeatherMapDaysListRetrofitInitiator(fragmentsInitiator);
        int number = 1;
        daysList.initMainSingleton(city, number);
    }

    //    Если город есть в апи - скачать с сервера. если город введен неверно - сгенерировать рандомно
    public void buildWithAPIGPS() {
        DaysListInitiaterable daysList = new OpenWeatherMapDaysListRetrofitInitiator(fragmentsInitiator);
        int number = 1;
        daysList.initMainSingletonWithGPS(lat, lon, number);
    }

    //    Если город введен неверно - сгенерировать рандомно
    public void buildWithRandom() {
        DaysListInitiaterable daysList = new RandomDaysListInitiator();
        daysList.initMainSingleton(city, number);
    }

    // Интерфейс для обработки нажатий
    public interface FragmentsInitiator {
        void initiateFragments(boolean result);
    }

    public FragmentsInitiator getFragmentsInitiator() {
        return fragmentsInitiator;
    }

    public void setFragmentsInitiator(FragmentsInitiator fragmentsInitiator) {
        this.fragmentsInitiator = fragmentsInitiator;
    }
}
