package com.home.banktochka.githubuserssearch.users.model;


public class UsersModel {

    private int page;
    private String search;

    public UsersModel() {
        this.page = 1;
        this.search = "";
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public void reset() {
        page = 1;
        search = "";
    }

    public String getSearch() {
        return search;
    }

    public int getPage() {
        return page;
    }

    public void onNextPage() {
        page++;
    }

    public void onError() {
        page--;
    }

}
