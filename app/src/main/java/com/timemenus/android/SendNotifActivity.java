package com.timemenus.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.gson.JsonObject;
import com.timemenus.android.services.AuthService;
import com.timemenus.android.services.FCMHelper;

import org.json.JSONException;

public class SendNotifActivity extends AppCompatActivity {

    private EditText notif;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notif);

        notif = (EditText) findViewById(R.id.create_notification);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), AdminActivity.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

    public void sendNotif(View view) throws JSONException {
        JsonObject obj = new JsonObject();

        //to many
        //{"registration_ids":["d1wA9q8qqmU:APA91bFM62c_aZhO6e8W5Yct2GftRfGnko2YENU00RefMnHxXWN507A-MDm-Cigi2wKQdVWwlLc2gdNfnGyyFxCWK3ED4V2f2plF-9gun8YbPLolV-5XTAsyHzP2NU3xSOo5ncZhTWOZ",
        // "ci4M9FKWN54:APA91bHrzEKX0lxXrOZSeA6cnTsN1AqC7Cb-UjDln7OixX7EQJE1zczK5XzDVzYyALsnmAekuTvaO29K3vFqfScWmrfLSbVJk1vUfB04H6lMU7wa4ye64rO5Pb6eGmXv6WcTCwPujly5"],"notification":{"body":"Menu Ready"},"priority":10}
        obj.addProperty("to","d1wA9q8qqmU:APA91bFM62c_aZhO6e8W5Yct2GftRfGnko2YENU00RefMnHxXWN507A-MDm-Cigi2wKQdVWwlLc2gdNfnGyyFxCWK3ED4V2f2plF-9gun8YbPLolV-5XTAsyHzP2NU3xSOo5ncZhTWOZ");

        JsonObject objBody = new JsonObject();
        objBody.addProperty("body","Menu Ready");
        obj.add("notification", objBody);
        obj.addProperty("priority", 10);

        FCMHelper.sendNotification(obj);
    }

    public void signOutButton(View view) {
        AuthService.signOut();
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);

    }
}
