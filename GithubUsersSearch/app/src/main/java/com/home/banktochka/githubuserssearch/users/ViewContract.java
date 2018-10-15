package com.home.banktochka.githubuserssearch.users;


import com.home.banktochka.githubuserssearch.users.model.Item;

import java.util.List;

public class ViewContract {

    interface IView {
        void onShowError();

        void onData(List<Item> data);
    }

    interface IPresenter {
        void onViewAttach(IView view);

        void onRequestNewData(String search);

        void onNextPage();

        void onDestroy();
    }
}
