package com.home.exhibitionlist;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ImageView imageView = findViewById(R.id.image);
        Picasso.with(this).load("https://upload.wikimedia.org/wikipedia/commons/f/fd/IPhone_5S.jpg")
                .fit()
                .centerCrop()
                .into(imageView);
    }
}
