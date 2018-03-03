package com.example.filmcatalog;


public interface Main {

    interface View {

    }

    interface Presenter {
        void onAttachView(View view);

        void onDetachView();
    }
}
