package com.home.model;


import java.util.ArrayList;
import java.util.List;

public class ExhibitList {
    private List<Exhibit> list;

    public ExhibitList() {
        this.list = new ArrayList<>();
    }

    public List<Exhibit> getList() {
        return list;
    }

    public void setList(List<Exhibit> list) {
        this.list = list;
    }
}
