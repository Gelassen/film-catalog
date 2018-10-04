package com.home.fileexhibitsloader;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.home.model.Exhibit;
import com.home.model.ExhibitList;

import org.json.JSONArray;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class ExhibitsConverter {

    public List<Exhibit> convert(InputStream is) {
        return new GsonBuilder()
                .create()
                .fromJson(new InputStreamReader(is), ExhibitList.class)
                .getList();
    }
}
