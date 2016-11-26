package com.yuly.elaundry.kurir.controller.activity;

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

import com.yuly.elaundry.kurir.R;
import com.yuly.elaundry.kurir.controller.app.AppConfig;
import com.yuly.elaundry.kurir.controller.app.AppController;
import com.yuly.elaundry.kurir.model.database.KonsumenDbHandler;
import com.yuly.elaundry.kurir.model.helper.VolleyErrorHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class DetailPemesananActivity extends AppCompatActivity {

    // SqLite database handler
    private KonsumenDbHandler db;

    private TextView tvNama, tvAlamat,tvNoHp, tvPaket,tvHarga,tvBaju,tvCelana,tvRok,tvTanggal,tvStatus;

    private Button btnEdit, btnSave;

    private ProgressDialog pDialog;
    private String id_pemesanan;

    private static final String TAG = DetailPemesananActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pemesanan_new);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        tvNama = (TextView) findViewById(R.id.tv_nama);
        tvAlamat = (TextView) findViewById(R.id.tv_alamat);
        tvNoHp = (TextView) findViewById(R.id.tv_nohp);
        tvPaket = (TextView) findViewById(R.id.tv_paket);
        tvHarga = (TextView) findViewById(R.id.tv_harga);
        tvBaju = (TextView) findViewById(R.id.tv_baju);
        tvCelana = (TextView) findViewById(R.id.tv_celana);
        tvRok = (TextView) findViewById(R.id.tv_rok);
        tvTanggal = (TextView) findViewById(R.id.tv_tanggal);
        tvStatus = (TextView) findViewById(R.id.tv_status);



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

        requestDetail();
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

                            JSONObject jObj = obj.getJSONObject(i);

                            Log.d("TAG", jObj.toString());

                            String pemesanan_no = jObj.getString("pemesanan_no");
                            String pemesanan_id = jObj.getString("pemesanan_id");
                            String pemesanan_nama = jObj.getString("konsumen_nama");
                            String pemesanan_nohp = jObj.getString("konsumen_nohp");
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

                            tvPaket.setText(pemesanan_nama);
                            tvAlamat.setText(pemesanan_alamat);
                            tvNoHp.setText(pemesanan_nohp);
                            tvPaket.setText(pemesanan_paket);
                            tvHarga.setText(pemesanan_harga);
                            tvBaju.setText(pemesanan_baju);
                            tvCelana.setText(pemesanan_celana);
                            tvRok.setText(pemesanan_rok);
                            tvTanggal.setText(pemesanan_tanggal);
                            tvStatus.setText(pemesanan_status);

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
