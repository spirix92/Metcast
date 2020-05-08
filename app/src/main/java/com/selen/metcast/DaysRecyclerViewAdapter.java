package com.selen.metcast;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.selen.metcast.data.Day;
import com.selen.metcast.data.MainSingleton;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;

public class DaysRecyclerViewAdapter extends RecyclerView.Adapter<DaysRecyclerViewAdapter.ViewHolder> {

    private List<Day> days;

    DaysRecyclerViewAdapter() {
        days = MainSingleton.getInstance().getDaysList();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_day, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DaysRecyclerViewAdapter.ViewHolder holder, int position) {
        GregorianCalendar calendar = days.get(position).getDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy");
        SimpleDateFormat weekFormat = new SimpleDateFormat("EEEE");

        holder.getDayOfMonth().setText(dateFormat.format(calendar.getTime()));
        holder.getDayOfWeek().setText(weekFormat.format(calendar.getTime()));

        int temp = days.get(position).getTemperature();
        holder.getTemperature().setText(String.valueOf(temp));
        holder.getItem_day().setBackgroundColor((temp > 0)
                ? holder.getItem_day().getResources().getColor(R.color.sun)
                : holder.getItem_day().getResources().getColor(R.color.snow));

        holder.getTemperaturePicture().setImageDrawable(days.get(position)
                .getPrecipitation().getPicture(holder.getItem_day().getResources()));
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView dayOfMonth;
        private TextView dayOfWeek;
        private TextView temperature;
        private ImageView temperaturePicture;
        private ConstraintLayout item_day;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            dayOfMonth = itemView.findViewById(R.id.day_of_month);
            dayOfWeek = itemView.findViewById(R.id.day_of_week);
            temperature = itemView.findViewById(R.id.temperature);
            temperaturePicture = itemView.findViewById(R.id.temperature_picture);
            item_day = itemView.findViewById(R.id.item);
        }

        TextView getDayOfMonth() {
            return dayOfMonth;
        }

        TextView getDayOfWeek() {
            return dayOfWeek;
        }

        TextView getTemperature() {
            return temperature;
        }

        ImageView getTemperaturePicture() {
            return temperaturePicture;
        }

        ConstraintLayout getItem_day() {
            return item_day;
        }
    }

}
