package com.selen.metcast.data.init_data;

import com.selen.metcast.data.json_openweathermap.WeatherRequest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitRequest {
    @GET("data/2.5/weather")
    Call<WeatherRequest> loadWeather(@Query("q") String cityCountry,
                                     @Query("units") String units,
                                     @Query("appid") String keyApi);
}
