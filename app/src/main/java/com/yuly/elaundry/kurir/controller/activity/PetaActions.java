package com.yuly.elaundry.kurir.controller.activity;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.yuly.elaundry.kurir.R;
import com.yuly.elaundry.kurir.model.dataType.Destination;
import com.yuly.elaundry.kurir.model.listeners.NavigatorListener;
import com.yuly.elaundry.kurir.model.listeners.PetaHandlerListener;
import com.yuly.elaundry.kurir.model.map.Navigasi;
import com.yuly.elaundry.kurir.model.map.PetaHandler;
import com.yuly.elaundry.kurir.model.util.InstructionAdapter;
import com.yuly.elaundry.kurir.model.util.Variable;

import org.mapsforge.core.model.LatLong;
import org.mapsforge.map.android.view.MapView;
import org.mapsforge.map.model.MapViewPosition;


public class PetaActions implements NavigatorListener, PetaHandlerListener {
    private Activity activity;
    protected FloatingActionButton showPositionBtn, navigationBtn, settingsBtn, controlBtn;
    protected FloatingActionButton zoomInBtn, zoomOutBtn;
    private ViewGroup sideBarVP, sideBarMenuVP, navSettingsVP, navSettingsFromVP, navSettingsToVP, navInstructionVP,
            navInstructionListVP;
    private boolean menuVisible;
    /**
     * true handle on start point ; false handle on end point
     */
    private boolean onStartPoint;
    private EditText fromLocalET, toLocalET;

    public PetaActions(AppCompatActivity activity, MapView mapView) {
        this.activity = activity;

        this.showPositionBtn = (FloatingActionButton) activity.findViewById(R.id.fab_location);

        this.controlBtn = (FloatingActionButton) activity.findViewById(R.id.fab_menu);

        this.zoomInBtn = (FloatingActionButton) activity.findViewById(R.id.fab_besarkan);
        this.zoomOutBtn = (FloatingActionButton) activity.findViewById(R.id.fab_kecilkan);


       // view groups managed by separate layout xml file : //map_sidebar_layout/map_sidebar_menu_layout
        this.sideBarVP = (ViewGroup) activity.findViewById(R.id.menu_nafigasi_peta);
        this.sideBarMenuVP = (ViewGroup) activity.findViewById(R.id.group_tombol_navigasi);
 /*           this.navSettingsVP = (ViewGroup) activity.findViewById(R.id.nav_settings_layout);
        this.navSettingsFromVP = (ViewGroup) activity.findViewById(R.id.nav_settings_from_layout);
        this.navSettingsToVP = (ViewGroup) activity.findViewById(R.id.nav_settings_to_layout);*/

        //        this.navInstructionVP = (ViewGroup) activity.findViewById(R.id.nav_instruction_layout); // TODO
/*
        this.navInstructionListVP = (ViewGroup) activity.findViewById(R.id.nav_instruction_list_layout);
        //form location and to location textView
        this.fromLocalET = (EditText) activity.findViewById(R.id.nav_settings_from_local_et);
        this.toLocalET = (EditText) activity.findViewById(R.id.nav_settings_to_local_et);

        */

        this.menuVisible = false;
        this.onStartPoint = true;

      //  MapHandler.getPetaHandler().setMapHandlerListener(this);
        //Navigasi.getNavigator().addListener(this);

        controlBtnHandler();
        zoomControlHandler(mapView);
        showMyLocation(mapView);
        //navBtnHandler();



    }



    /**
     * add end point marker to map
     *
     * @param endPoint
     */
    private void addToMarker(LatLong endPoint) {
        PetaHandler.getPetaHandler().addEndMarker(endPoint);
    }

    /**
     * add start point marker to map
     *
     * @param startPoint
     */
    private void addFromMarker(LatLong startPoint) {
        PetaHandler.getPetaHandler().addStartMarker(startPoint);
    }

