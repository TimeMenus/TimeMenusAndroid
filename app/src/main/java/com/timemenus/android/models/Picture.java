package com.timemenus.android.models;

/**
 * Created by fabdin on 8/2/2016.
 */
public class Picture {
    String name;
    String url;

    public Picture() {

    }

    public Picture(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
