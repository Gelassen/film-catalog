package com.example.filmcatalog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    protected BasePresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = (BasePresenter) getLastCustomNonConfigurationInstance();
        if (presenter == null) {
            createPresenter();
        }
        onCreateView();
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }

    protected abstract void createPresenter();

    protected abstract void onCreateView();

}
