package com.example.filmcatalog.films;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.filmcatalog.BaseActivity;
import com.example.filmcatalog.IApplication;
import com.example.filmcatalog.R;
import com.example.filmcatalog.films.Films.View;

import net.danlew.android.joda.JodaTimeAndroid;

public class MainActivity extends BaseActivity<FilmsPresenter> implements View, SwipeRefreshLayout.OnRefreshListener {

    private static final int SINGLE_IN_ROW = 1;
    private static final int DOUBLE_IN_ROW = 2;

    private final int spanCount = SINGLE_IN_ROW;

    private String apiKey = "6ccd72a2a8fc239b13f209408fc31c33";

    private GridLayoutManager gridLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rv;
    private FilmsAdapter adapter;

    private android.view.View search;
    private android.view.View clear;

    private android.view.View notFoundView;
    private android.view.View failedRequestView;
    private android.view.View progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JodaTimeAndroid.init(this);

        final EditText filter = findViewById(R.id.filter);
        search = findViewById(R.id.search);
        clear = findViewById(R.id.clear);

        search.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                presenter.onSearchMovie(apiKey, filter.getText().toString());
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
        filter.setFilters(getFilters());
        clear.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                filter.getText().clear();
                clear.setVisibility(android.view.View.GONE);
            }
        });

        gridLayoutManager = new GridLayoutManager(getApplicationContext(), spanCount);
        gridLayoutManager.setSpanCount(getSpanCount(null));

        adapter = new FilmsAdapter(this, this);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        rv = findViewById(R.id.recyclerView);
        rv.setLayoutManager(gridLayoutManager);
        rv.setAdapter(adapter);
        rv.addItemDecoration(new ItemDecoration(8));

        progressView = findViewById(R.id.root_progress);
        notFoundView = findViewById(R.id.root_not_found);
        failedRequestView = findViewById(R.id.root_failed_request);
        failedRequestView.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                presenter.onPullToRefresh(apiKey);
            }
        });

        presenter.onAttachView(this);

        presenter.onPullToRefresh(apiKey);
    }

    @Override
    public void onRefresh() {
        presenter.onPullToRefresh(getString(R.string.api_key));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        gridLayoutManager.setSpanCount(getSpanCount(newConfig));
        rv.setLayoutManager(gridLayoutManager);
        rv.setAdapter(null);
        rv.setAdapter(adapter);
        rv.getRecycledViewPool().clear();
        rv.getRecycledViewPool().getRecycledView(0);
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
        presenter.onAttachView(this);
    }

    @Override
    public void onResult(com.example.filmcatalog.films.model.Films films) {
        adapter.update(films.getResults());
    }

    @Override
    public void onFilmsItemClick(String name) {
        Snackbar.make(findViewById(R.id.coordinatorLayout), name, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressPlaceholder() {
        progressView.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void hideProgressPlaceholder() {
        progressView.setVisibility(android.view.View.GONE);
    }

    @Override
    public void showFilmsNotFound(String query) {
        ((TextView) notFoundView.findViewById(R.id.data)).setText(getString(R.string.films_not_found, query));
        notFoundView.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void hidesFilmsNotFound() {
        notFoundView.setVisibility(android.view.View.GONE);
    }

    @Override
    public void showError() {
        failedRequestView.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void hidesError() {
        notFoundView.setVisibility(android.view.View.GONE);
    }

    private int getSpanCount(Configuration newConfig) {
        int spanCount;
        if (newConfig == null) {
            spanCount = (Configuration.ORIENTATION_PORTRAIT == getResources().getConfiguration().orientation) ? SINGLE_IN_ROW : DOUBLE_IN_ROW;
        } else {
            spanCount = (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) ? DOUBLE_IN_ROW : SINGLE_IN_ROW;
        }
        return spanCount;
    }

    public InputFilter[] getFilters() {
        return new InputFilter[] { new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                boolean isSomethingInInput = charSequence.length() != 0;
                clear.setVisibility(isSomethingInInput ? android.view.View.VISIBLE : android.view.View.GONE);
                return charSequence;
            }
        }};
    }
}
