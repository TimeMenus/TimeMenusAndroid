package com.express.apps.expresscafe.models;

/**
 * Created by fabdin on 8/2/2016.
 */
public class ItemWithKey {
    String key;
    Item item;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
