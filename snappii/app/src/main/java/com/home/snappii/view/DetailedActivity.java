package com.home.snappii.view;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.home.snappii.AppApplication;
import com.home.snappii.R;
import com.home.snappii.model.Location;
import com.home.snappii.model.Result;
import com.home.snappii.providers.DataProvider;

import javax.inject.Inject;

public class DetailedActivity extends AppCompatActivity implements DetailViewContract.View {

    private static final int REQUEST_OK = 100;
    private static final String KEY = "payload";

    public static void start(Context context, Result result) {
        Intent intent = new Intent(context, DetailedActivity.class);
        intent.putExtra(KEY, result);
        context.startActivity(intent);
    }

    private DetailedPresenter presenter;
    private Result result;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        ((AppApplication) getApplication()).getComponent().inject(this);

        result = (Result) getIntent().getExtras().get(KEY);

        presenter = new DetailedPresenter((AppApplication) getApplication());
        presenter.onAttachView(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(DetailedActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(DetailedActivity.this, new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, REQUEST_OK);
                } else {
                    presenter.onRequestData(result);
                }
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

        findViewById(R.id.phone)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + result.getCell()));
                startActivity(intent);
            }
        });

        findViewById(R.id.emailContainer)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        composeEmail(new String[] { result.getEmail() }, "Hello from Snappii");
                    }
                });

        TextView emailView = findViewById(R.id.email);
        emailView.setText(result.getEmail());

        TextView locationView = findViewById(R.id.location);
        bindAddress(locationView, result.getLocation());

        TextView nationalityView = findViewById(R.id.nationality);
        nationalityView.setText(result.getNat());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_OK:
                presenter.onRequestData(result);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return false;
    }

    @Override
    public void onSendEmail(String userProfile, String pathToImage) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("image/png");
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Hello from Snappii");
        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + pathToImage));
        emailIntent.putExtra(Intent.EXTRA_TEXT, userProfile);
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
        }
    }

    @Override
    public void onError() {
        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
    }

    private void bindAddress(TextView location, Location data) {
        location.setText(data.getStreet() + ", " + data.getCity() + ", " + data.getState());
    }

    private void composeEmail(String[] addresses, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}
