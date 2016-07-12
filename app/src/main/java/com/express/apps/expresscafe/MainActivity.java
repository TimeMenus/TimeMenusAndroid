package com.express.apps.expresscafe;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;



public class MainActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.welness_web);

    }

    public void openTodayMenu(View view){
        Intent intent = new Intent(this, MenuActivity.class);

        startActivity(intent);
    }

    public void openAdminLoginPage(View view){
        Intent intent = new Intent(this,AdminLoginActivity.class);

        startActivity(intent);
    }



}
