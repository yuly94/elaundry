package com.yuly.elaundry.kurir.controller.peta;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yuly.elaundry.kurir.R;
import com.yuly.elaundry.kurir.model.dataType.Destination;
import com.yuly.elaundry.kurir.model.listeners.PetaSayaHandlerListener;
import com.yuly.elaundry.kurir.model.listeners.NavigasiSayaListener;

import com.yuly.elaundry.kurir.model.map.DetailPetaNavigasi;
import com.yuly.elaundry.kurir.model.peta.PetaSayaHandler;
import com.yuly.elaundry.kurir.model.map.PetaSayaNavigasi;
import com.yuly.elaundry.kurir.model.peta.DetailPetaRuteHandler;
import com.yuly.elaundry.kurir.model.util.PetunjukArahAdapter;
import com.yuly.elaundry.kurir.model.util.MyUtility;
import com.yuly.elaundry.kurir.model.util.Variable;

import org.mapsforge.core.model.LatLong;
import org.mapsforge.map.android.view.MapView;
import org.mapsforge.map.model.MapViewPosition;


public class PetaSayaActions implements NavigasiSayaListener, PetaSayaHandlerListener {
    private Activity activity;
    protected FloatingActionButton tombolLokasi, tombolNavigasi, tombolSetting, tombolMenu;
    protected FloatingActionButton tombolPerbesar, tombolPerkecil;
    private ViewGroup sideBarVP, sideBarMenuVP, navSettingsVP, navSettingsFromVP, navSettingsToVP, navInstructionVP,
            navListPenunjukJalanVP;
    private boolean menuVisible;
    /**
     * true handle on start point ; false handle on end point
     */
    private boolean onStartPoint;
    private EditText fromLocalET, toLocalET;

    public PetaSayaActions(Activity activity, MapView mapView) {
        this.activity = activity;
        this.tombolLokasi = (FloatingActionButton) activity.findViewById(R.id.fab_lokasi);
        this.tombolNavigasi = (FloatingActionButton) activity.findViewById(R.id.fab_navigasi);
        this.tombolSetting = (FloatingActionButton) activity.findViewById(R.id.fab_setting);
        this.tombolMenu = (FloatingActionButton) activity.findViewById(R.id.fab_menu);
        this.tombolPerbesar = (FloatingActionButton) activity.findViewById(R.id.fab_besarkan);
        this.tombolPerkecil = (FloatingActionButton) activity.findViewById(R.id.fab_kecilkan);

        // view groups managed by separate layout xml file : //map_sidebar_layout/map_sidebar_menu_layout
        this.sideBarVP = (ViewGroup) activity.findViewById(R.id.menu_nafigasi_peta);
        this.sideBarMenuVP = (ViewGroup) activity.findViewById(R.id.group_tombol_navigasi);
        this.navSettingsVP = (ViewGroup) activity.findViewById(R.id.nav_settings_layout);
        this.navSettingsFromVP = (ViewGroup) activity.findViewById(R.id.nav_settings_from_layout);
        this.navSettingsToVP = (ViewGroup) activity.findViewById(R.id.nav_settings_to_layout);
        //this.navInstructionVP = (ViewGroup) activity.findViewById(R.id.nav_instruction_layout); // TODO
        this.navListPenunjukJalanVP = (ViewGroup) activity.findViewById(R.id.nav_instruction_list_layout);
        //form location and to location textView
        this.fromLocalET = (EditText) activity.findViewById(R.id.nav_settings_from_local_et);
        this.toLocalET = (EditText) activity.findViewById(R.id.nav_settings_to_local_et);

        this.menuVisible = false;
        this.onStartPoint = true;

        PetaSayaHandler.getPetaSayaHandler().setPetaSayaHandlerListener(this);
        PetaSayaNavigasi.getNavigator().addListener(this);

        controlBtnHandler();
        zoomControlHandler(mapView);
        showMyLocation(mapView);
        navBtnHandler();
        navSettingsHandler();
    }




