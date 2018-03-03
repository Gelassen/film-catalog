package com.example.filmcatalog.di;


import javax.inject.Singleton;

@Singleton
@dagger.Component(
        modules = { MockNetworkModule.class }
)
public interface MockComponent extends Component{
}
