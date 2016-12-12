package com.yuly.elaundry.kurir.controller.activity;

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
import com.yuly.elaundry.kurir.model.dataType.DataRute;
import com.yuly.elaundry.kurir.model.dataType.Destination;
import com.yuly.elaundry.kurir.model.listeners.PetaRuteHandlerListener;
import com.yuly.elaundry.kurir.model.listeners.NavigatorListener;
import com.yuly.elaundry.kurir.model.map.DetailPetaNavigasi;
import com.yuly.elaundry.kurir.model.peta.DetailPetaRuteHandler;
import com.yuly.elaundry.kurir.model.util.InstructionAdapter;
import com.yuly.elaundry.kurir.model.util.MyUtility;
import com.yuly.elaundry.kurir.model.util.Variable;

import org.mapsforge.core.model.LatLong;
import org.mapsforge.map.android.view.MapView;
import org.mapsforge.map.model.MapViewPosition;


public class DetailPetaRuteActions implements NavigatorListener, PetaRuteHandlerListener {
    private Activity activity;
    protected FloatingActionButton showPositionBtn, navigationBtn, settingsBtn, controlBtn, fab_dapatkan;
    protected FloatingActionButton zoomInBtn, zoomOutBtn;
    private ViewGroup sideBarVP, sideBarMenuVP, navSettingsVP, navSettingsFromVP, navSettingsToVP, navInstructionVP,
            navInstructionListVP;
    private boolean menuVisible;

    private DetailPetaRuteHandler petaRuteHandler;
    private TextView tv_dari, tv_tujuan;
    /**
     * true handle on start point ; false handle on end point
     */
    private boolean onStartPoint;
    private EditText fromLocalET, toLocalET;

    public DetailPetaRuteActions(Activity activity, MapView mapView) {
        this.activity = activity;
        this.showPositionBtn = (FloatingActionButton) activity.findViewById(R.id.fab_lokasi);
        this.fab_dapatkan = (FloatingActionButton) activity.findViewById(R.id.fab_nafigasi);
        this.settingsBtn = (FloatingActionButton) activity.findViewById(R.id.fab_setting);
       // this.controlBtn = (FloatingActionButton) activity.findViewById(R.id.fab_menu);

        this.navigationBtn= (FloatingActionButton) activity.findViewById(R.id.fab_dapatkan);


        this.zoomInBtn = (FloatingActionButton) activity.findViewById(R.id.fab_besarkan);
        this.zoomOutBtn = (FloatingActionButton) activity.findViewById(R.id.fab_kecilkan);
        // view groups managed by separate layout xml file : //map_sidebar_layout/map_sidebar_menu_layout
        this.sideBarVP = (ViewGroup) activity.findViewById(R.id.menu_nafigasi_peta);
        this.sideBarMenuVP = (ViewGroup) activity.findViewById(R.id.group_tombol_navigasi);
        this.navSettingsVP = (ViewGroup) activity.findViewById(R.id.nav_settings_layout);
        this.navSettingsFromVP = (ViewGroup) activity.findViewById(R.id.nav_settings_from_layout);
        this.navSettingsToVP = (ViewGroup) activity.findViewById(R.id.nav_settings_to_layout);
        //        this.navInstructionVP = (ViewGroup) activity.findViewById(R.id.nav_instruction_layout); // TODO
        this.navInstructionListVP = (ViewGroup) activity.findViewById(R.id.nav_instruction_list_layout);
        //form location and to location textView
        this.fromLocalET = (EditText) activity.findViewById(R.id.nav_settings_from_local_et);
        this.toLocalET = (EditText) activity.findViewById(R.id.nav_settings_to_local_et);

/*        this.tv_dari = (TextView) activity.findViewById(R.id.nav_instruction_list_summary_to_tv);
        this.tv_tujuan = (TextView) activity.findViewById(R.id.nav_instruction_list_summary_from_tv);*/

        this.menuVisible = false;
        this.onStartPoint = true;
        DetailPetaRuteHandler.getPetaRuteHandler().setRutePetaHandlerListener(this);
        DetailPetaNavigasi.getNavigator().addListener(this);
        //controlBtnHandler();
        zoomControlHandler(mapView);
        showMyLocation(mapView);
        navBtnHandler();
        navSettingsHandler();
        settingsBtnHandler();

        showRute();

    }

