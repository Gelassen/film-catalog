package com.example.filmcatalog;


import android.util.Log;

import com.example.filmcatalog.films.model.Films;
import com.example.filmcatalog.films.providers.FilmsProvider;

import java.io.Reader;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class Debug {

    private final OkHttpClient client;

    private String baseUrl = "https://api.themoviedb.org/3/";

    private String apiKey = "6ccd72a2a8fc239b13f209408fc31c33";

    private FilmsProvider provider;

    public Debug(IApplication application) {
        provider = new FilmsProvider(application);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }

    public void runFilmsProvider() {
        provider.getFilms(apiKey, "ru-RU", "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getSubscriber());
    }

    private Observer<? super Films> getSubscriber() {
        return new SimpleObserver<Films>() {

            @Override
            public void onNext(Films films) {
                super.onNext(films);
                Log.d(App.TAG, "Films: " + films.getResults().size());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                Log.e(App.TAG, "Failed finish", e);
            }
        };
    }

    public void runAsync() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Debug.this.run();
                } catch (Exception e) {
                    Log.e(App.TAG, "Run app", e);
                }
            }
        });
        thread.start();
    }

    public void run() throws Exception {
        Request request = new Request.Builder()
                .url("https://api.themoviedb.org/3/discover/movie?api_key=6ccd72a2a8fc239b13f209408fc31c33")
                .build();

        Response response = client.newCall(request).execute();
        Reader reader = response.body().charStream();

        StringBuilder result = new StringBuilder();
        char[] buffer = new char[64];
        while (reader.read(buffer) != -1) {
            result.append(buffer);
        }

        Log.d(App.TAG, "Result: " + result.toString());
    }

}
