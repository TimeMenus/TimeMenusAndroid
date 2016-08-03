package com.express.apps.expresscafe;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ExpandableListView;

import com.express.apps.expresscafe.models.Item;
import com.express.apps.expresscafe.models.Menu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MenuActivity extends ExpandableListActivity {

    private ArrayList<String> parentItems = new ArrayList<String>();
    private ArrayList<Object> childItems = new ArrayList<Object>();

    String TAG="Express Db";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//       ExpandableListView expandableList = (ExpandableListView) findViewById(R.id.menuListView);
        ExpandableListView expandableList = getExpandableListView();
        expandableList.setDividerHeight(2);
        expandableList.setGroupIndicator(null);
        expandableList.setClickable(true);

        setGroupParents();
        setChildData();

        MyExpandableAdapter adapter = new MyExpandableAdapter(parentItems, childItems);

        adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
        expandableList.setAdapter(adapter);
        expandableList.setOnChildClickListener(this);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("menues");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterator it= dataSnapshot.getChildren().iterator();
                while(it.hasNext()){

                    DataSnapshot menu=(DataSnapshot) it.next();

//                    Menu m=menu.getValue();

//                    Log.d(TAG, "Menu: " + m.toString());
                    Log.d(TAG, "Menu: " + menu.getValue(Menu.class).getItems());

                    Iterator itemsItertr = menu.getValue(Menu.class).getItems().entrySet().iterator();
                    while (itemsItertr.hasNext()){
                        Map.Entry pair = (Map.Entry)itemsItertr.next();

                            Item itemVal = (Item)pair.getValue();

                            System.out.println(pair.getKey() + " : CategoryID: " + itemVal.getCategoryId() + " Name: " + itemVal.getName());

                    }

//                    Log.d(TAG, "Menu: " + menu.getKey());
                }
//                Iterator it= map.entrySet().iterator();
////
//                while(it.hasNext()){
//                    Map.Entry pair = (Map.Entry) it.next();
//
//                    Log.d(TAG, "Key is: " + pair.getKey());
//                    Log.d(TAG, "Value is: " + pair.getValue());
//                }


            }

            @Override
            public void onCancelled(DatabaseError error) {

                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });


    }

    public void setGroupParents() {
        parentItems.add("Android");
        parentItems.add("Core Java");
        parentItems.add("Desktop Java");
        parentItems.add("Enterprise Java");
    }

    public void setChildData() {
        ArrayList<String> child = new ArrayList<String>();
        child.add("Core");
        child.add("Games");
        childItems.add(child);
        child = new ArrayList<String>();
        child.add("Apache");
        child.add("Applet");
        child.add("AspectJ");
        child.add("Beans");
        child.add("Crypto");
        childItems.add(child);
        child = new ArrayList<String>();
        child.add("Accessibility");
        child.add("AWT");
        child.add("ImageIO");
        child.add("Print");
        childItems.add(child);
        child = new ArrayList<String>();
        child.add("EJB3");
        child.add("GWT");
        child.add("Hibernate");
        child.add("JSP");
        childItems.add(child);
    }

}
