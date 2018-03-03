package com.example.filmcatalog.films;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.filmcatalog.R;
import com.example.filmcatalog.films.model.Result;

import java.util.ArrayList;
import java.util.List;

public class FilmsAdapter extends Adapter<FilmsAdapter.FilmsViewHolder> {

    private List<Result> dataSource = new ArrayList<>();

    public void update(List<Result> results) {
        dataSource.clear();
        dataSource.addAll(results);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FilmsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_movie_item, parent);
        return new FilmsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }


    public class FilmsViewHolder extends RecyclerView.ViewHolder {

        public FilmsViewHolder(View itemView) {
            super(itemView);
        }
    }
}
