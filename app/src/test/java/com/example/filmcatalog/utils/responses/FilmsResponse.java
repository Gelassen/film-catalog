package com.example.filmcatalog.utils.responses;


import com.example.filmcatalog.utils.ResourceUtils;

public class FilmsResponse {

    public String getDefault() {
        return getResponse(ResponseType.MOVIES);
    }

    public String getResponse(ResponseType responseType) {
        ResourceUtils utils = new ResourceUtils();
        return utils.readString(responseType.getPath());
    }

    public enum ResponseType {
        MOVIES("movies/movies"),
        MOVIES_FILTER("movies/movies_filter_blade_runner");

        private String path;

        ResponseType(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }

    }
}
