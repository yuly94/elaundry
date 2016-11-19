package com.yuly.elaundry.fragment;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.yuly.elaundry.R;
import com.yuly.elaundry.app.AppConfig;
import com.yuly.elaundry.app.AppController;
import com.yuly.elaundry.app.Constants;
import com.yuly.elaundry.helper.SQLiteHandler;
import com.yuly.elaundry.helper.SessionManager;
import com.yuly.elaundry.helper.VolleyErrorHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MapsFragmentLocation extends Fragment implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private Context mContext;

    // LogCat tag
    private static final String TAG = MapsFragmentLocation.class.getSimpleName();

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    //private LocationRequest mLocationRequest;
    private Location mLastLocation;
    private Location location; // location
    double mlatitude; // latitude
    double mlongitude; // longitude
    private LatLngBounds MYLOCATION_VIEW;
    protected LocationManager locationManager;
    boolean canGetLocation = false;

    private Button pickerButton;
    private FloatingActionButton btnPesan;
    private EditText etLatitude, etLongitude,etCatatan, etAlamat;
    private RadioGroup rgPaket;
    private RadioButton rButton, rbEkonomis, rbReguler, rbExpress;
    private CheckBox cbBaju, cbCelana, cbRok;

    private String data_pemesanan_paket;


    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    // boolean flag to toggle periodic location updates
    private boolean mRequestingLocationUpdates = false;

    private static final int PLACE_PICKER_REQUEST = 1;

    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 10000; // 10 sec
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 10; // 10 meters


    //Database
    private SQLiteHandler db;
    private SessionManager session;

    private ProgressDialog pDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmen_pemesanan, container,
                false);

        initViews(view);

        // SqLite database handler
        db = new SQLiteHandler(getActivity());

        // session manager
        session = new SessionManager(getActivity());

        // Show Progress dialog
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        Log.d(TAG, "Periodic location updates started!");

        // First we need to check availability of play services
        if (checkPlayServices()) {

            // Building the GoogleApi client
            buildGoogleApiClient();
        }

        // Show location button click listener

        return view;

    }


    private void initViews(View view){

        etLatitude = (EditText) view.findViewById(R.id.et_latitude);
        etLongitude = (EditText) view.findViewById(R.id.et_longitude);
        etAlamat = (EditText) view.findViewById(R.id.et_alamat);
        etCatatan = (EditText) view.findViewById(R.id.et_catatan);

        rgPaket = (RadioGroup) view.findViewById(R.id.rg_paket);


/*      rbEkonomis = (RadioButton) view.findViewById(R.id.rb_ekonomis);
        rbReguler = (RadioButton) view.findViewById(R.id.rb_reguler);
        rbExpress = (RadioButton) view.findViewById(R.id.rb_express);*/

        // get selected radio button from radioGroup
      //  int selectedId = rgPaket.getCheckedRadioButtonId();

        rgPaket.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.rb_ekonomis:
                        // do operations specific to this selection
                        data_pemesanan_paket = "ekonomis";
                        break ;
                    case R.id.rb_reguler:
                        // do operations specific to this selection
                        data_pemesanan_paket = "reguler";
                        break;
                    case R.id.rb_express:
                        // do operations specific to this selection
                        data_pemesanan_paket = "express";
                        break;

                }
            }
        });

     //   Log.d(TAG,data_pemesanan_paket);
        // find the radio button by returned id
       // rButton = (RadioButton) view.findViewById(selectedId);

        cbBaju = (CheckBox) view.findViewById(R.id.cb_baju);
        cbCelana = (CheckBox) view.findViewById(R.id.cb_celana);
        cbRok = (CheckBox) view.findViewById(R.id.cb_rok);

       // pickerButton = (Button) view.findViewById(R.id.button_pilih_lokasi);
        btnPesan = (FloatingActionButton) view.findViewById(R.id.fab_pemesanan);


        etAlamat.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                displayLocation();
            }

        });


        btnPesan.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

            String pemesanan_latitude = etLatitude.getText().toString().trim();

            String pemesanan_longitude = etLongitude.getText().toString().trim();

            String pemesanan_alamat = etAlamat.getText().toString().trim();

            String pemesanan_catatan = etCatatan.getText().toString().trim();

            String pemesanan_paket = data_pemesanan_paket;
             //   Toast.makeText( getActivity(),rButton.getText(),Toast.LENGTH_SHORT).show();

            String pemesanan_baju = cbBaju.getText().toString().trim();

            String pemesanan_celana = cbCelana.getText().toString().trim();

            String pemesanan_rok = cbRok.getText().toString().trim();



            // data_pemesanan_paket = (String) rButton.getText();

            kirimPesanan(pemesanan_latitude, pemesanan_longitude, pemesanan_paket, pemesanan_alamat,
                         pemesanan_catatan, pemesanan_baju, pemesanan_celana, pemesanan_rok);
            }

        });

    }






    // Creating google api client object
    public synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    /**
     * Method to verify google play services on the device
     * */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(getActivity());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(getActivity(), resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported. Google Play Services not installed!");
                Toast.makeText(getActivity(), "This device is not supported. Google Play Services not installed!", Toast.LENGTH_LONG).show();

            }
            return false;
        }
        return true;

    }


    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        checkPlayServices();
    }


    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }
    /**
     * Google api callback methods
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }


    @Override
    public void onConnected(Bundle bundle) {

        if(mGoogleApiClient.isConnected()){
            Log.d(TAG,"Google_Api_Client: It was connected on (onConnected) function, working as it should.");
        }
        else{
            Log.d(TAG,"Google_Api_Client: It was NOT connected on (onConnected) function, It is definetly bugged.");
        }


        // Once connected with google api, get the location
     //   displayLocation();


    }


    /**
     * Method to display the location on UI
     * */
    private void displayLocation() {

        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
            double mlatitude = mLastLocation.getLatitude();
            double mlongitude = mLastLocation.getLongitude();

            Log.d("Location", mlatitude + ", " + mlongitude);


            try {
                //   PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                //   startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);



                MYLOCATION_VIEW = new LatLngBounds(
                        new LatLng(mlatitude, mlongitude), new LatLng(mlatitude, mlongitude));

              //  Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(getActivity());
                // startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);

                PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
                intentBuilder.setLatLngBounds(MYLOCATION_VIEW);

               Intent intent = intentBuilder.build(getActivity());
                startActivityForResult(intent, PLACE_PICKER_REQUEST);

                mRequestingLocationUpdates = false;

                // Stopping the location updates

                Log.d(TAG, "Periodic location updates stopped!");


            } catch (GooglePlayServicesRepairableException e) {
                Log.e(TAG, "GooglePlayServicesRepairableException", e);
            } catch (GooglePlayServicesNotAvailableException e) {
                Log.e(TAG, "GooglePlayServicesNotAvailableException", e);
            }

        } else {

            Toast.makeText(getActivity(), "Couldn't get the location. Make sure location is enabled on the device",Toast.LENGTH_SHORT).show();
                }
    }




    public void GPSTracker(Context context) {
        this.mContext = context;
        getLocation();
    }


    /**
     * Function to get latitude
     * */
    public double getLatitude() {
        if (location != null) {
            mlatitude = location.getLatitude();
        }

        // return latitude
        return mlatitude;
    }

    public double getLongitude() {
        if (location != null) {
            mlongitude = location.getLongitude();
        }

        // return longitude
        return mlongitude;
    }

    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    public void getLocation() {
        // TODO Auto-generated method stub
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
            mlatitude = mLastLocation.getLatitude();
            mlongitude = mLastLocation.getLongitude();

        } else {
            System.out
                    .println("(Couldn't get the location. Make sure location is enabled on the device)");
        }
    }




    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode, Intent data) {

        if (requestCode == PLACE_PICKER_REQUEST
                && resultCode == Activity.RESULT_OK) {

            final Place place = PlacePicker.getPlace(getActivity(), data);
            final CharSequence name = place.getName();
            final CharSequence address = place.getAddress();
            Double latitudex = place.getLatLng().latitude;
            Double longitudex = place.getLatLng().longitude;
            String attributions = (String) place.getAttributions();
            if (attributions == null) {
                attributions = "";
            }

            etLatitude.setText(latitudex.toString());
            etLongitude.setText( longitudex.toString());
            etCatatan.setText(name);
            etAlamat.setText(address);


        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
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
     * Melakukan pemesanan dengan mengirimkan param :

     * @param pemesanan_latitude
     * @param pemesanan_longitude
     * @param pemesanan_paket
     * @param pemesanan_alamat
     * @param pemesanan_catatan
     * @param pemesanan_baju
     * @param pemesanan_celana
     * @param pemesanan_rok
     */

    private void kirimPesanan(final String pemesanan_latitude, final String pemesanan_longitude,
                               final String pemesanan_paket, final String pemesanan_alamat,
                               final String pemesanan_catatan, final String pemesanan_baju,
                               final String pemesanan_celana, final String pemesanan_rok){
        // Tag used to cancel the request
        String tag_string_req = "req_profile";

        pDialog.setMessage(getString(R.string.mengupdate_profile));
        showDialog();

        Log.d("URL : ",AppConfig.URL_PEMESANAN);
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_PEMESANAN,
                new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Update profile Response : " + response);
                hideDialog();

                try {

                    JSONObject jObj = new JSONObject(response);

                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // Now store the user in SQLite

                        try {
                            parseDataPemesanan(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.d(Constants.TAG,jObj.getString("message"));

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("message");

                        Log.d(Constants.TAG,errorMsg);

                        if(getView()!=null) {
                            Snackbar.make(getView(), R.string.update_profile_gagal, Snackbar.LENGTH_LONG).show();
                        }

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();

                    Toast.makeText(getActivity(), getString(R.string.json_request_error) + e.getMessage(), Toast.LENGTH_LONG).show();

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                String e = VolleyErrorHelper.getMessage(error, getActivity());

                VolleyLog.d(AppController.TAG, "Error: " + e);


                Toast.makeText(getActivity(),
                        e, Toast.LENGTH_LONG).show();

                Log.d(Constants.TAG,"failed");
                //   progress.setVisibility(View.GONE);

                hideDialog();
            }
        }) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type","application/x-www-form-urlencoded");
                headers.put("Authorization", db.getUserApi() );

                Log.d("Params",headers.toString());
                return headers;
            }


            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to change password
                Map<String, String> params = new HashMap<>();
                params.put("pemesanan_latitude", pemesanan_latitude);
                params.put("pemesanan_longitude", pemesanan_longitude);
                params.put("pemesanan_alamat", pemesanan_alamat);
                params.put("pemesanan_paket", pemesanan_paket);
                params.put("pemesanan_catatan", pemesanan_catatan);
                params.put("pemesanan_baju", pemesanan_baju);
                params.put("pemesanan_celana", pemesanan_celana);
                params.put("pemesanan_rok", pemesanan_rok);

                Log.d("Params",params.toString());

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }



private void  parseDataPemesanan(String response) throws JSONException {

        JSONObject jObj = new JSONObject(response);
        Log.d("pemesanan response: ",jObj.getString("message"));

        boolean error = jObj.getBoolean("error");

        // Check for error node in json
        if (!error) {
            // Now store the user in SQLite

            JSONObject pemesanan = jObj.getJSONObject("pemesanan");
            String data_pemesanan_latitude = pemesanan.getString("pemesanan_latitude");
            String data_pemesanan_longitude = pemesanan.getString("pemesanan_longitude");
            String data_pemesanan_alamat = pemesanan.getString("pemesanan_alamat");
            String data_pemesanan_paket = pemesanan.getString("pemesanan_paket");
            String data_pemesanan_catatan = pemesanan.getString("pemesanan_catatan");
            String data_pemesanan_baju = pemesanan.getString("pemesanan_baju");
            String data_pemesanan_celana = pemesanan.getString("pemesanan_celana");
            String data_pemesanan_rok = pemesanan.getString("pemesanan_rok");

            // Inserting row in users table
            //   db.updatePassword(api, uid, created_at);

/*                        if (db.updatePemesanan(data_pemesanan_latitude, data_pemesanan_longitude,
                                data_pemesanan_alamat, data_pemesanan_paket, data_pemesanan_catatan,
                                data_pemesanan_baju, data_pemesanan_celana, data_pemesanan_rok)!=0) {


                          //  updateDataProfile();

                            if(getView()!=null) {
                                Snackbar.make(getView(), R.string.update_profile_berhasil, Snackbar.LENGTH_LONG).show();
                            }
                        //    disableView();

                        } else {
                            if(getView()!=null) {
                                Snackbar.make(getView(), R.string.update_profile_gagal, Snackbar.LENGTH_LONG).show();
                            }

                            Log.d(TAG,getString(R.string.update_profile_gagal));
                        }*/

            if(getView()!=null) {
                Snackbar.make(getView(), R.string.pemesanan_berhasil, Snackbar.LENGTH_LONG).show();
            }


            Log.d("pemesanan response : ",pemesanan.toString());

        } else {
            // Error in login. Get the error message
            String errorMsg = jObj.getString("message");

            Log.d(Constants.TAG,errorMsg);

            if(getView()!=null) {
                Snackbar.make(getView(), R.string.pemesanan_gagal, Snackbar.LENGTH_LONG).show();
            }

        }

}




    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
        pDialog.setMessage(getString(R.string.sedang_memuat));
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


}
