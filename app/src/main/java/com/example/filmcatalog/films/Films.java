package com.example.filmcatalog.films;


import com.example.filmcatalog.Main;

public interface Films {

    interface View extends Main.View {
        void onResult(com.example.filmcatalog.films.model.Films films, boolean lastPage);

        void onFilterResult(com.example.filmcatalog.films.model.Films films, boolean lastPage);

        void onNextPage();

        void onFilmsItemClick(String name);

        void showPullToRefreshProgress();

        void hidePullToRefreshProgress();

        void showProgressPlaceholder();

        void hideProgressPlaceholder();

        void showFilmsNotFound(String query);

        void hidesFilmsNotFound();

        void showOnFailedRequest();

        void hideFailedRequest();

        void showError();

        void hidesError();

        void onRestoreState(int position);

        void showList();

        void hideList();

        void onClearList();
    }

    interface Presenter extends Main.Presenter {
        void onSearchMovie(String apiKey, String movie, boolean isFirstPage, boolean clearPreviousList, boolean hasData);

        void onPullToRefresh(String apiKey, boolean hasData);

        void onTryAgain(String apiKey, String searchFilter, boolean hasData);

        void onConfigurationChange();

        void saveListState(int position);

        void onFilterClear();
    }
}
