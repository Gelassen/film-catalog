package com.example.filmcatalog.utils;


import com.example.filmcatalog.Config;
import org.junit.rules.ExternalResource;

import java.io.IOException;

import okhttp3.mockwebserver.MockWebServer;

public class MockWebServerRule extends ExternalResource {

    private MockWebServer mockWebServer;

    @Override
    protected void before() throws Throwable {
        mockWebServer = new MockWebServer();
        mockWebServer.setDispatcher(new MockDispatcher());
        mockWebServer.start(Config.PORT);
    }

    @Override
    protected void after() {
        try {
            mockWebServer.shutdown();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
