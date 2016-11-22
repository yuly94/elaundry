package com.yuly.elaundry.kurir.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import com.yuly.elaundry.R;
import com.yuly.elaundry.kurir.app.AppConfig;
import com.yuly.elaundry.kurir.app.AppController;
import com.yuly.elaundry.kurir.helper.SQLiteHandler;
import com.yuly.elaundry.kurir.helper.SessionManager;
import com.yuly.elaundry.kurir.helper.VolleyErrorHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PemesananActivity extends AppCompatActivity implements AppCompatSpinner.OnItemSelectedListener{

        //Declaring an Spinner
        private AppCompatSpinner spinnerPaket;

        //Declaring an Spinner
        private AppCompatSpinner spinnerTempat;

        //An ArrayList for Spinner Items
        private ArrayList<String> paketList;

        //An ArrayList for Spinner Items
        private ArrayList<String> tempatList;

        //JSON Array
        private JSONArray resultPaket;

        //JSON Array
        private JSONArray resultTempat;

        //TextViews to display details
        private TextView textViewHarga;
        private TextView textViewKeterangan;

        //TextViews to display details
        private TextView textViewTempat;
        private TextView textViewAlamat;

        private SQLiteHandler db;
        private SessionManager session;

        private ProgressDialog pDialog;

        private String konsumen_kunci_api;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_pesanan);
            //Initializing the ArrayList
            paketList = new ArrayList<String>();
            //Initializing the ArrayList
            tempatList = new ArrayList<String>();
            //Initializing Spinner
            spinnerPaket = (AppCompatSpinner) findViewById(R.id.spinner_paket);
            //Initializing Spinner
            spinnerTempat = (AppCompatSpinner) findViewById(R.id.spinner_tempat);
            //Adding an Item Selected Listener to our Spinner
            //As we have implemented the class Spinner.OnItemSelectedListener to this class iteself we are passing this to setOnItemSelectedListener
            spinnerPaket.setOnItemSelectedListener(this);
            //Initializing TextViews
            textViewHarga = (TextView) findViewById(R.id.view_harga_paket);
            textViewKeterangan = (TextView) findViewById(R.id.view_keterangan_paket);
            //Initializing TextViews
            textViewTempat = (TextView) findViewById(R.id.view_tempat);
            textViewAlamat = (TextView) findViewById(R.id.view_alamat);
            // SqLite database handler
            db = new SQLiteHandler(getApplicationContext());
            // session manager
            session = new SessionManager(getApplicationContext());
            // Fetching user details from SQLite
            HashMap<String, String> user = db.getUserDetails();
            // Get apikey from DB
            konsumen_kunci_api = user.get("konsumen_kunci_api");
            // Show Progress dialog
            pDialog = new ProgressDialog(this);
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);

            //This method will fetch the data from the URL

        makePaketJsonArryReq();
        makeTempatJsonArryReq();
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


                String e = VolleyErrorHelper.getMessage(error, getApplicationContext());
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
                headers.put("Authorization", konsumen_kunci_api);
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
     * Making json array request
     */
    private void makeTempatJsonArryReq() {
        showProgressDialog();
        JsonArrayRequest req = new JsonArrayRequest(AppConfig.URL_TEMPAT,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.d(AppController.TAG, "on Response " + response.toString());

                        //   msgResponse.setText(response.toString());
                        hideProgressDialog();

                        //Storing the Array of JSON String to our JSON Array
                         resultTempat = response;

                        //Calling method getPaketList to get the paketList from the JSON Array
                        getTempatList(resultTempat);

                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


                String e = VolleyErrorHelper.getMessage(error, getApplicationContext());
                VolleyLog.d(AppController.TAG, "Error: " + e);

                //  hideProgressDialog();
            }
        }) {

            /**
             * Passing some request headers
             * */

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", konsumen_kunci_api);
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
                    Toast.makeText(getApplicationContext(),
                            errorMsg, Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        //Setting adapter to show the items in the spinnerPaket
        spinnerPaket.setAdapter(new ArrayAdapter<String>(PemesananActivity.this, android.R.layout.simple_spinner_dropdown_item, paketList));
    }

    private void getTempatList(JSONArray j){
        //Traversing through all the items in the json array
        for(int i=0;i<j.length();i++){
            try {

                JSONObject jObj = j.getJSONObject(i);

                boolean error = jObj.getBoolean("error");
                if (!error) {

                    JSONObject tempat = jObj.getJSONObject("alamat");

                    String id = tempat.getString("id");
                    String nama = tempat.getString("nama");
                    String alamat = tempat.getString("alamat");
                    String kota = tempat.getString("kota");
                    String provinsi = tempat.getString("provinsi");
                    String latitude = tempat.getString("latitude");
                    String longitude = tempat.getString("longitude");
                    String created = tempat.getString("created");

                    String updated = tempat.getString("updated");

                    Log.d(AppController.TAG, "id : " + id);
                    Log.d(AppController.TAG, "nama : " + nama);
                    Log.d(AppController.TAG, "alamat : " + alamat);
                    Log.d(AppController.TAG, "kota : " + kota);
                    Log.d(AppController.TAG, "provinsi : " + provinsi);
                    Log.d(AppController.TAG, "latitude : " + latitude);
                    Log.d(AppController.TAG, "longitude : " + longitude);
                    Log.d(AppController.TAG, "creates : " + created);
                    Log.d(AppController.TAG, "updated : " + updated);


                    tempatList.add(nama);

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

        //Setting adapter to show the items in the spinnerPaket
        spinnerTempat.setAdapter(new ArrayAdapter<String>(PemesananActivity.this, android.R.layout.simple_spinner_dropdown_item, tempatList));
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



    //Method to get student name of a particular position
    private String getTempat(int position){
        String tempat = null;
        try {
            //Getting object of given index
            JSONObject json = resultTempat.getJSONObject(position);

            JSONObject place = json.getJSONObject("tempat");

            //Fetching name from that object
            tempat = place.getString("nama");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return tempat;
    }

    //Doing the same with this method as we did with getHarga()
    private String getAlamat(int position){
        String alamat=null;
        try {
            //Getting object of given index
            JSONObject json = resultTempat.getJSONObject(position);

            JSONObject place = json.getJSONObject("tempat");

            //Fetching name from that object
            alamat = place.getString("alamat");


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return alamat;
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

        textViewTempat.setText(getTempat(position));
        textViewAlamat.setText(getAlamat(position));
    }

    //When no item is selected this method would execute
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        textViewHarga.setText("");
        textViewKeterangan.setText("");

        textViewTempat.setText("");
        textViewAlamat.setText("");

    }


}