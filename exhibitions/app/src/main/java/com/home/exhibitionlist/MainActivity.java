package com.home.exhibitionlist;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.home.fileexhibitsloader.FileExhibitsLoader;
import com.home.model.Exhibit;

import java.util.List;

public class MainActivity extends AppCompatActivity implements IView {

    private Presenter presenter;
    private ExhibitionAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new ExhibitionAdapter();

        RecyclerView view = findViewById(R.id.list);
        view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        view.setAdapter(adapter);

        presenter = new Presenter(new FileExhibitsLoader(this), this);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        presenter.onStart();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onModel(List<Exhibit> data) {
        adapter.updateModel(data);
    }

    @Override
    public void onError() {
        Toast.makeText(this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
    }

}


