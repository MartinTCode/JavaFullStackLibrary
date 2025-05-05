package com.javafullstacklibrary.utils;

public class DataSingleton {
    private static DataSingleton instance;
    private String searchQuery;

    private DataSingleton() {
        // Private constructor to prevent instantiation
    }

    public static synchronized DataSingleton getInstance() {
        if (instance == null) {
            instance = new DataSingleton();
        }
        return instance;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }
}
