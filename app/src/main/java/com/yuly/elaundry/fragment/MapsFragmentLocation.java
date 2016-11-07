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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import com.google.android.gms.location.LocationRequest;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MapsFragmentLocation extends Fragment  implements AdapterView.OnItemSelectedListener , GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private Context mContext;

    // LogCat tag
    private static final String TAG = MapsFragmentLocation.class.getSimpleName();

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private LocationRequest mLocationRequest;
    private Location mLastLocation;
    private Location location; // location
    double latitude; // latitude
    double longitude; // longitude
    private LatLngBounds MYLOCATION_VIEW;
    protected LocationManager locationManager;
    boolean canGetLocation = false;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    // boolean flag to toggle periodic location updates
    private boolean mRequestingLocationUpdates = false;

    private static final int PLACE_PICKER_REQUEST = 1;
    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 10000; // 10 sec
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 10; // 10 meters

    // UI elements

    private EditText mAddress,mName,tvNamaLokasi,tvAlamat;


    //Declaring an Spinner
    private AppCompatSpinner spinnerPaket;


    //An ArrayList for Spinner Items
    private ArrayList<String> paketList;


    //JSON Array
    private JSONArray resultPaket;

    //TextViews to display details
    private TextView textViewHarga;
    private TextView textViewKeterangan;

    //Database
    private SQLiteHandler db;
    private SessionManager session;

    private ProgressDialog pDialog;

    private String apiKey;
    private FloatingActionButton btnPesan;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragmen_pemesanan, container,
                false);

        mName = (EditText) v.findViewById(R.id.tv_alamat1);
        mAddress = (EditText) v.findViewById(R.id.tv_alamat2);

        Button pickerButton = (Button) v.findViewById(R.id.button_pilih_lokasi);

        //Initializing the ArrayList
        paketList = new ArrayList<String>();

        //Initializing Spinner
        spinnerPaket = (AppCompatSpinner) v.findViewById(R.id.spinner_harga);


        //Adding an Item Selected Listener to our Spinner
        //As we have implemented the class Spinner.OnItemSelectedListener to this class iteself we are passing this to setOnItemSelectedListener
        spinnerPaket.setOnItemSelectedListener(this);

        //Initializing TextViews
        tvNamaLokasi = (EditText) v.findViewById(R.id.tv_alamat1);
        tvAlamat = (EditText) v.findViewById(R.id.tv_alamat2);

        textViewHarga = (TextView) v.findViewById(R.id.tv_harga);
        textViewKeterangan = (TextView) v.findViewById(R.id.tv_keterangan);
        btnPesan = (FloatingActionButton) v.findViewById(R.id.fab_pemesanan);

        // SqLite database handler
        db = new SQLiteHandler(getActivity());
        // session manager
        session = new SessionManager(getActivity());
        // Fetching user details from SQLite
        HashMap<String, String> user = db.getUserDetails();
        // Get apikey from DB
        apiKey = user.get("api");
        // Show Progress dialog
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        //This method will fetch the data from the URL

        makePaketJsonArryReq();


        Log.d(TAG, "Periodic location updates started!");

        // First we need to check availability of play services
        if (checkPlayServices()) {

            // Building the GoogleApi client
            buildGoogleApiClient();
        }

        // Show location button click listener
        pickerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                displayLocation();
            }
        });


        btnPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // startActivity(new Intent(getActivity(), PemesananActivity.class));

                if(getView()!=null) {
                    Snackbar.make(getView(), R.string.update_profile_success, Snackbar.LENGTH_LONG).show();
                }


            }
        });


        return v;

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
            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();

            Log.d("Location", latitude + ", " + longitude);


            try {
                //   PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                //   startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);



                MYLOCATION_VIEW = new LatLngBounds(
                        new LatLng(latitude, longitude), new LatLng(latitude, longitude));

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
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }

    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
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
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();

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
            String attributions = (String) place.getAttributions();
            if (attributions == null) {
                attributions = "";
            }

            mName.setText(name);
            mAddress.setText(address);


        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    /**
     * Making json array request
     */
    private void makePaketJsonArryReq() {
        showProgressDialog();
        JsonArrayRequest req = new JsonArrayRequest(AppConfig.URL_PAKET,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.d(AppController.TAG, "on Response " + response.toString());

                        //   msgResponse.setText(response.toString());
                        hideProgressDialog();

                        //Storing the Array of JSON String to our JSON Array
                        resultPaket = response;

                        //Calling method getPaketList to get the paketList from the JSON Array
                        getPaketList(resultPaket);

                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


                String e = VolleyErrorHelper.getMessage(error, getActivity());
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



    private void getPaketList(JSONArray j){
        //Traversing through all the items in the json array
        for(int i=0;i<j.length();i++){
            try {

                JSONObject jObj = j.getJSONObject(i);

                boolean error = jObj.getBoolean("error");
                if (!error) {

                    JSONObject paket = jObj.getJSONObject("paket");

                    String id = paket.getString("id");
                    String nama = paket.getString("nama");
                    String harga = paket.getString("harga");
                    String keterangan = paket.getString("keterangan");
                    String status = paket.getString("status");

                    Log.d(AppController.TAG, "id : " + id);
                    Log.d(AppController.TAG, "nama : " + nama);
                    Log.d(AppController.TAG, "harga : " + harga);
                    Log.d(AppController.TAG, "keterangan : " + keterangan);
                    Log.d(AppController.TAG, "status : " + status);


                    paketList.add(nama);

                } else {

                    // Error occurred in registration. Get the error
                    // message
                    String errorMsg = jObj.getString("message");
                    Toast.makeText(getActivity(),
                            errorMsg, Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinnerPaket
        spinnerPaket.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, paketList));
    }



    //Method to get student name of a particular position
    private String getHarga(int position){
        String packet = null;
        try {
            //Getting object of given index
            JSONObject json = resultPaket.getJSONObject(position);

            JSONObject paket = json.getJSONObject("paket");

            //Fetching name from that object
            packet = paket.getString("harga");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return packet;
    }

    //Doing the same with this method as we did with getHarga()
    private String getKeterangan(int position){
        String keterangan=null;
        try {
            //Getting object of given index
            JSONObject json = resultPaket.getJSONObject(position);

            JSONObject paket = json.getJSONObject("paket");

            //Fetching name from that object
            keterangan = paket.getString("keterangan");


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return keterangan;
    }



    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();


    }




    //this method will execute when we pic an item from the spinnerPaket
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Setting the values to textviews for a selected item
        textViewHarga.setText(getHarga(position));
        textViewKeterangan.setText(getKeterangan(position));


    }

    //When no item is selected this method would execute
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        textViewHarga.setText("");
        textViewKeterangan.setText("");


    }



    private void lakukanPemesanan(final String nama,final String alamat,final String telepon,final String email){

        // Tag used to cancel the request
        String tag_string_req = "req_profile";

        pDialog.setMessage("Updating your profile, please wait ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_CHANGEPROFILE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response);
                hideDialog();

                try {

                    JSONObject jObj = new JSONObject(response);

                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // Now store the user in SQLite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String nama = user.getString("nama");
                        String alamat = user.getString("alamat");
                        String telepon = user.getString("nohp");
                        String email = user.getString("email");
                        String api = user.getString("api_key");
                        String created_at = user
                                .getString("created_at");

                        // Inserting row in users table
                        //   db.updatePassword(api, uid, created_at);

                        if (db.updateProfile(nama, alamat, telepon, email, uid, created_at)!=0) {

                            if(getView()!=null) {
                                Snackbar.make(getView(), R.string.update_profile_success, Snackbar.LENGTH_LONG).show();
                            }


                        } else {
                            Log.d(TAG,"update password failed, please try again");
                        }

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("message");

                        Log.d(Constants.TAG,errorMsg);

                        if(getView()!=null) {
                            Snackbar.make(getView(), R.string.update_profile_failed, Snackbar.LENGTH_LONG).show();
                        }

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();

                    Toast.makeText(getActivity(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();

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

                hideDialog();
            }
        }) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                params.put("Authorization", apiKey);

                Log.d("Params",params.toString());
                return params;
            }


            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to change password
                Map<String, String> params = new HashMap<String, String>();
                params.put("nama", nama);
                params.put("alamat", alamat);
                params.put("telepon", telepon);
                params.put("email",email);

                Log.d("Params",params.toString());

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }



    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
        pDialog.setMessage("Loading...");
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


}
