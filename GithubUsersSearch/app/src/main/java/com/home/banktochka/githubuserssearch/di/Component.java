package com.home.banktochka.githubuserssearch.di;


import javax.inject.Singleton;

@Singleton
@dagger.Component (
        modules = { NetworkModule.class, UsersModule.class }
)
public interface Component extends IComponent {
}
