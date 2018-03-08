package com.example.filmcatalog.films;


import com.example.filmcatalog.Main;

public interface Films {

    interface View extends Main.View {
        void onResult(com.example.filmcatalog.films.model.Films films, boolean lastPage);

        void onNextPage();

        void onFilmsItemClick(String name);

        void showProgressPlaceholder();

        void hideProgressPlaceholder();

        void showFilmsNotFound(String query);

        void hidesFilmsNotFound();

        void showError();

        void hidesError();
    }

    interface Presenter extends Main.Presenter {
        void onSearchMovie(String apiKey, String movie);

        void onPullToRefresh(String apiKey);
    }
}
