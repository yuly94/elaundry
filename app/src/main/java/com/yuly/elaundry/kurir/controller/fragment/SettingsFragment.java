package com.yuly.elaundry.kurir.controller.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yuly.elaundry.kurir.R;
import com.yuly.elaundry.kurir.controller.activity.DownloadMapActivity;
import com.yuly.elaundry.kurir.model.util.SetStatusBarColor;
import com.yuly.elaundry.kurir.model.util.Variable;

public class SettingsFragment extends Fragment {
    private RadioGroup algoRG;
   // private ViewGroup cbtn;
    private RadioGroup rg;
    private RadioButton rbf, rbs, dijkstrabi, dijkstraOneToMany, astarbi, astar;
    private CheckBox cb_advanced, cb_direction;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_settings, container,
                false);

/*

        final ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //         set status bar
        new SetStatusBarColor().setStatusBarColor(v.findViewById(R.id.statusBarBackgroundSettings),
                getResources().getColor(R.color.my_primary_dark), getActivity());

*/





       // cbtn = (ViewGroup) v.findViewById(R.id.activity_settings_download_map);
        rg = (RadioGroup) v.findViewById(R.id.activity_settings_weighting_rbtngroup);
        rbs = (RadioButton) v.findViewById(R.id.activity_settings_shortest_rbtn);

        algoRG = (RadioGroup) v.findViewById(R.id.activity_settings_routing_alg_rbtngroup);
        cb_direction = (CheckBox) v.findViewById(R.id.activity_settings_directions_cb);
        cb_advanced = (CheckBox) v.findViewById(R.id.activity_settings_advanced_cb);

        dijkstrabi = (RadioButton) v.findViewById(R.id.activity_settings_algorithm_bidijksjtra_rbtn);
        dijkstraOneToMany = (RadioButton) v.findViewById(R.id.activity_settings_algorithm_ontomdijksjtra_rbtn);
        astarbi = (RadioButton) v.findViewById(R.id.activity_settings_algorithm_biastar_rbtn);
        astar = (RadioButton) v.findViewById(R.id.activity_settings_algorithm_uniastar_rbtn);

        init(v);

        return v;

    }

    /**
     * init and set
     */
    public void init(View v) {

     //    downloadBtn(v);
      //  alternateRoute();
        advancedSetting();
        directions();
    }

    /**
     * init and implement directions checkbox
     */
    private void directions() {


        cb_direction.setChecked(Variable.getVariable().isDirectionsON());
        cb_direction.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Variable.getVariable().setDirectionsON(isChecked);
            }
        });
    }

    /**
     * set checkbox to enable or disable advanced settings
     * <p/>
     * init radio buttons
     */
    private void advancedSetting() {

        cb_advanced.setChecked(Variable.getVariable().isAdvancedSetting());
        cb_advanced.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Variable.getVariable().setAdvancedSetting(isChecked);
                for (int i = 0; i < algoRG.getChildCount(); i++) {
                    (algoRG.getChildAt(i)).setEnabled(isChecked);
                }
            }
        });
        //init set enable for radio buttons
        for (int i = 0; i < algoRG.getChildCount(); i++) {
            (algoRG.getChildAt(i)).setEnabled(Variable.getVariable().isAdvancedSetting());
        }
        algoRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.activity_settings_algorithm_bidijksjtra_rbtn:
                        Variable.getVariable().setRoutingAlgorithms("dijkstrabi");
                        break;
                    case R.id.activity_settings_algorithm_ontomdijksjtra_rbtn:
                        Variable.getVariable().setRoutingAlgorithms("dijkstraOneToMany");
                        break;
                    case R.id.activity_settings_algorithm_biastar_rbtn:
                        Variable.getVariable().setRoutingAlgorithms("astarbi");
                        break;
                    case R.id.activity_settings_algorithm_uniastar_rbtn:
                        Variable.getVariable().setRoutingAlgorithms("astar");
                        break;
                }
            }
        });
        //        init radio buttons:
        switch (Variable.getVariable().getRoutingAlgorithms()) {
            case "dijkstrabi":
                dijkstrabi.setChecked(true);
                break;
            case "dijkstraOneToMany":
                dijkstraOneToMany.setChecked(true);
                break;
            case "astarbi":
                astarbi.setChecked(true);
                break;
            case "astar":
                astar.setChecked(true);
                break;
        }
    }


    /**
     * init and set alternate route radio button option
     */
    private void alternateRoute() {

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.activity_settings_fastest_rbtn:
                        Variable.getVariable().setWeighting("fastest");
                        break;
                    case R.id.activity_settings_shortest_rbtn:
                        Variable.getVariable().setWeighting("shortest");
                        break;
                }
            }
        });

        if (Variable.getVariable().getWeighting().equalsIgnoreCase("fastest")) {
            rbf.setChecked(true);
        } else {
            rbs.setChecked(true);
        }
    }


    /**
     * move view to download map
     */
    private void downloadBtn(View v) {

       final ViewGroup  cbtn = (ViewGroup) v.findViewById(R.id.activity_settings_download_map);
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
        Intent intent = new Intent(getActivity(), DownloadMapActivity.class);
        startActivity(intent);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                getActivity().finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
