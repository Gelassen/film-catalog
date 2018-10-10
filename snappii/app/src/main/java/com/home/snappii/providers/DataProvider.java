package com.home.snappii.providers;


import android.content.Context;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.home.snappii.AppApplication;
import com.home.snappii.model.Data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.functions.Function;

public class DataProvider implements DataService {

    private static final String DEFAULT_SEED = "abc";

    @Inject
    @Named("network")
    Scheduler subscribeScheduler;

    @Inject
    @Named("ui")
    Scheduler observeScheduler;

    private final Context context;

    private NetworkService service;

    public DataProvider(AppApplication appApplication) {
        appApplication.getComponent().inject(this);
        context = appApplication;
        service = new NetworkService(appApplication);
    }

    public Observable<String> getMediaPath(final String url) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emiter) throws Exception {
                String path = "";
                FutureTarget<File> futureTarget = Glide.with(context)
                        .load(url)
                        .downloadOnly(512, 512);
                try {
                    File file = futureTarget.get();
                    path = file.getAbsolutePath();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                emiter.onNext(path);
                emiter.onComplete();
            }
        })
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String path) throws Exception {
                        File dstDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                        File dst = new File(dstDir, "tmp.png");
                        dst.createNewFile();
                        FileInputStream inStream = new FileInputStream(path);
                        FileOutputStream outStream = new FileOutputStream(dst);
                        FileChannel inChannel = inStream.getChannel();
                        FileChannel outChannel = outStream.getChannel();
                        inChannel.transferTo(0, inChannel.size(), outChannel);
                        inStream.close();
                        outStream.close();
                        return dst.getAbsolutePath();
                    }
                })
                .subscribeOn(subscribeScheduler)
                .observeOn(observeScheduler);
    }

    public Observable<Data> getData(int page, int results) {
        return service.getApi()
                .getData(page, results, DEFAULT_SEED)
                .subscribeOn(subscribeScheduler)
                .observeOn(observeScheduler);
    }

    @Override
    public Observable<Data> getData(int page, int results, String seed) {
        return service.getApi()
                .getData(page, results, seed)
                .subscribeOn(subscribeScheduler)
                .observeOn(observeScheduler);
    }

}
