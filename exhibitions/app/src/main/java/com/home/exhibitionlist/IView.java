package com.home.exhibitionlist;


import com.home.model.Exhibit;

import java.util.List;

public interface IView {
    void onModel(List<Exhibit> data);
}
