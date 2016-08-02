package com.express.apps.expresscafe.models;

import java.util.HashMap;
import java.util.List;

/**
 * Created by fabdin on 8/2/2016.
 */
public class Menu {
    String note;
    String date;
//    List<ItemWithKey> items;

    HashMap<String,Object> items;



//    Object items;


    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

//    public List<ItemWithKey> getItems() {
//        return items;
//    }
//
//    public void setItems(List<ItemWithKey> items) {
//        this.items = items;
//    }


    public HashMap<String, Object> getItems() {
        return items;
    }

    public void setItems(HashMap<String, Object> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return this.date+ " : "+getNote();
    }
}
