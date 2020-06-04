package com.selen.metcast.data;

public interface Constants {
    String CLEAR = "clear";
    String CLOUDY = "cloudy";
    String RAIN = "rain";
    String SNOW = "rain";

    String CURRENT_DAY_POSITION_IN_LIST = "current day in list";
    String CURRENT_DAY_POSITION_MAIN_ACTIVITY = "current day main activity";
    String PUT_CURRENT_CITY_MAIN_ACTIVITY = "put current city main activity";
    String CURRENT_CITY_MAIN_ACTIVITY = "current city main activity";

    String NAME_SHARED_PREFERENCE = "my setting";
    String IS_DARK_THEME = "is dark theme";

    String NAME_SHARED_PREFERENCE_CITY = "my setting city";
    String GET_CITY_NAME = "get city name";

    int DAYS_IN_LIST = 30;

    String WEATHER_URL = "https://api.openweathermap.org/";
    String WEATHER_UNITS = "metric";
    String WEATHER_API_KEY = "d1467727eb1b785602d006f500e8c523";

    String WEATHER_URL_START = "https://api.openweathermap.org/data/2.5/weather?q=";
    String WEATHER_URL_END = "&units=metric&lang=ru&appid=";

    int OPEN_SETTINGS_REQUEST_CODE = 942;
}
