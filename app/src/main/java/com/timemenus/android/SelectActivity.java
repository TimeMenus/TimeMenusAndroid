package com.timemenus.android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class SelectActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static final String PREFS_NAME = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);
        String myCafe = prefs.getString("mycafe_sel", null);

        if(myCafe != null){
            System.out.println("Taking to cafe: " + myCafe);
            Intent intent = new Intent(this,DashboardActivity.class);
            startActivity(intent);
        } else {

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, CAFES);

            AutoCompleteTextView textView = (AutoCompleteTextView)
                    findViewById(R.id.cafes_list);
            textView.setAdapter(adapter);
            textView.setOnItemClickListener(this);
        }
    }

    private static final String[] CAFES = new String[] {
            "EXPRESS DC1"
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Log.d("autocomplete", "Selected "+parent.getItemAtPosition(position));
        System.out.println("selected : "+ parent.getItemAtPosition(position));

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("mycafe_sel", parent.getItemAtPosition(position).toString());
        editor.commit();

        Intent intent = new Intent(this,DashboardActivity.class);
        startActivity(intent);

    }
}
