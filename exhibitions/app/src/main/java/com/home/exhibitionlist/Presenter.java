package com.home.exhibitionlist;


import com.home.fileexhibitsloader.FileExhibitsLoader;
import com.home.model.Exhibit;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class Presenter {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private IView view;
    private FileExhibitsLoader loader;

    public Presenter(FileExhibitsLoader loader, IView view) {
        this.loader = loader;
        this.view = view;
    }

    public void onStart() {
        loader.getExhibitList()
                .subscribe(new Observer<List<com.home.model.Exhibit>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<Exhibit> exhibits) {
                        view.onModel(exhibits);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void onDestroy() {
        compositeDisposable.clear();
    }
}
