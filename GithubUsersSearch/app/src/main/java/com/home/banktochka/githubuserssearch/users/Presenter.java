package com.home.banktochka.githubuserssearch.users;


import com.home.banktochka.githubuserssearch.IApplication;
import com.home.banktochka.githubuserssearch.users.model.Item;
import com.home.banktochka.githubuserssearch.users.model.UsersModel;
import com.home.banktochka.githubuserssearch.users.providers.UsersProvider;
import com.home.banktochka.githubuserssearch.utils.SimpleObserver;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import retrofit2.HttpException;

public class Presenter implements ViewContract.IPresenter {

    @Inject
    @Named("network")
    Scheduler subscribeScheduler;

    @Inject
    @Named("ui")
    Scheduler observeScheduler;

    @Inject
    UsersModel model;

    @Inject
    UsersProvider provider;

    private ViewContract.IView view;

    public Presenter(IApplication application) {
        application.getComponent().inject(this);
    }

    @Override
    public void onViewAttach(ViewContract.IView view) {
        this.view = view;
    }

    @Override
    public void onRequestNewData(String search) {
        model.setSearch(search);
        provider.getUsers(model.getSearch(), String.valueOf(model.getPage()))
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        model.onNextPage();
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        model.onError();
                    }
                })
                .subscribeOn(subscribeScheduler)
                .observeOn(observeScheduler)
                .subscribe(getUsersObservable());
    }

    @Override
    public void onNextPage() {

    }

    @Override
    public void onDestroy() {

    }

    private Observer<List<Item>> getUsersObservable() {
        return new SimpleObserver<List<Item>>() {
            @Override
            public void onNext(List<Item> data) {
                super.onNext(data);
                view.onData(data);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                final int failedToProcessEntity = 422;
                if (e instanceof HttpException
                        && ((HttpException) e).code() == failedToProcessEntity) {
                    view.onData(Collections.<Item>emptyList());
                } else {
                    view.onShowError();
                }
            }
        };
    }

}
