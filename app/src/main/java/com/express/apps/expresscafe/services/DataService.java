package com.express.apps.expresscafe.services;

import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.TextView;

import com.express.apps.expresscafe.R;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Created by fabdin on 8/3/2016.
 */
public class DataService {

    private static FirebaseDatabase database;
    private static List<Category> categories = new ArrayList<>();
    private static List<Item> items = new ArrayList<>();
    private static Menu todayMenu = null;
    private static String todayMenuNote = null;

    public static void newInstance() {

        database = FirebaseDatabase.getInstance();

        setCategoriesListener();
        setMenuListener();

//        Log.d("Item",DataService.getTodayMenu().getDate());
//        setItemsListener();

    }

    //Listeners
    private static void setMenuListener() {

        System.out.println("setMenuListener");
        DatabaseReference myRef = database.getReference("menues");
        myRef.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterator it = dataSnapshot.getChildren().iterator();
                while (it.hasNext()) {
                    DataSnapshot menu = (DataSnapshot) it.next();

                    Menu menuObject=(menu.getValue(Menu.class));
                    menuObject.setKey(menu.getKey());

                    if (menuObject!=null && menuObject.getDate().equals(UtilsService.getTodayDate())) {
                        Log.d("Item",menuObject.getKey()+" "+menuObject.getDate());
                        todayMenu = menuObject;
                        todayMenuNote = todayMenu.getNote();
                        setItemsListener();
                        break;
                    } else{
                        todayMenuNote = "Menu is not here yet.";
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }

    public static boolean isLoaded(){

        if(todayMenu !=null || todayMenuNote != null){
            return true;
        }
        return false;
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

    public static void setItemsListener(){

        if(todayMenu!=null){

            DatabaseReference itemsRef = database.getReference("menues/"+todayMenu.getKey());
            itemsRef.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    DataSnapshot menu=(DataSnapshot) dataSnapshot;

                    Menu todayMenu=menu.getValue(Menu.class);

                    if(todayMenu!=null && todayMenu.getItems() !=null) {
                        items=new ArrayList<Item>();

                        Iterator itemsItertr = todayMenu.getItems().entrySet().iterator();

                        while (itemsItertr.hasNext()) {
                            Map.Entry pair = (Map.Entry) itemsItertr.next();

                            Item item = (Item) pair.getValue();
                            item.setKey((String) pair.getKey());
                            items.add(item);
                        }

                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }

    public static Menu getTodayMenu(){
        return todayMenu;
    }

    public static String getTodayMenuNote(){
        return todayMenuNote;
    }

    //Items
    public List<Item> getItemsForCategory(String categoryId){



        return Collections.emptyList();

    }

    public static List<Item> getItems(){
        return items;
    }

    public Item getDashboardItemForCategory(String categoryId){
        return null;
    }

    //Categories
    public static Category getCategoryById(String key){

        for(Category c: categories){
            if(c.getKey().equals(key)){
                return c;
            }
        }


        return  new Category();

    }

    public static List<Category> getCategories(String key){

        return categories;
    }

    public static String getCategoryByName(String name) {

        for (Category c: categories){

            if(name.equals(c.getName())){
                return c.getKey();
            }
        }

        return null;
    }

    public static void loadWellnessNote(TextView wellnessDesc) {
        wellnessDesc.setText(todayMenuNote);
    }
}
