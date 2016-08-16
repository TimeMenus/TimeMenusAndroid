package com.express.apps.expresscafe.services;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by fabdin on 8/15/2016.
 */
public class AuthService
{
    private static FirebaseAuth mAuth;

    public static AuthService newInstance() {

        AuthService service = new AuthService();

        mAuth = FirebaseAuth.getInstance();

        return service;
    }


    public static void signOut(){
        mAuth.signOut();
    }

    public static FirebaseAuth getFirebaseAuth(){

        return mAuth;
    }
}
