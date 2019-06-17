package ru.rsprm;


import android.app.Application;
import com.squareup.okhttp.HttpUrl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import ru.rsprm.di.*;
import ru.rsprm.model.Truck;
import ru.rsprm.utils.ServerRule;

import java.util.List;

@RunWith(RobolectricTestRunner.class)
public class BaseTest {

    @Rule
    public ServerRule serverRule = new ServerRule();

    protected TestAppComponent component;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        HttpUrl url = serverRule.getUrl();

        Application app = RuntimeEnvironment.application;
        component = DaggerTestAppComponent
                .builder()
                .networkModule(new NetworkModule(app, url.toString()))
                .module(new Module(app))
                .build();
    }

}
