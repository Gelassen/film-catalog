package com.example.filmcatalog.films;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.filmcatalog.App;
import com.example.filmcatalog.R;
import com.example.filmcatalog.films.model.Result;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.http.Url;

public class FilmsAdapter extends Adapter<FilmsAdapter.FilmsViewHolder> {

    private Context context;
    private List<Result> dataSource = new ArrayList<>();

    public void update(List<Result> results) {
        dataSource.clear();
        dataSource.addAll(results);
        notifyDataSetChanged();
    }

    public FilmsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FilmsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        GlideApp.with(this);//.load("http://goo.gl/gEgYUd").into(imageView);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_movie_item, parent, false);
        return new FilmsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmsViewHolder holder, int position) {
        Result item = dataSource.get(position);
        holder.title.setText(item.getTitle());
        holder.desc.setText(item.getOverview());
        Glide.with(context)
                .load(context.getString(R.string.base_url) + item.getPosterPath())
                .into(holder.avatar);
        // TODO add placeholder
        holder.date.setText(getFormmattedData(item.getReleaseDate()));

    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    private String getFormmattedData(String date) {
        DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("yyyy-MM-dd");
        DateTime dateTime = dateTimeFormat.parseDateTime("2016-02-11");

        DateTimeFormatter dateTimeFormatSecond = DateTimeFormat.forPattern("dd MMMM yyyy");
        return dateTimeFormatSecond.print(dateTime);
    }

    public class FilmsViewHolder extends RecyclerView.ViewHolder {

        private final ImageView avatar;
        private final TextView title;
        private final TextView desc;
        private final TextView date;
        private final ImageView fav;

        public FilmsViewHolder(View view) {
            super(view);

            avatar = view.findViewById(R.id.icon);
            title = view.findViewById(R.id.title);
            desc = view.findViewById(R.id.desc);
            date = view.findViewById(R.id.date);
            fav = view.findViewById(R.id.fav);
        }
    }
}
