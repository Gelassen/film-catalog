package com.example.filmcatalog.films;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.filmcatalog.App;
import com.example.filmcatalog.BaseActivity;
import com.example.filmcatalog.IApplication;
import com.example.filmcatalog.R;
import com.example.filmcatalog.Storage;
import com.example.filmcatalog.films.Films.View;

import net.danlew.android.joda.JodaTimeAndroid;

public class MainActivity extends BaseActivity<FilmsPresenter> implements View, SwipeRefreshLayout.OnRefreshListener {

    private static final int SINGLE_IN_ROW = 1;
    private static final int DOUBLE_IN_ROW = 2;

    private final int spanCount = SINGLE_IN_ROW;

    private StaggeredGridLayoutManager gridLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView rv;
    private FilmsAdapter adapter;

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
        clear = findViewById(R.id.clear);

        filter.setFilters(getFilters());
        clear.setOnClickListener(getClearListener(filter));

        ImageView update = findViewById(R.id.update);
        update.setOnClickListener(getSearchMovieListener(filter));

        gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
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
                presenter.onPullToRefresh(getString(R.string.api_key));
            }
        });

        presenter.onAttachView(this);

        presenter.onPullToRefresh(getString(R.string.api_key));

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                final int[] position = gridLayoutManager.findFirstCompletelyVisibleItemPositions(null);
                presenter.saveListState(position[0]);
            }
        });
    }

    @Override
    public void onRefresh() {
        presenter.onPullToRefresh(getString(R.string.api_key));
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        gridLayoutManager.setSpanCount(getSpanCount(newConfig));

        rv.setLayoutManager(gridLayoutManager);
        rv.setAdapter(null);
        rv.setAdapter(adapter);
        rv.getRecycledViewPool().clear();
        rv.getRecycledViewPool().getRecycledView(0);

        presenter.onConfigurationChange();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetachView();
        adapter.onDestroy();
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
    public void onResult(com.example.filmcatalog.films.model.Films films, boolean lastPage) {
        adapter.update(films.getResults(), !lastPage);
        swipeRefreshLayout.setRefreshing(false);

        Storage storage = new Storage();
        storage.put(films.getResults());
    }

    @Override
    public void onFilterResult(com.example.filmcatalog.films.model.Films films, boolean lastPage) {
        adapter.clear();
        adapter.update(films.getResults(), !lastPage);
        swipeRefreshLayout.setRefreshing(false);
        hideKeyboard();
    }

    @Override
    public void onNextPage() {
        if (isSearchMode()) {
            presenter.onSearchMovie(getString(R.string.api_key), getSearchText(), false);
        } else {
            presenter.onPullToRefresh(getString(R.string.api_key));
        }
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
        Snackbar.make(findViewById(R.id.coordinatorLayout), getString(R.string.network_error), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void hidesError() {
        notFoundView.setVisibility(android.view.View.GONE);
    }

    @Override
    public void onRestoreState(int position) {
        rv.scrollToPosition(position);
    }

    @NonNull
    private android.view.View.OnClickListener getSearchMovieListener(final EditText filter) {
        return new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                // TODO I need more time to sync about logic of 1s delay. RX api might have out-of-box solution
                presenter.onSearchMovie(
                        getString(R.string.api_key),
                        filter.getText().toString(),
                        false
                );
            }
        };
    }

    @NonNull
    private android.view.View.OnClickListener getClearListener(final EditText filter) {
        return new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                filter.getText().clear();
                clear.setVisibility(android.view.View.GONE);
            }
        };
    }

    private String getSearchText() {
        EditText filter = findViewById(R.id.filter);
        String words = filter.getText().toString();
        return words;
    }

    private boolean isSearchMode() {
        EditText filter = findViewById(R.id.filter);
        String words = filter.getText().toString();
        return words.length() != 0;
    }

    private int getSpanCount(Configuration newConfig) {
        int spanCount;
        if (newConfig == null) {
            spanCount = (Configuration.ORIENTATION_PORTRAIT == getResources().getConfiguration().orientation) ? SINGLE_IN_ROW : DOUBLE_IN_ROW;
        } else {
            spanCount = (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) ? DOUBLE_IN_ROW : SINGLE_IN_ROW;
        }
        Log.d(App.TAG, "Span count: " + spanCount);
        return spanCount;
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(findViewById(R.id.coordinatorLayout).getWindowToken(), 0);
    }

    private InputFilter[] getFilters() {
        return new InputFilter[] { new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                EditText filter = findViewById(R.id.filter);
                String words = filter.getText().toString();
                boolean isSomethingInInput = words.length() != 0;
                clear.setVisibility(isSomethingInInput ? android.view.View.VISIBLE : android.view.View.GONE);
                presenter.onSearchMovie(MainActivity.this.getString(R.string.api_key), words, true);
                return charSequence;
            }
        }};
    }
}