    /**
     * settings layout:
     * <p>
     * from item handler: when from item is clicked
     */
    private void settingsFromItemHandler() {
        final ViewGroup fromFieldVG = (ViewGroup) activity.findViewById(R.id.map_nav_settings_from_item);
        fromFieldVG.setOnTouchListener(new View.OnTouchListener() {
            @Override public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        fromFieldVG.setBackgroundColor(activity.getResources().getColor(R.color.my_primary_light));
                        return true;
                    case MotionEvent.ACTION_UP:
                        fromFieldVG.setBackgroundColor(activity.getResources().getColor(R.color.my_primary));
                        navSettingsVP.setVisibility(View.INVISIBLE);
                        navSettingsFromVP.setVisibility(View.VISIBLE);
                        return true;
                }
                return false;
            }
        });
        //        from layout
        //clear button
        ImageButton fromLayoutClearBtn = (ImageButton) activity.findViewById(R.id.nav_settings_from_clear_btn);
        fromLayoutClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                navSettingsVP.setVisibility(View.VISIBLE);
                navSettingsFromVP.setVisibility(View.INVISIBLE);
            }
        });
        //  from layout: items
        useCurrentLocationHandler();
        //        chooseFromFavoriteHandler();TODO
        pointOnMapHandler();
    }

    /**
     * from layout : point item view group
     * <p>
     * preform actions when point on map item is clicked
     */
    private void pointOnMapHandler() {
        final ViewGroup pointItem = (ViewGroup) activity.findViewById(R.id.map_nav_settings_from_point);
        pointItem.setOnTouchListener(new View.OnTouchListener() {
            @Override public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        pointItem.setBackgroundColor(activity.getResources().getColor(R.color.my_primary_light));
                        return true;
                    case MotionEvent.ACTION_UP:
                        pointItem.setBackgroundColor(activity.getResources().getColor(R.color.my_primary));
                        onStartPoint = true;
                        navSettingsFromVP.setVisibility(View.INVISIBLE);
                        //touch on map
                        Toast.makeText(activity, "Touch on Map to choose your start Location", Toast.LENGTH_SHORT)
                                .show();
                        PetaHandler.getPetaHandler().setNeedLocation(true);
                        return true;
                }
                return false;
            }
        });
    }

    /**
     * choose from favorite list handler: preform actions when choose from favorite item is clicked
     */
    private void chooseFromFavoriteHandler() {
        //create a list view
        //read from Json file inflater to RecyclerView
        final ViewGroup chooseFavorite = (ViewGroup) activity.findViewById(R.id.map_nav_settings_from_favorite);
        chooseFavorite.setOnTouchListener(new View.OnTouchListener() {
            @Override public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        chooseFavorite.setBackgroundColor(activity.getResources().getColor(R.color.my_primary_light));
                        return true;
                    case MotionEvent.ACTION_UP:
                        chooseFavorite.setBackgroundColor(activity.getResources().getColor(R.color.my_primary));
                        //TODO
                        return true;
                }
                return false;
            }
        });
    }

    /**
     * current location handler: preform actions when current location item is clicked
     */
    private void useCurrentLocationHandler() {
        final ViewGroup useCurrentLocal = (ViewGroup) activity.findViewById(R.id.map_nav_settings_from_current);
        useCurrentLocal.setOnTouchListener(new View.OnTouchListener() {
            @Override public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        useCurrentLocal.setBackgroundColor(activity.getResources().getColor(R.color.my_primary_light));
                        return true;
                    case MotionEvent.ACTION_UP:
                        useCurrentLocal.setBackgroundColor(activity.getResources().getColor(R.color.my_primary));
                        if (MapActivity.getmCurrentLocation() != null) {
                            Destination.getDestination().setStartPoint(
                                    new LatLong(MapActivity.getmCurrentLocation().getLatitude(),
                                            MapActivity.getmCurrentLocation().getLongitude()));
                            addFromMarker(Destination.getDestination().getStartPoint());
                            fromLocalET.setText(Destination.getDestination().getStartPointToString());
                            navSettingsFromVP.setVisibility(View.INVISIBLE);
                            navSettingsVP.setVisibility(View.VISIBLE);
                            activeNavigator();
                        } else {
                            Toast.makeText(activity, "Current Location not available, Check your GPS signal!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        return true;
                }
                return false;
            }
        });
    }

    /**
     * when use press on the screen to get a location form map
     *
     * @param latLong
     */
    @Override public void onPressLocation(LatLong latLong) {
        if (onStartPoint) {
            Destination.getDestination().setStartPoint(latLong);
            addFromMarker(latLong);
            fromLocalET.setText(Destination.getDestination().getStartPointToString());
        } else {
            Destination.getDestination().setEndPoint(latLong);
            addToMarker(latLong);
            toLocalET.setText(Destination.getDestination().getEndPointToString());
        }
        navSettingsVP.setVisibility(View.VISIBLE);
        activeNavigator();
    }

    /**
     * calculate path calculating (running) true NOT running or finished false
     *
     * @param shortestPathRunning
     */
    @Override public void pathCalculating(boolean shortestPathRunning) {
        if (!shortestPathRunning && Navigasi.getNavigator().getGhResponse() != null) {
            activeDirections();
        }
    }

    /**
     * drawer polyline on map , active navigator instructions(directions) if on
     */
    private void activeNavigator() {
        LatLong startPoint = Destination.getDestination().getStartPoint();
        LatLong endPoint = Destination.getDestination().getEndPoint();
        if (startPoint != null && endPoint != null) {
            // show path finding process
            navSettingsVP.setVisibility(View.INVISIBLE);

            View pathfinding = activity.findViewById(R.id.map_nav_settings_path_finding);
            pathfinding.setVisibility(View.VISIBLE);
            pathfinding.bringToFront();
            PetaHandler petaHandler = PetaHandler.getPetaHandler();
            petaHandler.calcPath(startPoint.latitude, startPoint.longitude, endPoint.latitude, endPoint.longitude);
            if (Variable.getVariable().isDirectionsON()) {
                petaHandler.setNeedPathCal(true);
                //rest running at
            }
        }
    }

    /**
     * active directions, and directions view
     */
    private void activeDirections() {
        RecyclerView instructionsRV;
        RecyclerView.Adapter instructionsAdapter;
        RecyclerView.LayoutManager instructionsLayoutManager;

        instructionsRV = (RecyclerView) activity.findViewById(R.id.nav_instruction_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        instructionsRV.setHasFixedSize(true);

        // use a linear layout manager
        instructionsLayoutManager = new LinearLayoutManager(activity);
        instructionsRV.setLayoutManager(instructionsLayoutManager);

        // specify an adapter (see also next example)
        instructionsAdapter = new InstructionAdapter(Navigasi.getNavigator().getGhResponse().getInstructions());
        instructionsRV.setAdapter(instructionsAdapter);

    }



/*

    */
/**
     * handler clicks on nav button
     *//*

    private void navBtnHandler() {
        navigationBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                sideBarVP.setVisibility(View.INVISIBLE);
                if (Navigasi.getNavigator().isOn()) {
                    navInstructionListVP.setVisibility(View.VISIBLE);
                } else {
                    navSettingsVP.setVisibility(View.VISIBLE);
                }
            }
        });
    }
*/


    /**
     * start button: control button handler FAB
     */

    private void controlBtnHandler() {
        final ScaleAnimation anim = new ScaleAnimation(0, 1, 0, 1);
        anim.setFillBefore(true);
        anim.setFillAfter(true);
        anim.setFillEnabled(true);
        anim.setDuration(300);
        anim.setInterpolator(new OvershootInterpolator());

        controlBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (isMenuVisible()) {
                    setMenuVisible(false);
                    sideBarMenuVP.setVisibility(View.INVISIBLE);
                    controlBtn.setImageResource(R.drawable.ic_keyboard_arrow_up_white_24dp);
                    controlBtn.startAnimation(anim);
                } else {
                    setMenuVisible(true);
                    sideBarMenuVP.setVisibility(View.VISIBLE);
                    controlBtn.setImageResource(R.drawable.ic_keyboard_arrow_down_white_24dp);
                    controlBtn.startAnimation(anim);
                }
            }
        });
    }

    /**
     * implement zoom btn
     */
    protected void zoomControlHandler(final MapView mapView) {
        zoomInBtn.setImageResource(R.drawable.ic_add_white_24dp);
        zoomOutBtn.setImageResource(R.drawable.ic_remove_white_24dp);

        zoomInBtn.setOnClickListener(new View.OnClickListener() {
            MapViewPosition mvp = mapView.getModel().mapViewPosition;

            @Override public void onClick(View v) {
                if (mvp.getZoomLevel() < Variable.getVariable().getZoomLevelMax()) mvp.zoomIn();
            }
        });
        zoomOutBtn.setOnClickListener(new View.OnClickListener() {
            MapViewPosition mvp = mapView.getModel().mapViewPosition;

            @Override public void onClick(View v) {
                if (mvp.getZoomLevel() > Variable.getVariable().getZoomLevelMin()) mvp.zoomOut();
            }
        });
    }

    /**
     * move map to my current location as the center of the screen
     */
    protected void showMyLocation(final MapView mapView) {
        showPositionBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (MapActivity2.getmCurrentLocation() != null) {
                    showPositionBtn.setImageResource(R.drawable.ic_my_location_white_24dp);
                    PetaHandler.getPetaHandler().centerPointOnMap(
                            new LatLong(MapActivity2.getmCurrentLocation().getLatitude(),
                                    MapActivity2.getmCurrentLocation().getLongitude()), 0);

 /*                                       mapView.getModel().mapViewPosition.setMapPosition(new MapPosition(
                                               new LatLong(MapActivity.getmCurrentLocation().getLatitude(),
                                                       MapActivity.getmCurrentLocation().getLongitude()),
                                                mapView.getModel().mapViewPosition.getZoomLevel()));*/

                } else {
                    showPositionBtn.setImageResource(R.drawable.ic_place_blue_24dp);
                    Toast.makeText(activity, "No Location Available", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * @return side bar menu visibility status
     */
    public boolean isMenuVisible() {
        return menuVisible;
    }

    /**
     * side bar menu visibility
     *
     * @param menuVisible
     */
    public void setMenuVisible(boolean menuVisible) {
        this.menuVisible = menuVisible;
    }

    /**
     * the change on navigator: navigation is used or not
     *
     * @param on
     */
    @Override public void statusChanged(boolean on) {
        if (on) {
            navigationBtn.setImageResource(R.drawable.ic_directions_white_24dp);
        } else {
            navigationBtn.setImageResource(R.drawable.ic_navigation_white_24dp);
        }
    }

    /**
     * called from Map activity when onBackpressed
     *
     * @return false no actions will perform; return true MapActivity will be placed back in the activity stack
     */



    private void log(String str) {
        Log.i(this.getClass().getSimpleName(), "-----------------" + str);
    }
}