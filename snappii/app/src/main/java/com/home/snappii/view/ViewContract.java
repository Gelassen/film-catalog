package com.home.snappii.view;


import com.home.snappii.model.Result;

import java.util.List;

public class ViewContract {

    public interface IView {
        void show(List<Result> users);

        void onError();
    }

    public interface IPresenter {
        void onAttachView(IView view);

        void requestData();

        void requestNextPage();

        void onDestroy();

        void onPullToRefresh();
    }
}
