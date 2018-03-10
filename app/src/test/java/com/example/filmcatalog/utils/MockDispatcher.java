package com.example.filmcatalog.utils;


import com.example.filmcatalog.utils.responses.FilmsResponse;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

public class MockDispatcher extends Dispatcher {

    private static final String MOVIES = "GET /3/discover/movie?api_key=";
    private static final String MOVIES_FILTER = "GET /3/search/movie?api_key=";

    @Override
    public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
        MockResponse response = null;
        String requestLine = request.getRequestLine();

        if (isMatch(MOVIES, requestLine)) {
            response = getFilmsResponse(FilmsResponse.ResponseType.MOVIES);
        } else if (isMatch(MOVIES_FILTER, requestLine)) {
            response = getFilmsResponse(FilmsResponse.ResponseType.MOVIES_FILTER);
        } else {
            response = new MockResponse().setResponseCode(404);
        }

        return response;
    }

    public MockResponse getFilmsResponse(FilmsResponse.ResponseType responseType) {
        String body = new FilmsResponse().getResponse(responseType);
        return new MockResponse()
                .setResponseCode(200)
                .setBody(body);
    }

    private boolean isMatch(String pattern, String request) {
        return request.contains(pattern);
    }
}