    /**
     * init and implement performance for settings
     */
    private void settingsBtnHandler() {
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                AppSettings.getAppSettings().set(activity, sideBarVP);

            }
        });
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
            DetailPetaRuteHandler.getPetaRuteHandler().centerPointOnMap(fl, 0);
            addFromMarker(fl);
        }
        if (fl == null && tl != null) {
            DetailPetaRuteHandler.getPetaRuteHandler().centerPointOnMap(tl, 0);
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
        toPointOnRutePetaHandler();
    }

    /**
     * from layout : point item view group  (on to layout)
     * <p>
     * preform actions when point on map item is clicked
     */
    private void toPointOnRutePetaHandler() {
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
                        DetailPetaRuteHandler.getPetaRuteHandler().setNeedLocation(true);
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
                        if (DetailPetaRuteActivity.getmCurrentLocation() != null) {
                            Destination.getDestination().setEndPoint(
                                    new LatLong(DetailPetaRuteActivity.getmCurrentLocation().getLatitude(),
                                            DetailPetaRuteActivity.getmCurrentLocation().getLongitude()));
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
        DetailPetaRuteHandler.getPetaRuteHandler().addEndMarker(endPoint);
    }

    /**
     * add start point marker to map
     *
     * @param startPoint
     */
    private void addFromMarker(LatLong startPoint) {
        DetailPetaRuteHandler.getPetaRuteHandler().addStartMarker(startPoint);
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
        pointOnRutePetaHandler();
    }

    /**
     * from layout : point item view group
     * <p>
     * preform actions when point on map item is clicked
     */
    private void pointOnRutePetaHandler() {
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
                        DetailPetaRuteHandler.getPetaRuteHandler().setNeedLocation(true);
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
                        if (DetailPetaRuteActivity.getmCurrentLocation() != null) {
                            Destination.getDestination().setStartPoint(
                                    new LatLong(DetailPetaRuteActivity.getmCurrentLocation().getLatitude(),
                                            DetailPetaRuteActivity.getmCurrentLocation().getLongitude()));
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
        if (!shortestPathRunning && DetailPetaNavigasi.getNavigator().getGhResponse() != null) {
            activeDirections();
        }
    }

    protected void showRute() {
        fab_dapatkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeNavigator();
            }
        });
    }

    /**
     * drawer polyline on map , active navigator instructions(directions) if on
     */
    public void activeNavigator() {
        if (DetailPetaRuteActivity.getmCurrentLocation() != null) {
                LatLong lokasiAwal = new LatLong(DetailPetaRuteActivity.getmCurrentLocation().getLatitude(),
            DetailPetaRuteActivity.getmCurrentLocation().getLongitude());//new LatLong ( -7.8130979,112.0157406);
            LatLong lokasiAkhir = DataRute.getDatarute().getEndPoint();
            // show path finding process
            navSettingsVP.setVisibility(View.INVISIBLE);

            View pathfinding = activity.findViewById(R.id.map_nav_settings_path_finding);
            pathfinding.setVisibility(View.VISIBLE);
            pathfinding.bringToFront();
            DetailPetaRuteHandler mapHandler = DetailPetaRuteHandler.getPetaRuteHandler();

            mapHandler.calcPath(
                    lokasiAwal.latitude, lokasiAwal.longitude, lokasiAkhir.latitude, lokasiAkhir.longitude

            );



            DetailPetaRuteHandler.getPetaRuteHandler().tambahMarkerMerah(lokasiAkhir);

           /* tv_dari.setText(lokasiAwal.latitude+","+lokasiAwal.longitude);
            tv_tujuan.setText(lokasiAkhir.latitude+","+lokasiAkhir.longitude);*/
           // petaRuteHandler.addMarkers(lokasiAwal, lokasiAkhir);

            Log.d("Active", "navigator");

            if (Variable.getVariable().isDirectionsON()) {
                mapHandler.setNeedPathCal(true);
                //rest running at
            }
        } else {

            Toast.makeText(activity, "gagal mendapatkan lokasi anda",Toast.LENGTH_LONG).show();
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
        instructionsAdapter = new InstructionAdapter(DetailPetaNavigasi.getNavigator().getGhResponse().getInstructions());
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
        navInstructionListVP.setVisibility(View.VISIBLE);
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
                                DetailPetaNavigasi.getNavigator().setOn(false);
                                //delete polyline and markers
                                removeNavigation();
                                navInstructionListVP.setVisibility(View.INVISIBLE);
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
                navInstructionListVP.setVisibility(View.INVISIBLE);
                sideBarVP.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * fill up values for nav list summary
     */
    private void fillNavListSummaryValues() {
        ImageView travelMode;
       // travelMode = (ImageView) activity.findViewById(R.id.nav_instruction_list_travel_mode_iv);
       // travelMode.setImageResource(Navigasi.getNavigator().getTravelModeResId(true));
        TextView from, to, distance, time;
/*        from = (TextView) activity.findViewById(R.id.nav_instruction_list_summary_from_tv);
        to = (TextView) activity.findViewById(R.id.nav_instruction_list_summary_to_tv);*/
        distance = (TextView) activity.findViewById(R.id.nav_instruction_list_summary_distance_tv);
        time = (TextView) activity.findViewById(R.id.nav_instruction_list_summary_time_tv);

    /*    from.setText(Destination.getDestination().getStartPointToString());
        to.setText(Destination.getDestination().getEndPointToString());*/
        distance.setText(DetailPetaNavigasi.getNavigator().getDistance());
        time.setText(DetailPetaNavigasi.getNavigator().getTime());
    }

    /**
     * remove polyline, markers from map layers
     * <p>
     * set from & to = null
     */
    private void removeNavigation() {
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
        final ImageButton footBtn, bikeBtn, carBtn;
        footBtn = (ImageButton) activity.findViewById(R.id.nav_settings_foot_btn);
        bikeBtn = (ImageButton) activity.findViewById(R.id.nav_settings_bike_btn);
        carBtn = (ImageButton) activity.findViewById(R.id.nav_settings_car_btn);
        // init travel mode
        switch (Variable.getVariable().getTravelMode()) {
            case "foot":
                footBtn.setImageResource(R.drawable.ic_directions_walk_orange_24dp);
                break;
            case "bike":
                bikeBtn.setImageResource(R.drawable.ic_directions_bike_orange_24dp);
                break;
            case "car":
                carBtn.setImageResource(R.drawable.ic_add_shopping_cart_24dp);
                break;
        }

        //foot
        footBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (!Variable.getVariable().getTravelMode().equalsIgnoreCase("foot")) {
                    Variable.getVariable().setTravelMode("foot");
                    footBtn.setImageResource(R.drawable.ic_directions_walk_orange_24dp);
                    bikeBtn.setImageResource(R.drawable.ic_directions_bike_white_24dp);
                    carBtn.setImageResource(R.drawable.ic_directions_car_white_24dp);
                    activeNavigator();
                }
            }
        });
        //bike
        bikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (!Variable.getVariable().getTravelMode().equalsIgnoreCase("bike")) {
                    Variable.getVariable().setTravelMode("bike");
                    footBtn.setImageResource(R.drawable.ic_directions_walk_white_24dp);
                    bikeBtn.setImageResource(R.drawable.ic_directions_bike_orange_24dp);
                    carBtn.setImageResource(R.drawable.ic_directions_car_white_24dp);
                    activeNavigator();
                }
            }
        });
        // car
        carBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (!Variable.getVariable().getTravelMode().equalsIgnoreCase("car")) {
                    Variable.getVariable().setTravelMode("car");
                    footBtn.setImageResource(R.drawable.ic_directions_walk_white_24dp);
                    bikeBtn.setImageResource(R.drawable.ic_directions_bike_white_24dp);
                    carBtn.setImageResource(R.drawable.ic_directions_car_orange_24dp);
                    activeNavigator();
                }
            }
        });
    }

    /**
     * handler clicks on nav button
     */
    private void navBtnHandler() {
        navigationBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                sideBarVP.setVisibility(View.INVISIBLE);
                if (DetailPetaNavigasi.getNavigator().isOn()) {
                    navInstructionListVP.setVisibility(View.VISIBLE);
                } else {
                    navSettingsVP.setVisibility(View.VISIBLE);
                }
            }
        });
    }

