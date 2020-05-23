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

import com.selen.metcast.data.MainSingleton;

import java.util.List;

public class CitiesRecyclerViewAdapter extends RecyclerView.Adapter<CitiesRecyclerViewAdapter.ViewHolder> {

    private List<String> cities;
    private Fragment fragment;
    private Setting.OnItemCityClickListener itemCityClickListener;
    private int menuPosition;


    CitiesRecyclerViewAdapter(Activity activity, Fragment fragment) {
        cities = MainSingleton.getInstance().getCitiesList();
        this.fragment = fragment;
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
        fragment.registerForContextMenu(holder.getItem_city());
    }

    @Override
    public int getItemCount() {
        return cities == null ? 0 : cities.size();
    }

    void removeCityItem(int position){
        cities.remove(position);
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
            item_city.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemCityClickListener != null) {
                        itemCityClickListener.onItemClick(city.getText().toString());
                    }
                }
            });

            item_city.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    menuPosition = getAdapterPosition();
                    return false;
                }
            });

        }

        public TextView getCity() {
            return city;
        }

        public FrameLayout getItem_city() {
            return item_city;
        }

    }

}
