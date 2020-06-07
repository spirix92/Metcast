package com.selen.metcast.data.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.selen.metcast.data.Constants;

@Entity(indices = {@Index(value = {Constants.CITY_NAME})})
public class City {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = Constants.CITY_NAME)
    public String cityName;

}
