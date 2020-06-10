package com.selen.metcast.data;

public interface Constants {

    String CURRENT_DAY_POSITION_IN_LIST = "current day in list";
    String CURRENT_DAY_POSITION_MAIN_ACTIVITY = "current day main activity";
    String PUT_CURRENT_CITY_MAIN_ACTIVITY = "put current city main activity";
    String PUT_CURRENT_CITY_GPS_REQUEST = "put current city gps request";

    //    Precipitation
    String CLEAR = "clear";
    String CLOUDY = "cloudy";
    String RAIN = "rain";
    String SNOW = "rain";

//    BaseActivity
    String NAME_SHARED_PREFERENCE = "my setting";
    String IS_DARK_THEME = "is dark theme";

//    MainActivity
    String NAME_SHARED_PREFERENCE_CITY = "my setting city";
    String GET_CITY_NAME = "get city name";
    int OPEN_SETTINGS_REQUEST_CODE = 942;
    int DAYS_IN_LIST = 30;
    int PERMISSION_REQUEST_CODE = 10;
    String CURRENT_CITY_MAIN_ACTIVITY = "current city main activity";

//    OpenWeatherMapDaysListManualInitiator
    String WEATHER_URL = "https://api.openweathermap.org/";
    String WEATHER_API_KEY = "d1467727eb1b785602d006f500e8c523";
    String WEATHER_URL_START = "https://api.openweathermap.org/data/2.5/weather?q=";
    String WEATHER_URL_END = "&units=metric&lang=ru&appid=";

//    OpenWeatherMapDaysListRetrofitInitiator
    String WEATHER_UNITS = "metric";

//    data.database
    String CITY_NAME = "city name";
    String CITIES_DATABASE = "cities database";

//    broadcast
    String NOTIFICATION_CHANNEL = "2";
    String ACTION_SEND_MSG = "com.selen.metcast.broadcastmessage";
    String STORM_NAME_MSG_TITLE = "storm broadcast message title";
    String STORM_NAME_MSG = "storm broadcast message";
    int FLAG_RECEIVER_INCLUDE_BACKGROUND = 0x01000000;
}
