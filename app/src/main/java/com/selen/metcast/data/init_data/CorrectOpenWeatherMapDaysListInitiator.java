package com.selen.metcast.data.init_data;

import android.os.Handler;

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

import javax.net.ssl.HttpsURLConnection;

//покачто ключ не выношу за приложение, чтобы было проще проверять
public class CorrectOpenWeatherMapDaysListInitiator implements CorrectDaysListInitiaterable {

    private static final String WEATHER_API_KEY = "d1467727eb1b785602d006f500e8c523";

    private CorrectDaysListInitiaterableBuilder.FragmentsInitiator fragmentsInitiator;
    private String city;

    public CorrectOpenWeatherMapDaysListInitiator(CorrectDaysListInitiaterableBuilder.FragmentsInitiator fragmentsInitiator) {
        this.fragmentsInitiator = fragmentsInitiator;
    }

    @Override
    public void initMainSingleton(String city, int number) {
        this.city = city;
        MainSingleton data = MainSingleton.getInstance();
        data.clearDaysList();
        final List<Day> dayList = data.getDaysList();
        final GregorianCalendar currentDate = new GregorianCalendar();
        final int finI = 0;

        try {
            final URL uri = new URL(Constants.WEATHER_URL_START + city + Constants.WEATHER_URL_END + WEATHER_API_KEY);
            final Handler handler = new Handler();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    HttpsURLConnection urlConnection = null;
                    try {
                        urlConnection = (HttpsURLConnection) uri.openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.setReadTimeout(10000);
                        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        String result = getLines(in);
                        Gson gson = new Gson();
                        Error error = gson.fromJson(result, Error.class);
                        if (error.getCod() == 200) {
//                            все хорошо

                            WeatherRequest weatherRequest = gson.fromJson(result, WeatherRequest.class);
                            //        генерируем дату
                            GregorianCalendar date = new GregorianCalendar();
                            gregorianCalendarGenerate(currentDate, date, finI);
//        получаем температуру
                            int temperature = (int) weatherRequest.getMain().getTemp();
//        получаем силу ветра
                            int wind = weatherRequest.getWind().getSpeed();
//        получаем давление
                            int pressure = weatherRequest.getMain().getPressure();
//        получаем влажность
                            int humidity = weatherRequest.getMain().getHumidity();
//        получаем атмосферные осадки
                            Precipitation precipitation = (Precipitation.values())[(int) (Math.random() * 4)];
                            Day day = new Day(date, temperature, wind, pressure, humidity, precipitation);
                            dayList.add(day);

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (fragmentsInitiator != null)
                                        fragmentsInitiator.initiateFragments(true);
                                }
                            });
                        } else {
//                            все плохо
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (null != urlConnection) {
                            urlConnection.disconnect();
                        }
                    }
                }
            }).start();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

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
