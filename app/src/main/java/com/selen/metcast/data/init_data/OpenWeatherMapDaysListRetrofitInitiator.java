package com.selen.metcast.data.init_data;

import android.os.Handler;

import com.selen.metcast.data.Constants;
import com.selen.metcast.data.Day;
import com.selen.metcast.data.MainSingleton;
import com.selen.metcast.data.Precipitation;
import com.selen.metcast.data.json_openweathermap.WeatherRequest;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//покачто ключ не выношу за приложение, чтобы было проще проверять
public class OpenWeatherMapDaysListRetrofitInitiator implements DaysListInitiaterable {

    private DaysListInitiaterableBuilder.FragmentsInitiator fragmentsInitiator;
    private RetrofitRequest retrofitRequest;

    public OpenWeatherMapDaysListRetrofitInitiator(DaysListInitiaterableBuilder.FragmentsInitiator fragmentsInitiator) {
        this.fragmentsInitiator = fragmentsInitiator;
    }

    @Override
    public void initMainSingleton(String city, final int number) {
        initRetorfit();
        retrofitRequest.loadWeather(city, Constants.WEATHER_UNITS, Constants.WEATHER_API_KEY)
                .enqueue(new Callback<WeatherRequest>() {
                    @Override
                    public void onResponse(Call<WeatherRequest> call, Response<WeatherRequest> response) {
                        if (response.body() != null && response.isSuccessful()) {
//                            данные получены
                            addDay(response);
                            if (fragmentsInitiator != null)
                                fragmentsInitiator.initiateFragments(true);
                        } else {
//                            данные не получены
                            if (fragmentsInitiator != null)
                                fragmentsInitiator.initiateFragments(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherRequest> call, Throwable t) {
                        if (fragmentsInitiator != null)
                            fragmentsInitiator.initiateFragments(false);
                    }
                });
    }

    @Override
    public void initMainSingletonWithGPS(float lat, float lon, int number) {
        initRetorfit();
        retrofitRequest.loadWeatherGPS(lat, lon, Constants.WEATHER_UNITS, Constants.WEATHER_API_KEY)
                .enqueue(new Callback<WeatherRequest>() {
                    @Override
                    public void onResponse(Call<WeatherRequest> call, Response<WeatherRequest> response) {
                        if (response.body() != null && response.isSuccessful()) {
//                            данные получены
                            addDay(response);
                            if (fragmentsInitiator != null)
                                fragmentsInitiator.initiateFragments(true);
                        } else {
//                            данные не получены
                            if (fragmentsInitiator != null)
                                fragmentsInitiator.initiateFragments(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherRequest> call, Throwable t) {
                        if (fragmentsInitiator != null)
                            fragmentsInitiator.initiateFragments(false);
                    }
                });
    }

    private void initRetorfit() {
        if (retrofitRequest == null) {
            Retrofit retrofit;
            retrofit = new Retrofit.Builder()
                    // Базовая часть адреса
                    .baseUrl(Constants.WEATHER_URL)
                    // Конвертер, необходимый для преобразования JSON в объекты
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            // Создаём объект, при помощи которого будем выполнять запросы
            retrofitRequest = retrofit.create(RetrofitRequest.class);
        }
    }

    private void addDay(Response<WeatherRequest> response) {
        MainSingleton data = MainSingleton.getInstance();
        data.clearDaysList();
        final List<Day> dayList = data.getDaysList();
        final GregorianCalendar currentDate = new GregorianCalendar();
        final int finI = 0;
//                            генерируем дату
        GregorianCalendar date = new GregorianCalendar();
        gregorianCalendarGenerate(currentDate, date, finI);
//                            получаем температуру
        int temperature = (int) response.body().getMain().getTemp();
//                            получаем силу ветра
        float wind = response.body().getWind().getSpeed();
//                            получаем давление
        int pressure = response.body().getMain().getPressure();
//                            получаем влажность
        int humidity = response.body().getMain().getHumidity();
//                            получаем атмосферные осадки
        Precipitation precipitation = (Precipitation.values())[pictureRequest(response.body().getWeather()[0].getMain())];

        Day day = new Day(date, temperature, wind, pressure, humidity, precipitation);
        dayList.add(day);
    }

    private void gregorianCalendarGenerate(GregorianCalendar currentDate, GregorianCalendar date, int number) {
//        (так, как заполняем не более одного месяца этот велосипед работает)
        date.roll(Calendar.DAY_OF_MONTH, number);
        if (date.get(Calendar.DAY_OF_MONTH) < currentDate.get(Calendar.DAY_OF_MONTH))
            date.roll(Calendar.MONTH, 1);
        if (date.get(Calendar.MONTH) < currentDate.get(Calendar.MONTH))
            date.roll(Calendar.YEAR, 1);
    }

    private int pictureRequest(String weather){
        int result = 0;
        switch (weather){
            case "Clouds":
                result = 1;
                break;
            case "Rain":
                result = 2;
                break;
            case "Snow":
                result = 3;
                break;
            default:
                result = 0;
                break;
        }
        return result;
    }

}
