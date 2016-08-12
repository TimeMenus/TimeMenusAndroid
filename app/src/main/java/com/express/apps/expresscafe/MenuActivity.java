package com.express.apps.expresscafe;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ExpandableListView;

import com.express.apps.expresscafe.models.Category;
import com.express.apps.expresscafe.models.Item;
import com.express.apps.expresscafe.models.Menu;
import com.express.apps.expresscafe.services.DataService;
import com.express.apps.expresscafe.services.UtilsService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MenuActivity extends ExpandableListActivity {

    private ArrayList<String> parentItems = new ArrayList<String>();
    private ArrayList<Object> childItems = new ArrayList<Object>();

    String TAG="Express Db";
    FirebaseDatabase database = null;
    DataService dataService = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ExpandableListView expandableList = getExpandableListView();
        expandableList.setDividerHeight(2);
        expandableList.setGroupIndicator(null);
        expandableList.setClickable(true);


        dataService = DataService.newInstance();


        setGroupParents();
//        setChildData();

        MyExpandableAdapter adapter = new MyExpandableAdapter(parentItems, childItems);

        adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
        expandableList.setAdapter(adapter);
        expandableList.setOnChildClickListener(this);


    }

    public void setGroupParents() {


        List<Category> cats=dataService.getCategories(null);

        for(Category c: cats){
            parentItems.add(c.getName());
        }


    }

    public void setChildData() {

        DatabaseReference myRef = database.getReference("menues");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterator it= dataSnapshot.getChildren().iterator();

                while(it.hasNext()){

                    DataSnapshot menu=(DataSnapshot) it.next();

                    String date=menu.getValue(Menu.class).getDate();



                    if(date.equals(UtilsService.getTodayDate())) {

                        Log.d(TAG, "Menu: " + menu.getValue(Menu.class).getDate());

                        HashMap<String,Item> items =  menu.getValue(Menu.class).getItems();

                        if(items !=null && items.size()>0) {

                            Iterator itemsIt = items.entrySet().iterator();
                            ArrayList<String> child = new ArrayList<String>();

                            while (itemsIt.hasNext()) {

                                Map.Entry pair = (Map.Entry) itemsIt.next();

                                Item itemVal = (Item) pair.getValue();
                                itemVal.setKey((String)pair.getKey());

                                child.add(itemVal.getName());


                            }

                            childItems.add(child);
                        }
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {

                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

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
