package com.express.apps.expresscafe.services;

import android.util.Log;

import com.express.apps.expresscafe.models.Item;
import com.express.apps.expresscafe.models.Menu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by kkatta on 8/10/2016.
 */
public class menuesService {

    private static String key;

    public static String keyforTodayDate(final String todayDate) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("menues");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterator it = dataSnapshot.getChildren().iterator();
                while (it.hasNext()) {
                    DataSnapshot menu = (DataSnapshot) it.next();
                    String dateofItem = menu.getValue(Menu.class).getDate();
                    System.out.println("dateofItem: "+dateofItem+"todayDate: "+todayDate);
                    if (dateofItem == todayDate) {
                        key = ((DataSnapshot) it.next()).getKey();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
        return key;
    }

    public static String getTodayDate(){
        Date date = new Date();
        String mon = new SimpleDateFormat("MMM").format(date);
        String year = new SimpleDateFormat("yy").format(date);
        String day = new SimpleDateFormat("d").format(date);
        String dayWthSufx = getDayOfMonthSuffix(Integer.parseInt(day));
        String todayDate = mon + " " + dayWthSufx + " " + year;
        return todayDate;
    }

    private static String getDayOfMonthSuffix(final int n) {
        if (n >= 11 && n <= 13) {
            return n+"th";
        }
        switch (n % 10) {
            case 1:  return n+"st";
            case 2:  return n+"nd";
            case 3:  return n+"rd";
            default: return n+"th";
        }
    }

}