/*

    */
/**
     * start button: control button handler FAB
     *//*


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
*/

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
                if (DetailPetaRuteActivity.getmCurrentLocation() != null) {
                    showPositionBtn.setImageResource(R.drawable.ic_my_location_white_24dp);
                    DetailPetaRuteHandler.getPetaRuteHandler().centerPointOnMap(
                            new LatLong(DetailPetaRuteActivity.getmCurrentLocation().getLatitude(),
                                    DetailPetaRuteActivity.getmCurrentLocation().getLongitude()), 0);

 /*                                       mapView.getModel().mapViewPosition.setMapPosition(new MapPosition(
                                               new LatLong(PetaRuteActivity.getmCurrentLocation().getLatitude(),
                                                       PetaRuteActivity.getmCurrentLocation().getLongitude()),
                                                mapView.getModel().mapViewPosition.getZoomLevel()));*/

                } else {
                    showPositionBtn.setImageResource(R.drawable.ic_location_searching_white_24dp);
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
     * @return false no actions will perform; return true PetaRuteActivity will be placed back in the activity stack
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
        } else if (navInstructionListVP.getVisibility() == View.VISIBLE) {
            navInstructionListVP.setVisibility(View.INVISIBLE);
            sideBarVP.setVisibility(View.VISIBLE);
            return false;
        } else if (AppSettings.getAppSettings().getAppSettingsVP() != null &&
                AppSettings.getAppSettings().getAppSettingsVP().getVisibility() == View.VISIBLE) {
            AppSettings.getAppSettings().getAppSettingsVP().setVisibility(View.INVISIBLE);
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