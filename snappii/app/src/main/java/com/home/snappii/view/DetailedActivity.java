package com.home.snappii.view;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.home.snappii.R;
import com.home.snappii.model.Location;
import com.home.snappii.model.Result;

public class DetailedActivity extends AppCompatActivity {

    private static final String KEY = "payload";

    public static void start(Context context, Result result) {
        Intent intent = new Intent(context, DetailedActivity.class);
        intent.putExtra(KEY, result);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        Result result = (Result) getIntent().getExtras().get(KEY);

        Log.d("TAG", "we got data");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView avatar = findViewById(R.id.avatar);
        Glide.with(this)
                .load(result.getPicture().getLarge())
                .into(avatar);

        getSupportActionBar().setTitle(result.getName().getFirst() + " " + result.getName().getLast());

        TextView phoneView = findViewById(R.id.phone);
        phoneView.setText(result.getCell());

        TextView emailView = findViewById(R.id.email);
        emailView.setText(result.getEmail());

        TextView locationView = findViewById(R.id.location);
        bindAddress(locationView, result.getLocation());

        TextView nationalityView = findViewById(R.id.nationality);
        nationalityView.setText(result.getNat());
    }

    private void bindAddress(TextView location, Location data) {
        location.setText(data.getStreet() + ", " + data.getCity() + ", " + data.getState());
    }

}
