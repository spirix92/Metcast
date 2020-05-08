package com.selen.metcast.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//список всех полученных дней
public class MainSingleton implements Serializable {
    private static MainSingleton instance = null;
    private static final Object syncObj = new Object();

    private List<Day> days;

    private MainSingleton() {
        days = new ArrayList<>();
    }

    public static MainSingleton getInstance() {
        if (instance == null) {
            synchronized (syncObj) {
                if (instance == null) {
                    instance = new MainSingleton();
                }
            }
        }
        return instance;
    }

    public List<Day> getDaysList() {
        synchronized (syncObj) {
            return days;
        }
    }

    public void setDaysList(List<Day> days) {
        synchronized (syncObj) {
            this.days = days;
        }
    }

    public void clearDaysList() {
        synchronized (syncObj) {
            days.clear();
        }
    }

}