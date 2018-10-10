package com.home.snappii.view;


import com.home.snappii.AppApplication;
import com.home.snappii.model.Data;
import com.home.snappii.model.Location;
import com.home.snappii.model.Result;
import com.home.snappii.providers.DataProvider;
import com.home.snappii.utils.SimpleObserver;

import javax.inject.Inject;

public class DetailedPresenter implements DetailViewContract.Presenter {

    @Inject
    DataProvider dataProvider;

    private DetailViewContract.View view;

    public DetailedPresenter(AppApplication appApplication) {
        appApplication.getComponent().inject(this);
    }

    @Override
    public void onAttachView(DetailViewContract.View view) {
        this.view = view;
    }

    @Override
    public void onRequestData(Result result) {
        dataProvider.getMediaPath(result.getPicture().getLarge())
                .subscribe(getDataObserver(result));
    }

    private SimpleObserver<String> getDataObserver(final Result result) {
        return new SimpleObserver<String>() {
            @Override
            public void onNext(String pathToImage) {
                super.onNext(pathToImage);
                view.onSendEmail(getUserProfile(result), pathToImage);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                view.onError();
            }

            // TODO move to separate provider
            private String getUserProfile(Result result) {
                StringBuilder builder = new StringBuilder();
                builder.append(result.getName().getFirst() + " ");
                builder.append(result.getName().getLast() + " \n");
                builder.append(result.getNat() + "\n");
                builder.append(result.getPhone() + "\n");
                builder.append(result.getEmail() + "\n");

                Location location = result.getLocation();
                builder.append(location.getStreet() + ", " + location.getCity() + ", " + location.getState());
                return builder.toString();
            }
        };
    }
}
