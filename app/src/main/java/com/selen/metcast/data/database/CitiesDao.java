package com.selen.metcast.data.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CitiesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCity(City city);

    @Update
    void updateCity(City city);

    @Delete
    void deleteCity(City city);

    @Query("DELETE FROM city WHERE `city name` = :cityName")
    void deleteCityByCityName(String cityName);

    @Query("SELECT * FROM city")
    List<City> getAllCities();

    @Query("SELECT * FROM city WHERE `city name` = :cityName")
    City getCityByCityName(String cityName);

    @Query("SELECT COUNT() FROM city")
    long getCountCities();

}
