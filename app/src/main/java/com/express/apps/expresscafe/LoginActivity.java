package com.express.apps.expresscafe;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.express.apps.expresscafe.services.AuthService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.AuthResult;

public class LoginActivity extends BaseActivity {



    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "AdminLogin";
    TextView msgArea;
    Button signOut;
    Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = AuthService.getFirebaseAuth();

        msgArea = (TextView) findViewById(R.id.msg);
        signOut = (Button) findViewById(R.id.sign_out_button);
        signIn = (Button) findViewById(R.id.email_sign_in_button);


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                Log.i(TAG, "Status Changed");

                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    msgArea.setText("Welcome " + user.getEmail());
                    Intent i = new Intent(LoginActivity.this, AdminActivity.class);
                    startActivity(i);

                    signOut.setVisibility(View.VISIBLE);
                    signIn.setVisibility(View.GONE);


                } else {

                    msgArea.setText("Admin Login");
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    signOut.setVisibility(View.GONE);
                    signIn.setVisibility(View.VISIBLE);
                }

                hideProgressDialog();
            }
        };


    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


    public void signInButton(View view) {

        EditText email = (EditText) findViewById(R.id.email);
        EditText password = (EditText) findViewById(R.id.password);

        Log.i(TAG, email.getText().toString() + " " + password.getText().toString());

        signIn(email.getText().toString(), password.getText().toString());

    }

    public void signOutButton(View view) {
        signOut();


        Intent intent = new Intent(this, DashboardActivity.class);

        startActivity(intent);

    }

    private void signIn(final String email, String password) {

        if (email.isEmpty() || password.isEmpty()) {

            msgArea.setText("Email and Password must be entered");

            return;
        }

        showProgressDialog();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.


                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                            msgArea.setText(task.getException().getMessage());




                        }else {
                            Intent i = new Intent(LoginActivity.this, AdminActivity.class);
                            startActivity(i);
                        }

                        hideProgressDialog();

                    }
                });

    }

    private void signOut() {
        showProgressDialog();
        AuthService.signOut();
    }


}
