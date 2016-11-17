package com.timemenus.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class SelectActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, CAFES);

        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.cafes_list);
        textView.setAdapter(adapter);
        textView.setOnItemClickListener(this);

    }

    private static final String[] CAFES = new String[] {
            "EXPRESS DC1"
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Log.d("autocomplete", "Selected "+parent.getItemAtPosition(position));
        Intent intent = new Intent(this,DashboardActivity.class);
        startActivity(intent);

    }
}
