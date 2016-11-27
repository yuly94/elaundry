package com.yuly.elaundry.konsumen.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yuly.elaundry.konsumen.R;
import com.yuly.elaundry.konsumen.app.AppConfig;
import com.yuly.elaundry.konsumen.app.AppController;
import com.yuly.elaundry.konsumen.helper.KonsumenDbHandler;
import com.yuly.elaundry.konsumen.helper.VolleyErrorHelper;
import com.yuly.elaundry.konsumen.models.TransaksiModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class DetailPemesananActivityOld extends AppCompatActivity {

    // SqLite database handler
    private KonsumenDbHandler db;

    private TextView etPaket,etHarga,etAlamat,etTanggal;

    private Button btnEdit, btnSave;

    private ProgressDialog pDialog;
    private String id_pemesanan;

    private static final String TAG = DetailPemesananActivityOld.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pemesanan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        etPaket = (TextView) findViewById(R.id.et_paket);
        etHarga = (TextView) findViewById(R.id.et_harga);
        etAlamat = (TextView) findViewById(R.id.et_alamat);
        etTanggal = (TextView) findViewById(R.id.et_tanggal);
        btnEdit = (Button) findViewById(R.id.btn_edit);
        btnSave = (Button) findViewById(R.id.btn_save);

        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            // return up one level
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        // SqLite database handler
        db = new KonsumenDbHandler(getApplicationContext());

        id_pemesanan = getIntent().getStringExtra("EXTRA_SESSION_ID");

        //Toast.makeText(this, id_pemesanan + " id dari list pemesanan", Toast.LENGTH_SHORT).show();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        disableView();
        requestDetail();
    }


    private void disableView(){
        etPaket.setEnabled(false);
        etAlamat.setEnabled(false);
        etHarga.setEnabled(false);
        etTanggal.setEnabled(false);
        btnSave.setEnabled(false);
        btnEdit.setEnabled(true);

    }
    /**
     * Making json array request
     */
    private void requestDetail() {
        showProgressDialog();


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                AppConfig.URL_PEMESANAN_DETAIL+id_pemesanan, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());


                Log.d(AppController.TAG, "on Response " + response.toString());
                //   msgResponse.setText(response.toString());
                hideProgressDialog();


                try {
                    String error = response.getString("error");
                    if (error == "false") {
                        JSONArray obj = response.getJSONArray("pemesanan");

                        for (int i = 0; i < obj.length(); i++) {
                            TransaksiModel listPesanan = new TransaksiModel();

                            JSONObject jObj = obj.getJSONObject(i);

                            Log.d("TAG", jObj.toString());

                            String pemesanan_no = jObj.getString("pemesanan_no");
                            String pemesanan_id = jObj.getString("pemesanan_id");
                            String konsumen_id = jObj.getString("konsumen_id");
                            String pemesanan_latitude = jObj.getString("pemesanan_latitude");
                            String pemesanan_longitude = jObj.getString("pemesanan_longitude");
                            String pemesanan_alamat = jObj.getString("pemesanan_alamat");
                            String pemesanan_catatan = jObj.getString("pemesanan_catatan");
                            String pemesanan_paket = jObj.getString("pemesanan_paket");
                            String pemesanan_baju = jObj.getString("pemesanan_baju");
                            String pemesanan_celana = jObj.getString("pemesanan_celana");
                            String pemesanan_rok = jObj.getString("pemesanan_rok");
                            String pemesanan_harga = jObj.getString("pemesanan_harga");
                            String pemesanan_tanggal = jObj.getString("pemesanan_tanggal");
                            String pemesanan_status = jObj.getString("pemesanan_status");


                            Log.d(AppController.TAG, "id : " + pemesanan_id);
                            Log.d(AppController.TAG, "nama : " + konsumen_id);

                            Log.d(AppController.TAG, "kota : " + pemesanan_latitude);
                            Log.d(AppController.TAG, "provinsi : " + pemesanan_longitude);
                            Log.d(AppController.TAG, "latitude : " + pemesanan_alamat);
                            Log.d(AppController.TAG, "latitude : " + pemesanan_catatan);
                            Log.d(AppController.TAG, "longitude : " + pemesanan_paket);
                            Log.d(AppController.TAG, "creates : " + pemesanan_baju);
                            Log.d(AppController.TAG, "creates : " + pemesanan_celana);
                            Log.d(AppController.TAG, "creates : " + pemesanan_rok);
                            Log.d(AppController.TAG, "creates : " + pemesanan_harga);
                            Log.d(AppController.TAG, "creates : " + pemesanan_tanggal);
                            Log.d(AppController.TAG, "creates : " + pemesanan_status);



                            etPaket.setText(pemesanan_paket);
                            etHarga.setText(pemesanan_harga);
                            etAlamat.setText(pemesanan_alamat);
                            etTanggal.setText(pemesanan_tanggal);

                        }


                    }
                    else {

                        JSONArray message = response.getJSONArray("message");

                        Toast.makeText(getApplicationContext(), message.toString(), Toast.LENGTH_SHORT).show();

                    }


                }
                catch (JSONException e) {

                    e.printStackTrace();
                }

            }


        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


                String e = VolleyErrorHelper.getMessage(error, getApplicationContext());
                VolleyLog.d(AppController.TAG, "Error: " + e);

                hideProgressDialog();

                Toast.makeText(getApplicationContext(),
                        e, Toast.LENGTH_LONG).show();
            }
        }) {

            /**
             * Passing some request headers
             */


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", db.getUserApi()); //"a72d24f68128083f2904c6ce38fa232f97f2cab1");//"ef9bd71d1a704132aa366d1aa870f8be1faa44db");
                return headers;
            }


        };

        // Adding request to request queue
        String tag_json_arry = "jarray_req";
        AppController.getInstance().addToRequestQueue(jsonObjReq , tag_json_arry);


    }


    private void showProgressDialog() {
        if (!pDialog.isShowing()){
            pDialog.show();
        }

        Log.d("Show Progress", "here");

    }

    private void hideProgressDialog() {
        if (pDialog.isShowing()) {
            pDialog.hide();
        }
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
}
