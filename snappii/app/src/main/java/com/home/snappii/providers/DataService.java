package com.home.snappii.providers;


import com.home.snappii.model.Data;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DataService {

    @GET("/api/")
    Observable<Data> getData(@Query("page") int page, @Query("results") int results, @Query("seed") String seed);
}
