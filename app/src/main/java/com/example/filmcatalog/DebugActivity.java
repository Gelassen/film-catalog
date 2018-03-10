package com.example.filmcatalog;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.filmcatalog.films.Films;
import com.example.filmcatalog.films.FilmsAdapter;
import com.example.filmcatalog.films.ItemDecoration;

public class DebugActivity extends Activity {
    private RecyclerView rv;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


}
