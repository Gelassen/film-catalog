package com.example.filmcatalog;


import android.content.Context;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

public class Favorites {

    private static final String EXTRA = "EXTRA";

    private HashMap<Integer, Boolean> favs = new HashMap<>();

    public Favorites() {
        favs = new HashMap<>();
    }

    public void setFavorities(int id, boolean fav) {
        favs.put(id, fav);
    }

    public boolean isFav(int id) {
        return favs.get(id) == null ? false : favs.get(id);
    }

    public void save(Context context) {
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(this);
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(EXTRA, json)
                .commit();
    }

    public static Favorites restore(Context context) {
        String json = PreferenceManager.getDefaultSharedPreferences(context).getString(EXTRA, "");
        Gson gson = new GsonBuilder().create();
        Favorites result = gson.fromJson(json, Favorites.class);
        return result == null ? new Favorites() : result;
    }
}
