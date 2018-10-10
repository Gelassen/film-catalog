package com.home.snappii.view;


import com.home.snappii.model.Result;

public class DetailViewContract {
    interface View {
        void onSendEmail(String userProfile, String pathToImage);

        void onError();
    }

    interface Presenter {
        void onAttachView(View view);

        void onRequestData(Result result);
    }
}
