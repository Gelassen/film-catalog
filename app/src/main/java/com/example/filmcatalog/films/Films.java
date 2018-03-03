package com.example.filmcatalog.films;


import com.example.filmcatalog.Main;

public interface Films {

    interface View extends Main.View {

        void onResult(com.example.filmcatalog.films.model.Films films);

        void onError();
    }

    interface Presenter extends Main.Presenter {
        void onViewCreated();

        void onSearchMovie(String apiKey, String movie);

        void onPullToRefresh(String apiKey);
    }
}
