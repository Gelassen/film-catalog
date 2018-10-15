package com.home.banktochka.githubuserssearch.users.providers;


import com.home.banktochka.githubuserssearch.users.model.Data;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserService {

    @GET("/search/users")
    Observable<Data> getUsers(@Query("q") String search,
                              @Query("page") String page);

}
