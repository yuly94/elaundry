package com.yuly.elaundry.kurir.controller.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.graphhopper.GraphHopper;
import com.yuly.elaundry.kurir.R;
import com.yuly.elaundry.kurir.model.peta.CariRuteHandler;
import com.yuly.elaundry.kurir.model.util.Variable;

import org.mapsforge.core.model.LatLong;
import org.mapsforge.map.android.graphics.AndroidGraphicFactory;
import org.mapsforge.map.android.view.MapView;
import org.mapsforge.map.layer.Layers;
import org.mapsforge.map.layer.overlay.Marker;

import java.io.File;

public class CariRuteActivity extends AppCompatActivity implements LocationListener {
    private MapView mapView;
    private static Location mCurrentLocation;
    private Marker mPositionMarker;
    private Location mLastLocation;
    private CariRuteActions PetaActions;
    private LocationManager locationManager;

    private GraphHopper hopper;


    private File mapsFolder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

       //         set status bar
      //  new SetStatusBarColor().setStatusBarColor(findViewById(R.id.statusBarBackgroundDownload),
        //        getResources().getColor(R.color.my_primary_dark), this);


     /*   //         set status bar
        new SetStatusBarColor().setStatusBarColor(findViewById(R.id.statusBarBackgroundSettings),
                getResources().getColor(R.color.my_primary_dark), this.getActivity());

*/

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 5, this);

        boolean greaterOrEqKitkat = Build.VERSION.SDK_INT >= 19;
        if (greaterOrEqKitkat) {
            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                logUser("Elaundry is not usable without an external storage!");
                return;
            }
            mapsFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    Variable.getVariable().getMapDirectory());
        } else
            mapsFolder = new File(Environment.getExternalStorageDirectory(), Variable.getVariable().getMapDownloadDirectory());

            Variable.getVariable().setContext(getApplicationContext());
            Variable.getVariable().setZoomLevels(22, 1);
            AndroidGraphicFactory.createInstance(getApplication());
            mapView = new MapView(this);
            mapView.setClickable(true);
            mapView.setBuiltInZoomControls(false);

            CariRuteHandler.getPetaHandler()
                    //  .init(this, mapView, Variable.getVariable().getCountry(), Variable.getVariable().getMapsFolder());

                    .init(this, mapView, Variable.getVariable().getCountry(), mapsFolder);

            CariRuteHandler.getPetaHandler().loadMap(new File(mapsFolder,
                    Variable.getVariable().getCountry() + "-gh"));


            customMapView();
            checkGpsAvailability();
            getMyLastLocation();
            updateCurrentLocation(null);

            Log.d("oncreate : ", DetailPetaRuteActivity.class.getSimpleName());



    }



    public void tambakan(){

        // buatMarker((-7.817117399999998), (112.0287791));
        LatLong mcLatLong = new LatLong(-7.817117399999998,112.0287791);
        //   Layers layers = mapView.getLayerManager().getLayers();

        CariRuteHandler.getPetaHandler().tambahMarkerMerah(mcLatLong);

        //  mPositionMarker = PetaHandler.getPetaHandler().createMarker(mcLatLong, R.drawable.ic_place_green_24dp);
        // layers.add(mPositionMarker);


    }

    public void tambakanMarker(Double lat,Double lon){

        // buatMarker((-7.817117399999998), (112.0287791));
         LatLong mcLatLong = new LatLong(lat,lon);
     //   Layers layers = mapView.getLayerManager().getLayers();

        CariRuteHandler.getPetaHandler().tambahMarkerMerah(mcLatLong);

      //  mPositionMarker = PetaHandler.getPetaHandler().createMarker(mcLatLong, R.drawable.ic_place_green_24dp);
       // layers.add(mPositionMarker);


    }


    /**
     * inject and inflate activity map content to map activity context and bring it to front
     */
    private void customMapView() {
        ViewGroup inclusionViewGroup = (ViewGroup) findViewById(R.id.custom_map_view_layout);
        View inflate = LayoutInflater.from(this).inflate(R.layout.activity_map_baru, null);
        inclusionViewGroup.addView(inflate);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.my_toolbar);

        setSupportActionBar(mToolbar);

        if (getSupportActionBar()!=null) {
            getSupportActionBar().setTitle("Peta");

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            // Thema
            themeUtils.onActivityCreateSetTheme(this,getSupportActionBar(),this);
        }

        inclusionViewGroup.getParent().bringChildToFront(inclusionViewGroup);

