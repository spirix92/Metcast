package com.selen.metcast.data;

import java.util.GregorianCalendar;

public class Day {
//    текущая дата
    private GregorianCalendar date;
//    текущая температура
    private int temperature;
//    текущая сила ветра
    private int wind;
//    текущее давление
    private int pressure;
//    текущая влажность
    private int humidity;
//    Атмосферные осадки
    private Precipitation precipitation;

    public Day(GregorianCalendar date, int temperature, int wind, int pressure, int humidity, Precipitation precipitation) {
        this.date = date;
        this.temperature = temperature;
        this.wind = wind;
        this.pressure = pressure;
        this.humidity = humidity;
        this.precipitation = precipitation;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getWind() {
        return wind;
    }

    public int getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public Precipitation getPrecipitation() {
        return precipitation;
    }
}
