package com.example.filmcatalog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {

    protected T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();

        presenter = (T) getLastCustomNonConfigurationInstance();
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
