package com.express.apps.expresscafe.services;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import com.express.apps.expresscafe.models.Category;
import com.express.apps.expresscafe.models.Item;
import com.express.apps.expresscafe.models.Menu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by fabdin on 8/3/2016.
 */
public class DataService {

    private static FirebaseDatabase database;
    private static List<Category> categories =new ArrayList<>();
    private static Menu todayMenu = null;

    public static DataService newInstance() {

        database = FirebaseDatabase.getInstance();
        DataService ds = new DataService();

        setCategoriesListener();
        setMenuListener();

        return ds;
    }


    //Listeners

    private static void setMenuListener() {


        DatabaseReference myRef = database.getReference("menues");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator it = dataSnapshot.getChildren().iterator();
                while (it.hasNext()) {
                    DataSnapshot menu = (DataSnapshot) it.next();

                    Menu menuObject=(menu.getValue(Menu.class));

                    if (menuObject.getDate() == UtilsService.getTodayDate()) {
                        todayMenu = menuObject;
                        todayMenu.setKey(((DataSnapshot) it.next()).getKey());

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

    }

    private static void setCategoriesListener() {

        DatabaseReference categoriesRef = database.getReference("categories");
        categoriesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterator it=dataSnapshot.getChildren().iterator();
                categories = new ArrayList<Category>();
                while(it.hasNext()){
                    DataSnapshot ds=(DataSnapshot) it.next();
                    Category c=new Category();
                    c.setKey(ds.getKey());
                    c.setName((String)ds.getValue());

                    categories.add(c);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public static Menu getTodayMenu(){

        return todayMenu;
    }


    //Items

    public List<Item> getItemsForCategory(String categoryId){

        return Collections.emptyList();

    }

    public Item getDashboardItemForCategory(String categoryId){
        return null;
    }

    //Categories
    public Category getCategoryById(String key){


        return  new Category();

    }


    public static List<Category> getCategories(String key){

        return categories;
    }


    public String getCategoryByName(String name) {

        for (Category c: categories){

            if(name.equals(c.getName())){
                return c.getKey();
            }
        }

        return null;
    }
}
