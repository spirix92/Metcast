package com.selen.metcast.data.init_data;

import com.selen.metcast.data.Day;
import com.selen.metcast.data.MainSingleton;
import com.selen.metcast.data.Precipitation;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class CorrectRandomDaysListInitiator implements CorrectDaysListInitiaterable {

    //    заполняем синглтон со списком дней данными
    @Override
    public void initMainSingleton(String city, int number) {
        MainSingleton data = MainSingleton.getInstance();
        data.clearDaysList();
        List<Day> dayList = data.getDaysList();
        GregorianCalendar currentDate = new GregorianCalendar();
        for (int i = 0; i < number; i++) {
            dayList.add(dayGenerator(currentDate, i));
        }
    }

    private Day dayGenerator(GregorianCalendar currentDate, int i) {
//        генерируем дату
        GregorianCalendar date = new GregorianCalendar();
        gregorianCalendarGenerate(currentDate, date, i);
//        генерируем температуру
        int temperature = (int) (Math.random() * 20) - 10;
//        генерируем силу ветра
        int wind = (int) (Math.random() * 20);
//        генерируем давление
        int pressure = (int) (Math.random() * 300) + 500;
//        генерируем влажность
        int humidity = (int) (Math.random() * 100);
//        генерируем атмосферные осадки
        Precipitation precipitation = (Precipitation.values())[(int) (Math.random() * 4)];
        return new Day(date, temperature, wind, pressure, humidity, precipitation);
    }

    private void gregorianCalendarGenerate(GregorianCalendar currentDate, GregorianCalendar date, int number){
//        (так, как заполняем не более одного месяца этот велосипед работает)
        date.roll(Calendar.DAY_OF_MONTH, number);
        if (date.get(Calendar.DAY_OF_MONTH) < currentDate.get(Calendar.DAY_OF_MONTH))
            date.roll(Calendar.MONTH, 1);
        if (date.get(Calendar.MONTH) < currentDate.get(Calendar.MONTH))
            date.roll(Calendar.YEAR, 1);
    }

}
