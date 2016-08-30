package com.express.apps.expresscafe;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.TextView;

import com.express.apps.expresscafe.services.AuthService;
import com.express.apps.expresscafe.services.DataService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class DashboardActivity extends AppCompatActivity {

    Button button;
    AuthService authService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        authService = AuthService.newInstance();


        String[] perms = {"android.permission.READ_EXTERNAL_STORAGE","android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA"};

        int permsRequestCode = 200;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(perms, permsRequestCode);
        }


        setContentView(R.layout.activity_main);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.welness_web);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        DataService.newInstance();

        System.out.println("Loaded .."+DataService.isLoaded());

        com.express.apps.expresscafe.models.Menu todayMenu = DataService.getTodayMenu();
        TextView wellnessDesc=(TextView) findViewById(R.id.wellness_description);

        DataService.loadWellnessNote(wellnessDesc);

//        wellnessDesc.setText(DataService.getTodayMenuNote());

//        while(todayMenu == null){
//            imageView.setVisibility(View.INVISIBLE);
//
//            Button todayMenuButton=(Button)findViewById(R.id.button);
//            todayMenuButton.setVisibility(View.INVISIBLE);
//
//            wellnessDesc.setVisibility(View.INVISIBLE);
//
//            TextView note=(TextView) findViewById(R.id.note);
//            note.setVisibility(View.VISIBLE);
//
//        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow, menu);

        FirebaseAuth mAuth = AuthService.getFirebaseAuth();
        FirebaseUser user = mAuth.getCurrentUser();



        for(int i=0;i<menu.size();i++){
            MenuItem item=menu.getItem(i);

            item.setVisible(false);

            if((item.getTitle().equals("Admin")|| item.getTitle().equals("Logout")) && user!=null) {
                item.setVisible(true);
            }


            if(item.getTitle().equals("Login") && user==null) {
                item.setVisible(true);
            }

        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        //Login

        if(item.getTitle().equals("Login")){
            Intent intent = new Intent(this,LoginActivity.class);

            startActivity(intent);
        }

        if(item.getTitle().equals("Admin")){
            Intent intent = new Intent(this,AdminActivity.class);

            startActivity(intent);
        }


        if(item.getTitle().equals("Logout")){
            AuthService.signOut();

            Intent intent = new Intent(this,DashboardActivity.class);
            startActivity(intent);
        }




        return super.onOptionsItemSelected(item);
    }

    public void openTodayMenu(View view) {
        Intent intent = new Intent(this, MenuActivity.class);

        startActivity(intent);
    }
}
