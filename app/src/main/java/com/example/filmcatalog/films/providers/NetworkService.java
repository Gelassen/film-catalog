package com.example.filmcatalog.films.providers;

import com.example.filmcatalog.IApplication;

import javax.inject.Inject;
import javax.inject.Named;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    @Inject
    @Named("media")
    String url;

    private FilmsService service;

    public NetworkService(IApplication application) {
        application.getComponent().inject(this);
        service = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(FilmsService.class);
    }

    public FilmsService getApi() {
        return service;
    }
}
