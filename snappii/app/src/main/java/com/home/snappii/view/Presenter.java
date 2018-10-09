package com.home.snappii.view;


import com.home.snappii.AppApplication;
import com.home.snappii.model.Data;
import com.home.snappii.providers.DataProvider;
import com.home.snappii.utils.SimpleObserver;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class Presenter implements ViewContract.IPresenter {

    @Inject
    DataProvider dataProvider;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private ViewContract.IView view;

    private int page = 0;
    private int results = 20;

    public Presenter(AppApplication appApplication) {
        appApplication.getComponent().inject(this);
    }

    @Override
    public void onAttachView(ViewContract.IView view) {
        this.view = view;
    }

    @Override
    public void requestData() {
        dataProvider.getData(page, results)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        page++;
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        page--;
                    }
                })
                .subscribe(getDataObserver());
    }

    @Override
    public void requestNextPage() {
        requestData();
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
    }

    @Override
    public void onPullToRefresh() {
        reset();
        requestData();
    }

    private void reset() {
        page = 0;
    }

    private SimpleObserver<Data> getDataObserver() {
        return new SimpleObserver<Data>() {
            @Override
            public void onNext(Data data) {
                super.onNext(data);
                view.show(data.getResults());
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.onError();
            }
        };
    }

}
