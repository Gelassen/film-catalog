package com.home.banktochka.githubuserssearch.di;


import com.home.banktochka.githubuserssearch.users.Presenter;
import com.home.banktochka.githubuserssearch.users.providers.NetworkService;
import com.home.banktochka.githubuserssearch.users.providers.UsersProvider;

public interface IComponent {
    void inject(UsersProvider usersProvider);

    void inject(NetworkService networkService);

    void inject(Presenter presenter);
}
