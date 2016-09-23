package com.timemenus.app;

import android.os.Build;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.TextView;
import com.timemenus.app.services.AuthService;
import com.timemenus.app.services.DataService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;


public class DashboardActivity extends BaseActivity {

    Button button;
    AuthService authService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        showProgressDialog();
        super.onCreate(savedInstanceState);

        authService = AuthService.newInstance();

        String[] perms = {"android.permission.READ_EXTERNAL_STORAGE","android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA"};

        int permsRequestCode = 200;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(perms, permsRequestCode);
        }

        setContentView(R.layout.activity_main);

        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.welness_web);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        DataService.newInstance();

        final Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
                                  @Override
                                  public void run() {
                                      if (!DataService.isLoaded()) {
                                          System.out.println("Firebase data not loaded yet...");
                                      } else {
                                          final com.timemenus.app.models.Menu todayMenu = DataService.getTodayMenu();
                                          final TextView wellnessDesc=(TextView) findViewById(R.id.wellness_description);

                                          runOnUiThread(new Runnable() {
                                              @Override
                                              public void run() {
                                                  if(todayMenu==null){
                                                      imageView.setVisibility(View.INVISIBLE);
                                                      Button todayMenuButton = (Button) findViewById(R.id.button);
                                                      todayMenuButton.setVisibility(View.INVISIBLE);
                                                  }
                                                  DataService.loadWellnessNote(wellnessDesc);

                                              }
                                          });
                                          hideProgressDialog();
                                          t.cancel();
                                          t.purge();
                                      }
                                  }
                              },
                0,
                500);

//        wellnessDesc.setText(DataService.getTodayMenuNote());
//       DataService.loadWellnessNote(wellnessDesc);

//
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

        if(item.getTitle().equals("Admin")) {
            Intent intent = null;
            if (DataService.getTodayMenu() != null) {
                intent = new Intent(this, AdminActivity.class);
            } else {
                intent = new Intent(this, AddMenuToday.class);
            }


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
