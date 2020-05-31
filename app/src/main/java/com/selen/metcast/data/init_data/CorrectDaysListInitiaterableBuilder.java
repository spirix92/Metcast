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
        int number = 1;
        daysList.initMainSingleton(city, number);
    }

    //    Если город введен неверно - сгенерировать рандомно
    public void buildWithRandom() {
        CorrectDaysListInitiaterable daysList = new CorrectRandomDaysListInitiator();
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
