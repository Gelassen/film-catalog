package com.example.filmcatalog.films;

import android.os.Bundle;

import com.example.filmcatalog.BaseActivity;
import com.example.filmcatalog.IApplication;
import com.example.filmcatalog.R;
import com.example.filmcatalog.di.IComponent;
import com.example.filmcatalog.films.Films.View;

public class MainActivity extends BaseActivity implements View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
