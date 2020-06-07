package com.selen.metcast;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.selen.metcast.data.database.CitiesSource;

public class CitiesRecyclerViewAdapter extends RecyclerView.Adapter<CitiesRecyclerViewAdapter.ViewHolder> {

    private Fragment fragment;
    private SettingActivity.OnItemCityClickListener itemCityClickListener;
    private int menuPosition;
    private CitiesSource citiesSource;


    CitiesRecyclerViewAdapter(Activity activity, Fragment fragment, CitiesSource citiesSource) {
        this.fragment = fragment;
        this.citiesSource = citiesSource;
        if (activity instanceof SettingActivity) {
            itemCityClickListener = ((SettingActivity) activity).getItemCityClickListener();
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
        holder.getCity().setText(citiesSource.getCities().get(position).cityName);
        if (fragment != null) {
            fragment.registerForContextMenu(holder.getItem_city());
        }
    }

    @Override
    public int getItemCount() {
        return citiesSource == null ? 0 : (int) citiesSource.getCountCities();
    }

    void removeCityItem(int position) {
        citiesSource.removeCity(citiesSource.getCities().get(position));
        notifyItemRemoved(position);
    }

    int getMenuPosition() {
        return menuPosition;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView city;
        private FrameLayout item_city;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            city = itemView.findViewById(R.id.city);
            item_city = itemView.findViewById(R.id.item_city);
            item_city.setOnClickListener(itemClick);
            item_city.setOnLongClickListener(itemLongClick);
        }

        public TextView getCity() {
            return city;
        }

        public FrameLayout getItem_city() {
            return item_city;
        }

        private View.OnClickListener itemClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemCityClickListener != null) {
                    itemCityClickListener.onItemClick(city.getText().toString());
                }
            }
        };

        private View.OnLongClickListener itemLongClick = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                menuPosition = getAdapterPosition();
                return false;
            }
        };
    }

}
