package com.selen.metcast;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.selen.metcast.data.Constants;
import com.selen.metcast.data.init_data.DaysListInitiaterableBuilder;


public class MainActivity extends BaseActivity {

    private int startPosition;
    private String savedCity;
    private OnItemDayClickListener itemDayClickListener;
    private AppBarLayout appbarLayout;
    private CoordinatorLayout coordinatorLayout;
    private DaysListInitiaterableBuilder.FragmentsInitiator initiator;
    private AirplaneReceiver airplaneReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.main_toolbar_layout);
        appbarLayout = findViewById(R.id.main_appbar_layout);
        coordinatorLayout = findViewById(R.id.coordinator_layout);
        setSupportActionBar(toolbar);
        itemDayClickListener = new OnItemDayClickListener() {
            @Override
            public void onItemClick(int position, String city) {
                replaceFragmentCurrentDay(position, city);
            }
        };

        if (savedInstanceState == null) {
            SharedPreferences sharedPref = getSharedPreferences(Constants.NAME_SHARED_PREFERENCE_CITY, MODE_PRIVATE);
            savedCity = sharedPref.getString(Constants.GET_CITY_NAME, getResources().getString(R.string.start_city));
            initFragments();
        } else {
            savedCity = savedInstanceState.getString(Constants.CURRENT_CITY_MAIN_ACTIVITY);
        }

        initNotificationChannel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        airplaneReceiver = new AirplaneReceiver(getString(R.string.broadcast_airplane_text),
                getString(R.string.broadcast_airplane));
        registerReceiver(airplaneReceiver, new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED));
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (airplaneReceiver !=null)
            unregisterReceiver(airplaneReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            openSettings();
        }
        if (id == R.id.action_broadcast_storm) {
            sendBroadcastStorm();
        }
        if (id == R.id.action_about_the_program) {
            showDialogAboutTheProgram();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.OPEN_SETTINGS_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                savedCity = data.getStringExtra(Constants.PUT_CURRENT_CITY_MAIN_ACTIVITY);
                SharedPreferences sharedPref = getSharedPreferences(Constants.NAME_SHARED_PREFERENCE_CITY, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(Constants.GET_CITY_NAME, savedCity);
                editor.commit();
                initFragments();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.CURRENT_CITY_MAIN_ACTIVITY, savedCity);
    }

    //    добавляем фрагмент текущего дня
    public void replaceFragmentCurrentDay(int startPosition, String savedCity) {
        CurrentDayFragment currentDayFragment = CurrentDayFragment.newInstance(startPosition, savedCity);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_fragment_current_day, currentDayFragment).commit();
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            appbarLayout.setExpanded(true, true);
    }

    //    добавляем фрагмент со списком дней
    public void replaceFragmentRecyclerView(String savedCity) {
        FragmentDaysRecyclerView recyclerViewDayFragment = FragmentDaysRecyclerView.newInstance(savedCity);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_fragment_days_recycler_view, recyclerViewDayFragment).commit();
    }

    private void openSettings() {
        Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
        startActivityForResult(intent, Constants.OPEN_SETTINGS_REQUEST_CODE);
    }

    private void replaceFragments(boolean result) {
        replaceFragmentCurrentDay(startPosition, savedCity);
        replaceFragmentRecyclerView(savedCity);
        if (!result) {
            showDialogRandomGenerate();
        } else {
            Snackbar.make(coordinatorLayout,
                    getString(R.string.data_uploaded), Snackbar.LENGTH_SHORT).show();
        }
    }

    public void initFragments() {
        final DaysListInitiaterableBuilder builder = new DaysListInitiaterableBuilder(savedCity, Constants.DAYS_IN_LIST);
        initiator = builder.getFragmentsInitiator();
        initiator = new DaysListInitiaterableBuilder.FragmentsInitiator() {
            @Override
            public void initiateFragments(boolean result) {
                if (!result) {
//                    сгенерировать случайные данные
                    builder.buildWithRandom();
                }
                replaceFragments(result);
            }
        };
        builder.setFragmentsInitiator(initiator);
        builder.buildWithAPI();
    }

    private void showDialogAboutTheProgram() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.dialog_about_title)
                .setMessage(R.string.dialog_about_message)
                .setIcon(R.mipmap.ic_launcher_round)
                .setCancelable(false)
                .setPositiveButton(R.string.dialog_about_btn_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
//                                закрывается и хорошо
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void showDialogRandomGenerate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.dialog_random_title)
                .setMessage(R.string.dialog_random_message)
                .setIcon(R.mipmap.ic_launcher_round)
                .setCancelable(false)
                .setPositiveButton(R.string.dialog_random_btn_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                openSettings();
                            }
                        })
                .setNegativeButton(R.string.dialog_random_btn_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                                закрывается и хорошо
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    //    отправить тестовое широковещательное сообщение о штормовом предупреждении
    @SuppressLint("WrongConstant")
    private void sendBroadcastStorm() {
        String msg = getString(R.string.broadcast_storm_text);
        String msgTitle = getString(R.string.broadcast_storm);
        Intent intent = new Intent();
        intent.setAction(Constants.ACTION_SEND_MSG);
        intent.putExtra(Constants.STORM_NAME_MSG, msg);
        intent.putExtra(Constants.STORM_NAME_MSG_TITLE, msgTitle);
        intent.addFlags(Constants.FLAG_RECEIVER_INCLUDE_BACKGROUND);
        sendBroadcast(intent);
    }

    // инициализация канала нотификаций
    private void initNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel(Constants.NOTIFICATION_CHANNEL, "name", importance);
            notificationManager.createNotificationChannel(channel);
        }
    }

    // Интерфейс для обработки нажатий
    public interface OnItemDayClickListener {
        void onItemClick(int position, String city);
    }

    public OnItemDayClickListener getItemDayClickListener() {
        return itemDayClickListener;
    }
}
