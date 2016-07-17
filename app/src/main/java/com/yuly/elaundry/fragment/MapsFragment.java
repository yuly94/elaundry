package com.yuly.elaundry.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yuly.elaundry.R;

import com.yuly.elaundry.activity.LoginActivityNew;
import com.yuly.elaundry.app.AppConfig;
import com.yuly.elaundry.app.AppController;
import com.yuly.elaundry.function.LocationPermissionUtils;
import com.yuly.elaundry.helper.SQLiteHandler;
import com.yuly.elaundry.helper.SessionManager;
import com.yuly.elaundry.helper.VolleyErrorHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_HYBRID;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_SATELLITE;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_TERRAIN;


public class MapsFragment extends FragmentActivity implements GoogleMap.OnMyLocationButtonClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback, NavigationView.OnNavigationItemSelectedListener {

    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean mPermissionDenied = false;


    private GoogleMap mMap;

    private SQLiteHandler db;
    private SessionManager session;

    private ProgressDialog pDialog;

    private String apiKey;


    private ShareActionProvider mShareActionProvider;
    ArrayList<LatLng> pointList = new ArrayList<LatLng>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_drawer_main);

/*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

*/

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from SQLite
        HashMap<String, String> user = db.getUserDetails();

        apiKey = user.get("api");

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);




        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);





        /*
      ATTENTION: This was auto-generated to implement the App Indexing API.
      See https://g.co/AppIndexing/AndroidStudio for more information.
     */
        GoogleApiClient client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


        if (savedInstanceState == null) {
            // First incarnation of this activity.
            mapFragment.setRetainInstance(true);
        }
        // Restoring the markers on configuration changes
        else {
            if (savedInstanceState.containsKey("points")) {
                pointList = savedInstanceState.getParcelableArrayList("points");
                if (pointList != null) {
                    for (int i = 0; i < pointList.size(); i++) {
                        drawMarker(pointList.get(i));
                    }
                }
            }
        }

        mapFragment.getMapAsync(this);
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMyLocationButtonClickListener(this);
        enableMyLocation();

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {

                // Drawing the currently touched marker on the map
                drawMarker(point);

                // Adding the currently created marker position to the arraylist
                pointList.add(point);

                Toast.makeText(getBaseContext(), "Marker is added to the Map", Toast.LENGTH_SHORT).show();
            }
        });

                makeJsonArryReq();


    }


    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            LocationPermissionUtils.requestPermission((AppCompatActivity) getApplicationContext(), LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }




    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (LocationPermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */

    private void showMissingPermissionError() {
        LocationPermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }


    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     */
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MapsFragment.this, LoginActivityNew.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // Inflate menu resource file.
        getMenuInflater().inflate(R.menu.menu_main, menu);




        return true;
    }

    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement

        if (id == R.id.menu_item_share) {
            // return true;

            // Fetch and store ShareActionProvider
            mShareActionProvider = (ShareActionProvider) item.getActionProvider();

        } else
        if (id == R.id.action_change_pass) {
            // return true;


            finish();
            System.exit(0);

        } else if (id == R.id.action_exit) {

            logoutUser();
        }

        switch (item.getItemId()) {

            case R.id.action_zoom:

                if (!item.isChecked()){
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                    item.setChecked(true);
                } else {
                    mMap.getUiSettings().setZoomControlsEnabled(false);
                    item.setChecked(false);
                }

                break;
            case R.id.menu_item_share:

                item.setChecked(!item.isChecked());

                break;


        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_terrain) {
            // Handle the camera action
            mMap.setMapType(MAP_TYPE_TERRAIN);

        } else if (id == R.id.nav_satellite) {
            mMap.setMapType(MAP_TYPE_SATELLITE);

        } else if (id == R.id.nav_hybrid) {
            mMap.setMapType(MAP_TYPE_HYBRID);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_logout) {
            logoutUser();

        } else if (id == R.id.nav_exit) {


            finish();
            System.exit(0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();


    }

    /**
     * Making json array request
     */
    private void makeJsonArryReq() {
        showProgressDialog();
        JsonArrayRequest req = new JsonArrayRequest(AppConfig.URL_MAPS,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.d(AppController.TAG, "on Response " + response.toString());

                        //   msgResponse.setText(response.toString());
                        hideProgressDialog();


                        parseJson(response);


                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


                String e = VolleyErrorHelper.getMessage(error,getApplicationContext());
                VolleyLog.d(AppController.TAG, "Error: " + e);

                hideProgressDialog();
            }
        }) {

            /**
             * Passing some request headers
             * */


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", apiKey);
                return headers;
            }


        };

        // Adding request to request queue
        String tag_json_arry = "jarray_req";
        AppController.getInstance().addToRequestQueue(req, tag_json_arry);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_arry);
    }

    /**
     * Parsing json reponse and passing the data to feed view list adapter
     *
     * respons */
    private void parseJson(JSONArray response)  {



        for (int i = 0; i < response.length(); i++) {

            try {
                JSONObject jObj = response.getJSONObject(i);
                boolean error = jObj.getBoolean("error");
                if (!error) {

                    JSONObject maps = jObj.getJSONObject("maps");

                    //   String maps_id = maps.getString("maps_id");
                    String name = maps.getString("name");
                    String address = maps.getString("address");
                    String city = maps.getString("city");
                    String province = maps.getString("province");
                    Double latitude = Double.valueOf(maps.getString("latitude"));
                    Double longitude = Double.valueOf(maps.getString("longitude"));


                    Log.d(AppController.TAG, "Maps name : " + name);
                    Log.d(AppController.TAG, "Lat, Long : " + latitude + " " + longitude);


                    // create marker
                    MarkerOptions marker = new MarkerOptions()
                            .icon(BitmapDescriptorFactory.defaultMarker())
                            .position(new LatLng(latitude, longitude))
                            .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                            .title(name)
                            .snippet(address + ", " + city + ", " + province);

                    // Changing marker icon
                    //   marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place_24dp));

                    // adding marker
                    mMap.addMarker(marker);

                } else {

                    // Error occurred in registration. Get the error
                    // message
                    String errorMsg = jObj.getString("message");
                    Toast.makeText(getApplicationContext(),
                            errorMsg, Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // notify data changes
        Toast.makeText(getApplicationContext(), "Maps loaded successfully", Toast.LENGTH_LONG).show();


    }


    // Draw a marker at the "point"
    private void drawMarker(LatLng point){
        // Creating an instance of MarkerOptions
        MarkerOptions markerOptions = new MarkerOptions();

        // Setting latitude and longitude for the marker
        markerOptions.position(point);

        // Setting a title for this marker
        markerOptions.title("Lat:"+point.latitude+","+"Lng:"+point.longitude);


        // Adding marker on the Google Map
        mMap.addMarker(markerOptions);
    }

    // A callback method, which is invoked on configuration is changed
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Adding the pointList arraylist to Bundle
        outState.putParcelableArrayList("points", pointList);

        // Saving the bundle
        super.onSaveInstanceState(outState);
    }

}



