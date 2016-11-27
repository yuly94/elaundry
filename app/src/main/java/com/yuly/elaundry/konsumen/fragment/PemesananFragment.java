package com.yuly.elaundry.konsumen.fragment;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
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
import com.yuly.elaundry.konsumen.R;
import com.yuly.elaundry.konsumen.app.AppConfig;
import com.yuly.elaundry.konsumen.app.AppController;
import com.yuly.elaundry.konsumen.app.Constants;
import com.yuly.elaundry.konsumen.helper.KonsumenDbHandler;
import com.yuly.elaundry.konsumen.helper.VolleyErrorHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PemesananFragment extends Fragment implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    private Context mContext;

    // LogCat tag
    private static final String TAG = PemesananFragment.class.getSimpleName();

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    //private LocationRequest mLocationRequest;
    private Location mLastLocation;
    private Location location; // location
    double mlatitude; // latitude
    double mlongitude; // longitude
    protected LocationManager locationManager;
    boolean canGetLocation = false;

    private FloatingActionButton btnPesan;
    private EditText etLatitude, etLongitude,etCatatan, etAlamat, etBaju,etCelana,etRok;
    private TextView tvHgBaju,tvHgCelana,tvHgRok, tvHgTotal, tvTitleHg;
    private RadioGroup rgPaket;
    private CheckBox cbBaju, cbCelana, cbRok;

    private String data_pemesanan_paket;
    private int HargaBaju,HargaCelana,HargaRok,varHargaBaju,varHargaCelana,varHargaRok;



    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    // boolean flag to toggle periodic location updates
    private boolean mRequestingLocationUpdates = false;

    private static final int PLACE_PICKER_REQUEST = 1;
