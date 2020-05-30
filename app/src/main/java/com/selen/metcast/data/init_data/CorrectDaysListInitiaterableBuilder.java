package com.selen.metcast.data.init_data;

public class CorrectDaysListInitiaterableBuilder {
    private String city;
    private int number;
    private FragmentsInitiator fragmentsInitiator;

    public CorrectDaysListInitiaterableBuilder(String city, int number) {
        this.city = city;
        this.number = number;
    }

    //    Если город есть в апи - скачать с сервера. если город введен неверно - сгенерировать рандомно
    public void buildWithAPI() {
        CorrectDaysListInitiaterable daysList = new CorrectOpenWeatherMapDaysListInitiator(fragmentsInitiator);
        number = 1;
        daysList.initMainSingleton(city, number);
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
}
