/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 */
package com.yuly.elaundry.activity;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.yuly.elaundry.R;

import com.yuly.elaundry.fragment.LoginFragmentVolley;
import com.yuly.elaundry.helper.SQLiteHandler;
import com.yuly.elaundry.helper.SessionManager;

public class LoginActivityNew extends AppCompatActivity {
    private static final String TAG = RegisterActivity.class.getSimpleName();

    private SessionManager session;
    private SQLiteHandler db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // pref = getPreferences(0);
        initFragment();

     //   checkSesi();
    }


    private void initFragment() {


        Fragment login = new LoginFragmentVolley();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, login).commit();
    }

    private void checkSesi(){

        // SQLite database handler
        db = new SQLiteHandler(this);

        // Session manager
        session = new SessionManager(this);

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            //  finish();
        }

    }



}
