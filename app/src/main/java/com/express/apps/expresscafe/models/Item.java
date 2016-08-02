package com.express.apps.expresscafe.models;

/**
 * Created by fabdin on 8/2/2016.
 */
public class Item {
    String categoryId;
//    String dashboard;
    String description;
    String name;
//    Picture picture;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

//    public String getDashboard() {
//        return dashboard;
//    }
//
//    public void setDashboard(String dashboard) {
//        this.dashboard = dashboard;
//    }

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

//    public Picture getPicture() {
//        return picture;
//    }
//
//    public void setPicture(Picture picture) {
//        this.picture = picture;
//    }
}
