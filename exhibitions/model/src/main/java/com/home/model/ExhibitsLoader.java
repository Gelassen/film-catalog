package com.home.model;


import java.util.List;

import io.reactivex.Observable;

public interface ExhibitsLoader {
    Observable<List<Exhibit>> getExhibitList();
}
