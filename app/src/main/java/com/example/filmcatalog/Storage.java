package com.example.filmcatalog;


import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.filmcatalog.films.model.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class Storage {

    public void put(List<Result> data) {
        Gson gson = new GsonBuilder().create();
        String result = gson.toJson(data);
        Log.d(App.TAG, "Result: " + result);
    }

    public void save(Context context) {
//        PreferenceManager.getDefaultSharedPreferences(context).edit().put
    }
}
