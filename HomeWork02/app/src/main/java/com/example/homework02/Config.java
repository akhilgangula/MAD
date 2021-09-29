package com.example.homework02;

import java.io.Serializable;

public class Config implements Serializable {
    private String filterBy, sortBy;
    private boolean order;

    public Config() {
    }

    public Config(String filterBy, String sortBy, boolean order) {
        this.filterBy = filterBy;
        this.sortBy = sortBy;
        this.order = order;
    }

    public String getFilterBy() {
        return filterBy;
    }

    public Config setFilterBy(String filterBy) {
        this.filterBy = filterBy;
        return this;
    }

    public String getSortBy() {
        return sortBy;
    }

    public Config setSortBy(String sortBy) {
        this.sortBy = sortBy;
        return this;
    }

    public boolean isOrder() {
        return order;
    }

    public Config setOrder(boolean order) {
        this.order = order;
        return this;
    }
}
