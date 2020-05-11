package com.selen.metcast;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.selen.metcast.data.Constants;

public class FragmentDaysRecyclerView extends Fragment {

    private String currentCIty;

    public FragmentDaysRecyclerView() {

    }

    static FragmentDaysRecyclerView newInstance(String currentCIty) {
        FragmentDaysRecyclerView fragment = new FragmentDaysRecyclerView();
        Bundle args = new Bundle();
        args.putString(Constants.PUT_CURRENT_CITY_MAIN_ACTIVITY, currentCIty);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentCIty = getArguments().getString(Constants.PUT_CURRENT_CITY_MAIN_ACTIVITY);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(Constants.PUT_CURRENT_CITY_MAIN_ACTIVITY, currentCIty);
        super.onSaveInstanceState(outState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_days_recycler_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        DaysRecyclerViewAdapter adapter = new DaysRecyclerViewAdapter();
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(view.getResources().getDrawable(R.drawable.separator));
        recyclerView.addItemDecoration(itemDecoration);

        recyclerView.setAdapter(adapter);

        adapter.SetOnItemClickListener(new DaysRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                CurrentDayFragment detail = CurrentDayFragment.newInstance(position, currentCIty);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.main_fragment_current_day, detail);
                ft.commit();
            }
        });
    }

}
