package com.selen.metcast.data.init_data;

import com.google.gson.Gson;
import com.selen.metcast.data.Constants;
import com.selen.metcast.data.Day;
import com.selen.metcast.data.MainSingleton;
import com.selen.metcast.data.Precipitation;
import com.selen.metcast.data.json_openweathermap.Error;
import com.selen.metcast.data.json_openweathermap.WeatherRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import javax.net.ssl.HttpsURLConnection;

//покачто ключ не выношу за приложение, чтобы было проще проверять
public class OpenWeatherMapDaysListInitiator implements DaysListInitiaterable {

    private static final String WEATHER_API_KEY = "d1467727eb1b785602d006f500e8c523";

    private String city;

    @Override
    public void initMainSingleton(String city, int number) {
        this.city = city;
        MainSingleton data = MainSingleton.getInstance();
        data.clearDaysList();
        List<Day> dayList = data.getDaysList();
        final GregorianCalendar currentDate = new GregorianCalendar();

        for (int i = 0; i < number; i++) {
            try {
                Day d = dayGenerator(currentDate, i);
                if (d == null)
                    throw new NullPointerException("Не удалось получить данные с сервера");
                dayList.add(dayGenerator(currentDate, i));
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean checkCity(String city) {
        boolean res = true;
        try {
            final URL uri = new URL(Constants.WEATHER_URL_START + city + Constants.WEATHER_URL_END + WEATHER_API_KEY);

            Callable callable = new Callable() {
                @Override
                public Object call() throws Exception {

                    HttpsURLConnection urlConnection = null;
                    boolean bool = true;
                    try {
                        urlConnection = (HttpsURLConnection) uri.openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.setReadTimeout(10000);
                        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        String result = getLines(in);
                        Gson gson = new Gson();
                        Error error = gson.fromJson(result, Error.class);
                        if (error.getCod() == 404) {
                            bool = false;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        bool = false;
                    } finally {
                        if (null != urlConnection) {
                            urlConnection.disconnect();
                        }
                    }
                    return bool;
                }
            };
            FutureTask<Boolean> future = new FutureTask<>(callable);
            new Thread(future).start();
            res = future.get();
        } catch (MalformedURLException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
            res = false;
        }
        return res;
    }

    private Day dayGenerator(final GregorianCalendar currentDate, int i) {
        Day currentDay = null;
        final int finI = i;
        try {
            final URL uri = new URL(Constants.WEATHER_URL_START + city + Constants.WEATHER_URL_END + WEATHER_API_KEY);
            Callable callable = new Callable() {
                @Override
                public Object call() throws Exception {
                    HttpsURLConnection urlConnection = null;
                    Day day = null;
                    try {
                        urlConnection = (HttpsURLConnection) uri.openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.setReadTimeout(10000);
                        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        String result = getLines(in);
                        Gson gson = new Gson();
                        WeatherRequest weatherRequest = gson.fromJson(result, WeatherRequest.class);
                        //        генерируем дату
                        GregorianCalendar date = new GregorianCalendar();
                        gregorianCalendarGenerate(currentDate, date, finI);
//        генерируем температуру
                        int temperature = (int) weatherRequest.getMain().getTemp();
//        генерируем силу ветра
                        int wind = weatherRequest.getWind().getSpeed();
//        генерируем давление
                        int pressure = weatherRequest.getMain().getPressure();
//        генерируем влажность
                        int humidity = weatherRequest.getMain().getHumidity();
//        генерируем атмосферные осадки
                        Precipitation precipitation = (Precipitation.values())[(int) (Math.random() * 4)];
                        day = new Day(date, temperature, wind, pressure, humidity, precipitation);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (null != urlConnection) {
                            urlConnection.disconnect();
                        }
                    }
                    return day;
                }
            };
            FutureTask<Day> future = new FutureTask<>(callable);
            new Thread(future).start();
            currentDay = future.get();
        } catch (MalformedURLException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return currentDay;
    }

    private String getLines(BufferedReader in) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    private void gregorianCalendarGenerate(GregorianCalendar currentDate, GregorianCalendar date, int number) {
//        (так, как заполняем не более одного месяца этот велосипед работает)
        date.roll(Calendar.DAY_OF_MONTH, number);
        if (date.get(Calendar.DAY_OF_MONTH) < currentDate.get(Calendar.DAY_OF_MONTH))
            date.roll(Calendar.MONTH, 1);
        if (date.get(Calendar.MONTH) < currentDate.get(Calendar.MONTH))
            date.roll(Calendar.YEAR, 1);
    }

}
