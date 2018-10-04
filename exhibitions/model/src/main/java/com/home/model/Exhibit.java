package com.home.model;


import java.util.ArrayList;
import java.util.List;

public class Exhibit {
    private String title;
    private List<String> images;

    public Exhibit() {
        images = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

}
