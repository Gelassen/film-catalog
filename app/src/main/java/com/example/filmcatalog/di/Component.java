package com.example.filmcatalog.di;

import javax.inject.Singleton;

@Singleton
@dagger.Component (
        modules = { NetworkModule.class}
)
public interface Component extends IComponent {
}
