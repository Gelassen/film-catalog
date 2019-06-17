package ru.rsprm.test;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.rsprm.model.Truck;
import ru.rsprm.network.Api;

import java.util.List;

public class Repo {

    private Api api;

    public Repo(String url) {
        OkHttpClient client = new OkHttpClient.Builder().build();

        api = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(url)
                .build().create(Api.class);
    }

    public void run() {
        api.getTrucks()
                .subscribeOn(Schedulers.trampoline())
                .observeOn(Schedulers.trampoline())
                .subscribe(new Subscriber<List<Truck>>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        // no op
                    }

                    @Override
                    public void onNext(List<Truck> trucks) {
                        String str = "result";
                    }

                    @Override
                    public void onError(Throwable t) {
                        String str = "error";
                    }

                    @Override
                    public void onComplete() {
                        // no op
                    }
                });
    }
}