    /**
     * navigation settings implementation
     * <p>
     * settings clear button
     * <p>
     * settings search button
     */
    private void navSettingsHandler() {
        final ImageButton navSettingsClearBtn = (ImageButton) activity.findViewById(R.id.nav_settings_clear_btn);
        navSettingsClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                navSettingsVP.setVisibility(View.INVISIBLE);
                sideBarVP.setVisibility(View.VISIBLE);
            }
        });
        ImageButton navSettingsSearchBtn = (ImageButton) activity.findViewById(R.id.nav_settings_search_btn);
        navSettingsSearchBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO implement search for input locations
                searchBtnActions();
            }
        });
        travelModeSetting();
        settingsFromItemHandler();
        settingsToItemHandler();
    }

    /**
     * perform actions when search btn is clicked
     * <p>
     * 1. check edit text and convert value to start point or end point
     */
    private void searchBtnActions() {
        String fls = fromLocalET.getText().toString();
        String tls = toLocalET.getText().toString();
        LatLong fl = null, tl = null;
        if (fls.length() > 2) {
            fl = MyUtility.getLatLong(fls);
        }
        if (tls.length() > 2) {
            tl = MyUtility.getLatLong(tls);
        }
        if (fl != null && tl == null) {
            PetaSayaHandler.getPetaSayaHandler().centerPointOnMap(fl, 0);
            addFromMarker(fl);
        }
        if (fl == null && tl != null) {
            PetaSayaHandler.getPetaSayaHandler().centerPointOnMap(tl, 0);
            addToMarker(tl);
        }
        if (fl != null && tl != null) {
            addFromMarker(fl);
            addToMarker(tl);
            Destination.getDestination().setStartPoint(fl);
            Destination.getDestination().setEndPoint(tl);
            activeNavigator();
        }
        if (fl == null && tl == null) {
            Toast.makeText(activity,
                    "Check your input (use coordinates)!\nExample:\nuse degrees: 63° 25′ 47″ N, 10° 23′ 36″ " +
                            "E\nor use digital: 63.429722, 10.393333", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * settings layout:
     * <p>
     * to item handler: when to item is clicked
     */
    private void settingsToItemHandler() {
        final ViewGroup toItemVG = (ViewGroup) activity.findViewById(R.id.map_nav_settings_to_item);
        toItemVG.setOnTouchListener(new View.OnTouchListener() {
            @Override public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        toItemVG.setBackgroundColor(activity.getResources().getColor(R.color.my_primary_light));
                        return true;
                    case MotionEvent.ACTION_UP:
                        toItemVG.setBackgroundColor(activity.getResources().getColor(R.color.my_primary));
                        navSettingsVP.setVisibility(View.INVISIBLE);
                        navSettingsToVP.setVisibility(View.VISIBLE);
                        return true;
                }
                return false;
            }
        });
        //        to layout
        //clear button
        ImageButton toLayoutClearBtn = (ImageButton) activity.findViewById(R.id.nav_settings_to_clear_btn);
        toLayoutClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                navSettingsVP.setVisibility(View.VISIBLE);
                navSettingsToVP.setVisibility(View.INVISIBLE);
            }
        });
        //  to layout: items
        toUseCurrentLocationHandler();
        toChooseFromFavoriteHandler();
        toPointOnMapHandler();
    }

    /**
     * from layout : point item view group  (on to layout)
     * <p>
     * preform actions when point on map item is clicked
     */
    private void toPointOnMapHandler() {
        final ViewGroup pointItem = (ViewGroup) activity.findViewById(R.id.map_nav_settings_to_point);
        pointItem.setOnTouchListener(new View.OnTouchListener() {
            @Override public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        pointItem.setBackgroundColor(activity.getResources().getColor(R.color.my_primary_light));
                        return true;
                    case MotionEvent.ACTION_UP:
                        pointItem.setBackgroundColor(activity.getResources().getColor(R.color.my_primary));
                        onStartPoint = false;
                        navSettingsToVP.setVisibility(View.INVISIBLE);
                        //touch on map
                        //                        Toast.makeText(activity, "Touch on Map to choose your
                        // destination!", Toast.LENGTH_SHORT).show();
                        PetaSayaHandler.getPetaSayaHandler().setNeedLocation(true);
                        return true;
                }
                return false;
            }
        });
    }

    /**
     * choose from favorite list handler: preform actions when choose from favorite item is clicked (on to layout)
     */
    private void toChooseFromFavoriteHandler() {
        //create a list view
        //read from Json file inflater to RecyclerView
        final ViewGroup chooseFavorite = (ViewGroup) activity.findViewById(R.id.map_nav_settings_to_favorite);
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
     * current location handler: preform actions when current location item is clicked (on to layout)
     */
    private void toUseCurrentLocationHandler() {
        final ViewGroup useCurrentLocal = (ViewGroup) activity.findViewById(R.id.map_nav_settings_to_current);
        useCurrentLocal.setOnTouchListener(new View.OnTouchListener() {
            @Override public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        useCurrentLocal.setBackgroundColor(activity.getResources().getColor(R.color.my_primary_light));
                        return true;
                    case MotionEvent.ACTION_UP:
                        useCurrentLocal.setBackgroundColor(activity.getResources().getColor(R.color.my_primary));
                        if (PetaSayaActivity.getmCurrentLocation() != null) {
                            Destination.getDestination().setEndPoint(
                                    new LatLong(PetaSayaActivity.getmCurrentLocation().getLatitude(),
                                            PetaSayaActivity.getmCurrentLocation().getLongitude()));
                            toLocalET.setText(Destination.getDestination().getEndPointToString());
                            addToMarker(Destination.getDestination().getEndPoint());
                            navSettingsToVP.setVisibility(View.INVISIBLE);
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
     * add end point marker to map
     *
     * @param endPoint
     */
    private void addToMarker(LatLong endPoint) {
        PetaSayaHandler.getPetaSayaHandler().addEndMarker(endPoint);
    }

    /**
     * add start point marker to map
     *
     * @param startPoint
     */
    private void addFromMarker(LatLong startPoint) {
        PetaSayaHandler.getPetaSayaHandler().addStartMarker(startPoint);
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
                        PetaSayaHandler.getPetaSayaHandler().setNeedLocation(true);
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
                        if (PetaSayaActivity.getmCurrentLocation() != null) {
                            Destination.getDestination().setStartPoint(
                                    new LatLong(PetaSayaActivity.getmCurrentLocation().getLatitude(),
                                            PetaSayaActivity.getmCurrentLocation().getLongitude()));
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
     * @param petasayashortestPathRunning
     */
    @Override public void pathCalculating(boolean petasayashortestPathRunning) {
        if (!petasayashortestPathRunning && PetaSayaNavigasi.getNavigator().getGhResponse() != null) {
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
            PetaSayaHandler petaSayaHandler = PetaSayaHandler.getPetaSayaHandler();
            petaSayaHandler.calcPath(startPoint.latitude, startPoint.longitude, endPoint.latitude, endPoint.longitude);
            if (Variable.getVariable().isPetunjukArahSayaON()) {
                petaSayaHandler.setNeedPathCal(true);
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
        instructionsAdapter = new PetunjukArahAdapter(DetailPetaNavigasi.getNavigator().getGhResponse().getInstructions());
        instructionsRV.setAdapter(instructionsAdapter);
        initNavListView();
    }

    /**
     * navigation list view
     * <p>
     * make nav list view control button ready to use
     */
    private void initNavListView() {
        fillNavListSummaryValues();
        navSettingsVP.setVisibility(View.INVISIBLE);
        navListPenunjukJalanVP.setVisibility(View.VISIBLE);
        ImageButton clearBtn, stopBtn;
        stopBtn = (ImageButton) activity.findViewById(R.id.nav_instruction_list_stop_btn);
        clearBtn = (ImageButton) activity.findViewById(R.id.nav_instruction_list_clear_btn);
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {

                // 1. Instantiate an AlertDialog.Builder with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage(R.string.stop_navigation_msg).setTitle(R.string.stop_navigation)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // stop!
                                PetaSayaNavigasi.getNavigator().setOn(false);
                                //delete polyline and markers
                                removeNavigation();
                                navListPenunjukJalanVP.setVisibility(View.INVISIBLE);
                                navSettingsVP.setVisibility(View.VISIBLE);
                                dialog.dismiss();
                            }
                        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.dismiss();
                    }
                });
                // Create the AlertDialog object and return it

                // 3. Get the AlertDialog from create()
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                navListPenunjukJalanVP.setVisibility(View.INVISIBLE);
                sideBarVP.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * fill up values for nav list summary
     */
    private void fillNavListSummaryValues() {
        ImageView travelMode;
        travelMode = (ImageView) activity.findViewById(R.id.nav_instruction_list_travel_mode_iv);
        travelMode.setImageResource(PetaSayaNavigasi.getNavigator().getTravelModeResId(true));
        TextView from, to, distance, time;
/*        from = (TextView) activity.findViewById(R.id.nav_instruction_list_summary_from_tv);
        to = (TextView) activity.findViewById(R.id.nav_instruction_list_summary_to_tv);*/
        distance = (TextView) activity.findViewById(R.id.nav_instruction_list_summary_distance_tv);
        time = (TextView) activity.findViewById(R.id.nav_instruction_list_summary_time_tv);
/*
        from.setText(Destination.getDestination().getStartPointToString());
        to.setText(Destination.getDestination().getEndPointToString());*/
        distance.setText(PetaSayaNavigasi.getNavigator().getDistance());
        time.setText(PetaSayaNavigasi.getNavigator().getTime());
    }

    /**
     * remove polyline, markers from map layers
     * <p>
     * set from & to = null
     */
    private void removeNavigation() {
        PetaSayaHandler.getPetaSayaHandler().removeMarkers();
        fromLocalET.setText("");
        toLocalET.setText("");
        PetaSayaNavigasi.getNavigator().setOn(false);
        Destination.getDestination().setStartPoint(null);
        Destination.getDestination().setEndPoint(null);
    }


    /**
     * remove polyline, markers from map layers
     * <p>
     * set from & to = null
     */
    private void hapusPath() {
        DetailPetaRuteHandler.getPetaRuteHandler().removeMarkers();
        fromLocalET.setText("");
        toLocalET.setText("");
        DetailPetaNavigasi.getNavigator().setOn(false);
        Destination.getDestination().setStartPoint(null);
        Destination.getDestination().setEndPoint(null);
    }


    /**
     * set up travel mode
     */
    private void travelModeSetting() {
        final ImageButton jalanKakiBtn, motorBtn;
        jalanKakiBtn = (ImageButton) activity.findViewById(R.id.nav_settings_foot_btn);
        motorBtn = (ImageButton) activity.findViewById(R.id.nav_settings_motor_btn);

        // init travel mode
        switch (Variable.getVariable().getTravelMode()) {
            case "foot":
                jalanKakiBtn.setImageResource(R.drawable.ic_accessibility_black_24dp);
                break;
            case "motorcycle":
                motorBtn.setImageResource(R.drawable.ic_motorcycle_black_24dp);
                break;
        }

        //foot
        jalanKakiBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (!Variable.getVariable().getTravelMode().equalsIgnoreCase("foot")) {
                    Variable.getVariable().setTravelMode("foot");
                    jalanKakiBtn.setImageResource(R.drawable.ic_accessibility_black_24dp);
                    motorBtn.setImageResource(R.drawable.ic_motorcycle_white_24dp);

                    activeNavigator();
                }
            }
        });
        //bike
        motorBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (!Variable.getVariable().getTravelMode().equalsIgnoreCase("motorcycle")) {
                    Variable.getVariable().setTravelMode("motorcycle");
                    jalanKakiBtn.setImageResource(R.drawable.ic_accessibility_white_24dp);
                    motorBtn.setImageResource(R.drawable.ic_motorcycle_black_24dp);
                 
                    activeNavigator();
                }
            }
        });
 
    }

    /**
     * handler clicks on nav button
     */
    private void navBtnHandler() {
        tombolNavigasi.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                sideBarVP.setVisibility(View.INVISIBLE);
                if (PetaSayaNavigasi.getNavigator().isOn()) {
                    navListPenunjukJalanVP.setVisibility(View.VISIBLE);
                } else {
                    navSettingsVP.setVisibility(View.VISIBLE);
                }
            }
        });
    }


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

        tombolMenu.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (isMenuVisible()) {
                    setMenuVisible(false);
                    sideBarMenuVP.setVisibility(View.INVISIBLE);
                    tombolMenu.setImageResource(R.drawable.ic_keyboard_arrow_up_white_24dp);
                    tombolMenu.startAnimation(anim);
                } else {
                    setMenuVisible(true);
                    sideBarMenuVP.setVisibility(View.VISIBLE);
                    tombolMenu.setImageResource(R.drawable.ic_keyboard_arrow_down_white_24dp);
                    tombolMenu.startAnimation(anim);
                }
            }
        });
    }

    /**
     * implement zoom btn
     */
    protected void zoomControlHandler(final MapView mapView) {
        tombolPerbesar.setImageResource(R.drawable.ic_add_white_24dp);
        tombolPerkecil.setImageResource(R.drawable.ic_remove_white_24dp);

        tombolPerbesar.setOnClickListener(new View.OnClickListener() {
            MapViewPosition mvp = mapView.getModel().mapViewPosition;

            @Override public void onClick(View v) {
                if (mvp.getZoomLevel() < Variable.getVariable().getZoomLevelMax()) mvp.zoomIn();
            }
        });
        tombolPerkecil.setOnClickListener(new View.OnClickListener() {
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
        tombolLokasi.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (PetaSayaActivity.getmCurrentLocation() != null) {
                    tombolLokasi.setImageResource(R.drawable.ic_my_location_white_24dp);
                    PetaSayaHandler.getPetaSayaHandler().centerPointOnMap(
                            new LatLong(PetaSayaActivity.getmCurrentLocation().getLatitude(),
                                    PetaSayaActivity.getmCurrentLocation().getLongitude()), 0);

 /*                                       mapView.getModel().mapViewPosition.setMapPosition(new MapPosition(
                                               new LatLong(MapActivity.getmCurrentLocation().getLatitude(),
                                                       MapActivity.getmCurrentLocation().getLongitude()),
                                                mapView.getModel().mapViewPosition.getZoomLevel()));*/

                } else {
                    tombolLokasi.setImageResource(R.drawable.ic_location_searching_white_24dp);
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
            tombolNavigasi.setImageResource(R.drawable.ic_directions_white_24dp);
        } else {
            tombolNavigasi.setImageResource(R.drawable.ic_navigation_white_24dp);
        }
    }

    /**
     * called from Map activity when onBackpressed
     *
     * @return false no actions will perform; return true MapActivity will be placed back in the activity stack
     */
    public boolean homeBackKeyPressed() {
        if (navSettingsVP.getVisibility() == View.VISIBLE) {
            navSettingsVP.setVisibility(View.INVISIBLE);
            sideBarVP.setVisibility(View.VISIBLE);
            return false;
        } else if (navSettingsFromVP.getVisibility() == View.VISIBLE) {
            navSettingsFromVP.setVisibility(View.INVISIBLE);
            navSettingsVP.setVisibility(View.VISIBLE);
            return false;
        } else if (navSettingsToVP.getVisibility() == View.VISIBLE) {
            navSettingsToVP.setVisibility(View.INVISIBLE);
            navSettingsVP.setVisibility(View.VISIBLE);
            return false;
        } else if (navListPenunjukJalanVP.getVisibility() == View.VISIBLE) {
            navListPenunjukJalanVP.setVisibility(View.INVISIBLE);
            sideBarVP.setVisibility(View.VISIBLE);
            return false;
        } else {
            return true;
        }
    }


    private void log(String str) {
        Log.i(this.getClass().getSimpleName(), "-----------------" + str);
    }
}