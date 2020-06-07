package com.selen.metcast.data.database;

import java.util.List;

public class CitiesSource {

        private final CitiesDao citiesDao;

        private List<City> cities;

        public CitiesSource(CitiesDao citiesDao){
            this.citiesDao = citiesDao;
        }

        public List<City> getCities(){

            if (cities == null){
                LoadCities();
            }
            return cities;
        }

        public void LoadCities(){
            cities = citiesDao.getAllCities();
        }

        public long getCountCities(){
            return citiesDao.getCountCities();
        }

        public void addCities(City city){
            citiesDao.insertCity(city);
            LoadCities();
        }

        public void updateCities(City city){
            citiesDao.updateCity(city);
            LoadCities();
        }

        public void removeCity(City city){
            citiesDao.deleteCity(city);
            LoadCities();
        }

    }
