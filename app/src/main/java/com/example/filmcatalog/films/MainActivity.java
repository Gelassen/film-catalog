package com.example.filmcatalog.films;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.filmcatalog.App;
import com.example.filmcatalog.BaseActivity;
import com.example.filmcatalog.Debug;
import com.example.filmcatalog.IApplication;
import com.example.filmcatalog.R;
import com.example.filmcatalog.films.Films.View;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class MainActivity extends BaseActivity<FilmsPresenter> implements View, SwipeRefreshLayout.OnRefreshListener {

    private String apiKey = "6ccd72a2a8fc239b13f209408fc31c33";

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rv;
    private FilmsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JodaTimeAndroid.init(this);

        adapter = new FilmsAdapter(this);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        rv = findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(adapter);

        presenter.onAttachView(this);

        presenter.onPullToRefresh(apiKey);

//        Debug debug = new Debug((IApplication) getApplication());
//        debug.runFilmsProvider();
    }

    @Override
    public void onRefresh() {
        presenter.onPullToRefresh(getString(R.string.api_key));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetachView();
    }

    @Override
    protected void createPresenter() {
        presenter = new FilmsPresenter(((IApplication) getApplication()).getComponent());
    }

    @Override
    protected void onCreateView() {
        // TODO attach view to presenter
        presenter.onAttachView(this);
    }

    @Override
    public void onResult(com.example.filmcatalog.films.model.Films films) {
        // TODO update model
        adapter.update(films.getResults());
        Log.d("TAG", "On result: " + films.getResults().size());
    }

    @Override
    public void onError() {
        // TODO show error state
        Log.d("TAG", "On error");
    }
}
