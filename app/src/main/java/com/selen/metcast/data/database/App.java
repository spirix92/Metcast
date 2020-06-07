package com.selen.metcast.data.database;

import android.app.Application;

import androidx.room.Room;

public class App extends Application {

    private static App instance;

    private CitiesDatabase db;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        db = Room.databaseBuilder(
                getApplicationContext(),
                CitiesDatabase.class,
                "education_database")
                .allowMainThreadQueries()
                .build();
    }

    public CitiesDao getCitiesDao() {
        return db.getCitiesDao();
    }

}
