package com.express.apps.expresscafe.models;

/**
 * Created by fabdin on 8/2/2016.
 */
public class Item {

    String key;
    String categoryId;
    Boolean dashboard;
    String description;
    String name;
    Picture picture;

    public Item() {
    }

    public Item(String key, Boolean dashboard, String description, String name, Picture pic) {
        this.key=key;
        this.dashboard=dashboard;
        this.description=description;
        this.name=name;
        this.picture=pic;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Boolean getDashboard() {
        return dashboard;
    }

    public void setDashboard(Boolean dashboard) {
        this.dashboard = dashboard;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }
}