//    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 10000; // 10 sec
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 10; // 10 meters




    //Database
    private KonsumenDbHandler db;

    private ProgressDialog pDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragmen_pemesanan, container,
                false);

        tvHgTotal = (TextView) view.findViewById(R.id.tv_hg_total);

        initViews(view);

        HargaBaju = 500;
        HargaCelana = 1000;
        HargaRok = 750;

        varHargaBaju = 0;
        varHargaCelana = 0;
        varHargaRok = 0;

        // SqLite database handler
        db = new KonsumenDbHandler(getActivity());

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

        tvTitleHg = (TextView) view.findViewById(R.id.tv_title_hg_total);

        //default sebelum user mengklik paket yg lain
        data_pemesanan_paket = "ekonomis";



        rgPaket.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.rb_ekonomis:
                        // do operations specific to this selection
                        data_pemesanan_paket = "ekonomis";
                        varHargaBaju = 2;
                        varHargaCelana = 2;
                        varHargaRok = 2;

                        hitHgBaju();
                        hitHgCelana();
                        hitHgBRok();

                        hitHgTotal();

                        break ;
                    case R.id.rb_reguler:
                        // do operations specific to this selection
                        data_pemesanan_paket = "reguler";
                        varHargaBaju = 3;
                        varHargaCelana = 3;
                        varHargaRok = 3;

                        hitHgBaju();
                        hitHgCelana();
                        hitHgBRok();

                        hitHgTotal();

                        break;
                    case R.id.rb_express:
                        // do operations specific to this selection
                        data_pemesanan_paket = "express";
                        varHargaBaju = 4;
                        varHargaCelana = 4;
                        varHargaRok = 4;

                        hitHgBaju();
                        hitHgCelana();
                        hitHgBRok();

                        hitHgTotal();

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

        etBaju = (EditText) view.findViewById(R.id.et_baju);
        etCelana = (EditText) view.findViewById(R.id.et_celana);
        etRok = (EditText) view.findViewById(R.id.et_rok);

        tvHgBaju = (TextView) view.findViewById(R.id.tv_hg_baju);
        tvHgCelana = (TextView) view.findViewById(R.id.tv_hg_celana);
        tvHgRok = (TextView) view.findViewById(R.id.tv_hg_rok);



        //disable check box secara default sebelum di chek
        disableEtBaju();
        disableEtCelana();
        disableEtRok();

        cbBaju.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // enable editing
                    etBaju.setFocusable(true);
                    etBaju.setFocusableInTouchMode(true); // user touches widget on phone with touch screen
                    etBaju.setClickable(true); // user navigates with wheel and selects widget

                   // hitHgBaju();

                    hitHgTotal();

                } else {
                    // disable editing
                    disableEtBaju();

                    hitHgTotal();
                }

            }
        });

        cbCelana.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // enable editing
                    etCelana.setFocusable(true);
                    etCelana.setFocusableInTouchMode(true); // user touches widget on phone with touch screen
                    etCelana.setClickable(true); // user navigates with wheel and selects widget

                  //  hitHgCelana();

                    hitHgTotal();

                } else {
                    // disable editing
                    disableEtCelana();

                    hitHgTotal();

                }

            }
        });


        cbRok.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // enable editing
                    etRok.setFocusable(true);
                    etRok.setFocusableInTouchMode(true); // user touches widget on phone with touch screen
                    etRok.setClickable(true); // user navigates with wheel and selects widget

                  //  hitHgBRok();

                    hitHgTotal();

                } else {
                    // disable editing
                    disableEtRok();

                    hitHgTotal();
                }

            }
        });


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

            int pemesanan_baju = Integer.parseInt(etBaju.getText().toString().trim());

            int pemesanan_celana = Integer.parseInt(etCelana.getText().toString().trim());

            int pemesanan_rok = Integer.parseInt(etRok.getText().toString().trim());

            int pemesanan_harga = Integer.parseInt(tvHgTotal.getText().toString().trim());

                int pemesanan_jumlah = (pemesanan_baju + pemesanan_celana + pemesanan_rok);

                // Apibila isian alamat atau jumlah pesanan tidak kosong
                if (!pemesanan_alamat.isEmpty() && (pemesanan_jumlah > 0)) {
                    // memanggil fungsi kirimPesanan
                    kirimPesanan(pemesanan_latitude, pemesanan_longitude, pemesanan_paket, pemesanan_alamat,
                            pemesanan_catatan, String.valueOf(pemesanan_baju), String.valueOf(pemesanan_celana), String.valueOf(pemesanan_rok), String.valueOf(pemesanan_harga));
                } else {
                    // Memberitahu konsumen untuk memasukkan password
                    Toast.makeText(getActivity(),
                            "Mohon masukkan alamat atau jumlah pemesanan ada", Toast.LENGTH_LONG)
                            .show();
                }


            }

        });


        etBaju.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0)
                   hitHgTotal();
            }
        });

        etCelana.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0)
                    hitHgTotal();
            }
        });

        etRok.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0)
                    hitHgTotal();
            }
        });

    }

    private  void disableEtBaju() {
        etBaju.setFocusable(false);
        etBaju.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
        etBaju.setClickable(false); // user navigates with wheel and selects widget
        etBaju.setText("0");

    }

    private void hitHgBaju(){
        int hgBaju = (HargaBaju * varHargaBaju);
        tvHgBaju.setText(hgBaju+" / pcs");
    }

    private  void disableEtCelana() {
        etCelana.setFocusable(false);
        etCelana.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
        etCelana.setClickable(false); // user navigates with wheel and selects widget
        etCelana.setText("0");
    }

    private void hitHgCelana(){
        int hgCelana = (HargaCelana * varHargaCelana);
        tvHgCelana.setText(hgCelana+" / pcs");
    }



    private  void disableEtRok() {
        etRok.setFocusable(false);
        etRok.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
        etRok.setClickable(false); // user navigates with wheel and selects widget
        etRok.setText("0");

    }

    private void hitHgBRok(){
        int hgRok = (HargaRok * varHargaRok);
        tvHgRok.setText(hgRok+" / pcs");
    }

    private void hitHgTotal(){
        int jumBaju = Integer.parseInt(String.valueOf(etBaju.getText()));
        int jumCelana = Integer.parseInt(String.valueOf(etCelana.getText()));
        int jumRok = Integer.parseInt(String.valueOf(etRok.getText()));
        int hgTotal = (((HargaBaju * varHargaBaju)*jumBaju)+((HargaCelana * varHargaCelana)*jumCelana)+((HargaRok * varHargaRok)*jumRok));

        tvHgTotal.setText(String.valueOf(hgTotal));

        Log.d(TAG, String.valueOf(hgTotal));
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
    public void onConnectionFailed(@NonNull ConnectionResult result) {
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
                //  startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);


                LatLngBounds MYLOCATION_VIEW = new LatLngBounds(
                        new LatLng(mlatitude, mlongitude), new LatLng(mlatitude, mlongitude));

                //Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(getActivity());
                //startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);

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
            String data_latitude = String.valueOf(place.getLatLng().latitude);
            String data_longitude = String.valueOf(place.getLatLng().longitude);
            String attributions = (String) place.getAttributions();
            if (attributions == null) {
                attributions = "";
            }

            etCatatan.setText(name);
            etAlamat.setText(address);
            etLatitude.setText(data_latitude);
            etLongitude.setText( data_longitude);



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

     * @param pemesanan_latitude latitude
     * @param pemesanan_longitude longitude
     * @param pemesanan_paket paket laundry
     * @param pemesanan_alamat alamat penjemputan
     * @param pemesanan_catatan catatan bagi kurir
     * @param pemesanan_baju jumlah baju
     * @param pemesanan_celana jumlah celana
     * @param pemesanan_rok jumlah rok
     */

    private void kirimPesanan(final String pemesanan_latitude, final String pemesanan_longitude,
                               final String pemesanan_paket, final String pemesanan_alamat,
                               final String pemesanan_catatan, final String pemesanan_baju,
                               final String pemesanan_celana, final String pemesanan_rok, final String pemesanan_harga){
        // Tag used to cancel the request
        String tag_string_req = getString(R.string.permintaan_melakukan_pemesanan);

        pDialog.setMessage(getString(R.string.melakukan_pemesanan));
        showProgressDialog();

        Log.d("URL : ",AppConfig.URL_PEMESANAN);
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_PEMESANAN,
                new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response pemesanan: " + response);
                hideProgressDialog();

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
                            Snackbar.make(getView(), R.string.pemesanan_gagal, Snackbar.LENGTH_LONG).show();
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

                hideProgressDialog();
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
                params.put("pemesanan_harga", pemesanan_harga);


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
            String data_pemesanan_harga = pemesanan.getString("pemesanan_harga");

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





}
