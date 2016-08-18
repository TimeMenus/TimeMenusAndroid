package com.express.apps.expresscafe;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.express.apps.expresscafe.models.Menu;
import com.express.apps.expresscafe.services.AuthService;
import com.express.apps.expresscafe.services.DataService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminActivity extends BaseActivity {

    private EditText note;
    private String todayNote;
    DataService dataService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar_admin);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        dataService = DataService.newInstance();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        showProgressDialog();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        note = (EditText) findViewById(R.id.edit_note);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("menues/" + dataService.getTodayMenu().getKey());
//        System.out.println("MenuService.keyforTodayDate(todayDate): " +MenuService.keyforTodayDate(todayDate));
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                todayNote = dataSnapshot.getValue(Menu.class).getNote();
                note.setText(todayNote);
                hideProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });

    }

    public void saveNote(View view) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("menues/" + dataService.getTodayMenu().getKey() + "/note");
        myRef.setValue(note.getText().toString());
    }

    public void addItems(View view) {
        Intent intent = new Intent(this, AddItemActivity.class);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), DashboardActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

    public void signOutButton(View view) {
//        signOut();
        AuthService.signOut();

        Intent intent = new Intent(this, DashboardActivity.class);

        startActivity(intent);

    }

}
