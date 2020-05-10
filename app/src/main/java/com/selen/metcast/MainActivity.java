package com.selen.metcast;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;
import com.selen.metcast.data.Day;
import com.selen.metcast.data.MainSingleton;
import com.selen.metcast.data.Precipitation;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int startPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initMainSingleton();
        addFragmentCurrentDay(startPosition);
    }

    //    добавляем фрагмент текущего дня
    public void addFragmentCurrentDay(int startPosition) {
        CurrentDayFragment detail = CurrentDayFragment.newInstance(startPosition);
            androidx.fragment.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.main_fragment_current_day, detail);
            ft.commit();
        }

//    заполняем синглтон со списком дней данными
        private void initMainSingleton () {
            MainSingleton data = MainSingleton.getInstance();
            data.clearDaysList();
            List<Day> dayList = data.getDaysList();
            GregorianCalendar currentDate = new GregorianCalendar();

            for (int i = 0; i < 30; i++) {
                dayList.add(dayGenerator(currentDate, i));
            }
        }

        private Day dayGenerator (GregorianCalendar currentDate,int i){
            //    генерируем дату
            //    (так, как заполняем не более одного месяца этот велосипед работает)
            GregorianCalendar date = new GregorianCalendar();
            date.roll(Calendar.DAY_OF_MONTH, i);
            if (date.get(Calendar.DAY_OF_MONTH) < currentDate.get(Calendar.DAY_OF_MONTH))
                date.roll(Calendar.MONTH, 1);
            if (date.get(Calendar.MONTH) < currentDate.get(Calendar.MONTH))
                date.roll(Calendar.YEAR, 1);
//    генерируем температуру
            int temperature;
            temperature = (int) (Math.random() * 20) - 10;
//    генерируем силу ветра
            int wind;
            wind = (int) (Math.random() * 20);
//    генерируем давление
            int pressure;
            pressure = (int) (Math.random() * 300) + 500;
//    генерируем влажность
            int humidity;
            humidity = (int) (Math.random() * 100);
//    генерируем атмосферные осадки
            Precipitation precipitation;
            precipitation = (Precipitation.values())[(int) (Math.random() * 4)];

            Day day = new Day(date, temperature, wind, pressure, humidity, precipitation);
            return day;
        }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return super.onCreateOptionsMenu(menu);
        }

        @Override
        public boolean onOptionsItemSelected (@NonNull MenuItem item){
            int id = item.getItemId();
            CoordinatorLayout coordinatorLayout = findViewById(R.id.coordinator_layout);

            if (id == R.id.action_settings) {
                Snackbar.make(coordinatorLayout, "Настройки", Snackbar.LENGTH_SHORT).show();
            }
            if (id == R.id.action_about_the_program) {
                Snackbar.make(coordinatorLayout, "О программе", Snackbar.LENGTH_SHORT).show();
            }
            return super.onOptionsItemSelected(item);
        }
    }
