package com.timemenus.android;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.timemenus.android.services.LocalService;
import com.timemenus.android.services.LocalService.LocalBinder;

public class TestActivity extends Activity {


    LocalService mService;
    boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        System.out.println("Creating TestActivity");

        // Bind to LocalService
        Intent intent = new Intent(this, LocalService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

    }

    public void tryBoundService(View view) {
        System.out.println("Try Bound Service");

        System.out.println(mBound);

        int num = mService.getRandomNumber();
        Toast.makeText(this, "number: " + num, Toast.LENGTH_SHORT).show();

    }



    @Override
    public void onStop() {
        System.out.println("Test Activity Stop");
        super.onStop();

        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {

            System.out.println("onServiceConnected");
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            LocalBinder binder = (LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            System.out.println("onServiceDisconnected");

            mBound = false;
        }
    };
}