/*        new SetStatusBarColor().setSystemBarColor(findViewById(R.id.statusBarBackgroundMap),
                getResources().getColor(R.color.my_primary_dark_transparent), this);*/


        PetaActions = new CariRuteActions(this, mapView);


    }

    /**
     * check if GPS enabled and if not send user to the GSP settings
     */
    private void checkGpsAvailability() {
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
    }



    /**
     * Updates the users location based on the location
     *
     * @param location Location
     */
    private void updateCurrentLocation(Location location) {
        if (location != null) {
            mCurrentLocation = location;
        } else if (mLastLocation != null && mCurrentLocation == null) {
            mCurrentLocation = mLastLocation;
        }
        if (mCurrentLocation != null) {
            LatLong mcLatLong = new LatLong(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
/*            if (Tracking.getTracking().isTracking()) {
                MapHandler.getPetaHandler().addTrackPoint(mcLatLong);
                Tracking.getTracking().addPoint(mCurrentLocation);
            }*/
            Layers layers = mapView.getLayerManager().getLayers();
            CariRuteHandler.getPetaHandler().removeLayer(layers, mPositionMarker);
            mPositionMarker = CariRuteHandler.getPetaHandler().createMarker(mcLatLong, R.drawable.ic_place_blue_24dp);
            layers.add(mPositionMarker);
            PetaActions.showPositionBtn.setImageResource(R.drawable.ic_my_location_white_24dp);
        } else {
            PetaActions.showPositionBtn.setImageResource(R.drawable.ic_location_searching_white_24dp);
        }
    }

    @Override
    public void onBackPressed() {
      /*  boolean back = PetaActions.homeBackKeyPressed();
        if (back) {
             moveTaskToBack(true);
*/
             finish();

        //}
        // if false do nothing
    }

    public void markerPemesanan(LatLong mcLatLong){
        Layers layers = mapView.getLayerManager().getLayers();
        mPositionMarker = CariRuteHandler.getPetaHandler().createMarker(mcLatLong, R.drawable.ic_place_blue_24dp);
        layers.add(mPositionMarker);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mCurrentLocation != null) {
            Variable.getVariable().setLastLocation(mapView.getModel().mapViewPosition.getMapPosition().latLong);
            //                        log("last browsed location : "+mapView.getModel().mapViewPosition
            // .getMapPosition().latLong);
        }
/*
        if (mapView != null)
            Variable.getVariable().setLastZoomLevel(mapView.getModel().mapViewPosition.getZoomLevel());
        Variable.getVariable().saveVariables();*/
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        if (hopper != null)
            hopper.close();

        hopper = null;
        // necessary?
        System.gc();
    }


    /**
     * @return my currentLocation
     */
    public static Location getmCurrentLocation() {
        return mCurrentLocation;
    }

    private void getMyLastLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location logps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location lonet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (lonet != null) {
            mLastLocation = lonet;
        } else if (logps != null) {
            mLastLocation = logps;
        } else {
            mLastLocation = null;
        }
    }

    /**
     * Called when the location has changed.
     * There are no restrictions on the use of the supplied Location object.
     *
     * @param location The new location, as a Location object.
     */
    @Override public void onLocationChanged(Location location) {
        updateCurrentLocation(location);
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    public void onProviderEnabled(String provider) {
        Toast.makeText(getBaseContext(), "Gps hidup!! ", Toast.LENGTH_SHORT).show();
    }

    public void onProviderDisabled(String provider) {
        Toast.makeText(getBaseContext(), "Gps mati!!", Toast.LENGTH_SHORT).show();
    }

    /**
     * send message to logcat
     *
     * @param str
     */
    private void log(String str) {
        Log.i(this.getClass().getSimpleName(), "-------" + str);
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

    private void logUser(String str) {
        log(str);
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }


    private void log(String str, Throwable t) {
        Log.i("GH", str, t);
    }

}
