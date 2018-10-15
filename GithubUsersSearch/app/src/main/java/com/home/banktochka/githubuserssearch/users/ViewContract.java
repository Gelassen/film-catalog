package com.home.banktochka.githubuserssearch.users;


public class ViewContract {

    interface IView {
        void onShowError();

        void onData();
    }

    interface IPresenter {
        void onViewAttach(IView view);

        void onRequestNewData();

        void onNextPage();

        void onDestroy();
    }
}
