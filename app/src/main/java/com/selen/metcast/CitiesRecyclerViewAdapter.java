package com.selen.metcast;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.selen.metcast.data.MainSingleton;

import java.util.List;

public class CitiesRecyclerViewAdapter extends RecyclerView.Adapter<CitiesRecyclerViewAdapter.ViewHolder> {

    private List<String> cities;
    private Setting.OnItemCityClickListener itemCityClickListener;

    CitiesRecyclerViewAdapter(Activity activity) {
        cities = MainSingleton.getInstance().getCitiesList();
        if (activity instanceof Setting) {
            itemCityClickListener = ((Setting) activity).getItemCityClickListener();
        }
    }

    @NonNull
    @Override
    public CitiesRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_city, parent, false);
        return new CitiesRecyclerViewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CitiesRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.getCity().setText(cities.get(position));
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView city;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            city = itemView.findViewById(R.id.city);
            ConstraintLayout item_city = itemView.findViewById(R.id.item_city);
            item_city.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemCityClickListener != null) {
                        itemCityClickListener.onItemClick(city.getText().toString());
                    }
                }
            });
        }

        public TextView getCity() {
            return city;
        }
    }

}
