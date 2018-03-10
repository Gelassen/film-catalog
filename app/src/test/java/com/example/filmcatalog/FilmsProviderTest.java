package com.example.filmcatalog;


import com.example.filmcatalog.films.model.Films;
import com.example.filmcatalog.films.providers.FilmsProvider;
import com.example.filmcatalog.utils.MockWebServerRule;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.robolectric.RuntimeEnvironment;

import io.reactivex.Observable;
import io.reactivex.internal.util.VolatileSizeArrayList;
import io.reactivex.observers.TestObserver;

public class FilmsProviderTest extends BaseTest {

    @Rule
    public MockWebServerRule mockWebServerRule = new MockWebServerRule();

    private FilmsProvider provider;

    private String apiKey = "6ccd72a2a8fc239b13f209408fc31c33";

    @Before
    public void setUp() throws Exception {
        provider = new FilmsProvider((IApplication) RuntimeEnvironment.application);
    }

    @Test
    public void getFilms_networkIsOn_returnFilms() throws Exception {
        TestObserver testObserver = new TestObserver();

        Observable<Films> films = provider.getFilms(apiKey, "ru-RU", String.valueOf(1));
        films.subscribe(testObserver);

        testObserver.assertNoErrors();
        testObserver.assertComplete();

        Films filmsResponse = (Films) ((VolatileSizeArrayList) testObserver.getEvents().get(0)).get(0);

        Assertions.assertThat(filmsResponse.getResults().size()).isEqualTo(20);
        Assertions.assertThat(filmsResponse.getResults().get(0).getOriginalTitle()).isEqualTo("The Maze Runner");
    }

    @Test
    public void getFilmsBySearch_networkIsOn_returnFilms() throws Exception {
        TestObserver testObserver = new TestObserver();

        Observable<Films> films = provider.getFilmsWithFilter(apiKey, "ru-RU", String.valueOf(1), "The Blade Runner");
        films.subscribe(testObserver);

        testObserver.assertNoErrors();
        testObserver.assertComplete();

        Films filmsResponse = (Films) ((VolatileSizeArrayList) testObserver.getEvents().get(0)).get(0);

        Assertions.assertThat(filmsResponse.getResults().size()).isEqualTo(11);
        Assertions.assertThat(filmsResponse.getResults().get(0).getOriginalTitle()).isEqualTo("Blade Runner");
    }
}
