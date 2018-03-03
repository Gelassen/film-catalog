package com.example.filmcatalog.films;

import android.os.Bundle;

import com.example.filmcatalog.BaseActivity;
import com.example.filmcatalog.R;

public class MainActivity extends BaseActivity {

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
        // TODO create custom presenter
    }

    @Override
    protected void onCreateView() {
        // TODO attach view to presenter
    }
}
