package com.home.banktochka.githubuserssearch.users;


import com.home.banktochka.githubuserssearch.IApplication;

public class Presenter implements ViewContract.IPresenter {

    private ViewContract.IView view;

    public Presenter(IApplication application) {
        application.getComponent().inject(this);
    }

    @Override
    public void onViewAttach(ViewContract.IView view) {
        this.view = view;
    }

    @Override
    public void onRequestNewData() {

    }

    @Override
    public void onNextPage() {

    }

    @Override
    public void onDestroy() {

    }
}
