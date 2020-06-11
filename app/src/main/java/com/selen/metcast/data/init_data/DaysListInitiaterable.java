package com.selen.metcast.data.init_data;

public interface DaysListInitiaterable {
    void initMainSingleton(String city, int number);
    void initMainSingletonWithGPS(float lat, float lon, int number);
}
