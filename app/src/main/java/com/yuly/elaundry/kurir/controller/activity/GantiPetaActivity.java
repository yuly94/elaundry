package com.yuly.elaundry.kurir.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yuly.elaundry.kurir.R;
import com.yuly.elaundry.kurir.model.util.SetStatusBarColor;
import com.yuly.elaundry.kurir.model.util.Variable;

public class GantiPetaActivity extends AppCompatActivity {
    private RadioGroup algoRG;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gantipeta);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        //         set status bar
        new SetStatusBarColor().setStatusBarColor(findViewById(R.id.statusBarBackgroundSettings),
                getResources().getColor(R.color.my_primary_dark), this);
        init();
    }


    /**
     * init and set
     */
    public void init() {

        downloadBtn();

    }


    /**
     * move view to download map
     */
    private void downloadBtn() {
        final ViewGroup cbtn = (ViewGroup) findViewById(R.id.activity_settings_download_map);
        cbtn.setOnTouchListener(new View.OnTouchListener() {
            @Override public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        cbtn.setBackgroundColor(getResources().getColor(R.color.my_primary_light));
                        return true;
                    case MotionEvent.ACTION_UP:
                        cbtn.setBackgroundColor(getResources().getColor(R.color.my_icons));
                        startDownloadActivity();
                        return true;
                }
                return false;
            }
        });
    }


    private void startDownloadActivity() {
        Intent intent = new Intent(this, DownloadMapActivity.class);
        startActivity(intent);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
