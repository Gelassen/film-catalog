package com.home.fileexhibitsloader;


import android.content.Context;

import com.home.model.Exhibit;
import com.home.model.ExhibitsLoader;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FileExhibitsLoader implements ExhibitsLoader {

    private Context context;

    public FileExhibitsLoader(Context context) {
        this.context = context;
    }

    @Override
    public Observable<List<Exhibit>> getExhibitList() {
        return Observable.create(new ObservableOnSubscribe<List<Exhibit>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Exhibit>> emitter) throws Exception {
                List<Exhibit> result = new ExhibitsConverter()
                        .convert(context.getAssets().open("data.json"));

                emitter.onNext(result);
                emitter.onComplete();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
