package com.express.apps.expresscafe;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;

import com.express.apps.expresscafe.services.AuthService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class DashboardActivity extends AppCompatActivity {

    Button button;
    AuthService authService;
    static final String[] FRUITS = new String[] { "Apple", "Avocado", "Banana",
            "Blueberry", "Coconut", "Durian", "Guava", "Kiwifruit",
            "Jackfruit", "Mango", "Olive", "Pear", "Sugar-apple" };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        authService = AuthService.newInstance();

        setContentView(R.layout.activity_main);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.welness_web);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

//        setListAdapter(new ArrayAdapter<String>(this, R.layout.dashboard_item,FRUITS));
//        ListView listView = getListView();
//        listView.setTextFilterEnabled(true);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                // When clicked, show a toast with the TextView text
//                Toast.makeText(getApplicationContext(),
//                        ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
//            }
//        });

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
            Intent intent = new Intent(this,LoginActivity.class);

            startActivity(intent);
        }


        if(item.getTitle().equals("Logout")){
            AuthService.signOut();

            Intent intent = new Intent(this,DashboardActivity.class);
            startActivity(intent);
        }




        return super.onOptionsItemSelected(item);
    }

    public void openTodayMenu(View view){
        Intent intent = new Intent(this, MenuActivity.class);

        startActivity(intent);
    }

    public void openAdminLoginPage(View view){

    }



}
