/**
 * Author: Yuly Nurhidayati
 * URL: elaundry.pe.hu
 */
package com.yuly.elaundry.kurir.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;


import com.yuly.elaundry.kurir.R;

import com.yuly.elaundry.kurir.controller.fragment.LoginFragmentVolley;
import com.yuly.elaundry.kurir.model.database.KurirDbHandler;
import com.yuly.elaundry.kurir.model.helper.SessionManager;

public class LoginActivityNew extends AppCompatActivity {
    private static final String TAG = LoginActivityNew.class.getSimpleName();

    private SessionManager session;
    private KurirDbHandler db;

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
                .replace(R.id.fragment_frame, login).commit();
    }

    private void checkSesi(){

        // SQLite database handler
        db = new KurirDbHandler(this);

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
