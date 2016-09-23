package com.timemenus.app.services;

import android.util.Log;

import com.timemenus.app.models.Menu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

/**
 * Created by kkatta on 8/10/2016.
 */
public class MenuService {

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


}
