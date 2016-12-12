package com.yuly.elaundry.kurir.controller.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

import com.android.volley.toolbox.StringRequest;
import com.yuly.elaundry.kurir.R;
import com.yuly.elaundry.kurir.controller.app.AppConfig;
import com.yuly.elaundry.kurir.controller.app.AppController;
import com.yuly.elaundry.kurir.controller.fragment.DownloadPetaFragment;
import com.yuly.elaundry.kurir.model.database.KurirDbHandler;
import com.yuly.elaundry.kurir.model.geterseter.TransaksiModel;
import com.yuly.elaundry.kurir.model.helper.VolleyErrorHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class DetailPemesananActivity extends AppCompatActivity {

    // SqLite database handler
    private KurirDbHandler db;

    private TextView tvNama, tvAlamat,tvNoHp, tvPaket,tvHarga,tvBaju,tvCelana,tvRok,tvTanggal,tvStatus;

    private Button btnEdit, btnSave;

    private ProgressDialog pDialog;
    private String id_pemesanan, text_tombol,text_update_status, text_status_sebelumnya, pem_latitude, pem_longitude;
    private View parentLayout;

    private Button btnAmbil;

    private static final String TAG = DetailPemesananActivity.class.getSimpleName();

    TransaksiModel transaksiModel = new TransaksiModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pemesanan_new);

        //http://stackoverflow.com/questions/4486034/get-root-view-from-current-activity/4488149#4488149
        parentLayout = findViewById(android.R.id.content);

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

        btnAmbil = (Button) findViewById(R.id.btn_ambil);



        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            // return up one level
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Thema
        themeUtils.onActivityCreateSetTheme(this,getSupportActionBar(),this);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Memuat...");
        pDialog.setCancelable(false);

        // SqLite database handler
        db = new KurirDbHandler(getApplicationContext());

        id_pemesanan = getIntent().getStringExtra("PEMESANAN_ID");
        text_tombol = getIntent().getStringExtra("TEXT_TOMBOL");
        pem_latitude = getIntent().getStringExtra("PEMESANAN_LATITUDE");
        pem_longitude = getIntent().getStringExtra("PEMESANAN_LONGITUDE");
        text_status_sebelumnya = getIntent().getStringExtra("STATUS_SEBELUMNYA");
        text_update_status = getIntent().getStringExtra("UPDATE_STATUS");

        btnAmbil.setText(text_tombol);

        //Toast.makeText(this, id_pemesanan + " id dari list pemesanan", Toast.LENGTH_SHORT).show();

        requestDetail();

        btnAmbil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updatePemesanan(text_update_status);
            }
        });

        FloatingActionButton mFab = (FloatingActionButton)findViewById(R.id.fab_lokasi);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!MendownloadPeta.getMendownloadPeta().checkFilePetaAda()){

                    //MendownloadPeta.getMendownloadPeta().dialogDownloadPeta();

                    panggilDialogDownload();

                } else {

                    Intent intentPeta = new Intent(getApplication(), DetailPetaRuteActivity.class);

                    intentPeta.putExtra("PESANAN_LATITUDE", pem_latitude);
                    intentPeta.putExtra("PESANAN_LONGITUDE", pem_longitude);

                    startActivity(intentPeta);
                }

            }
        });
    }



    private void panggilDialogDownload(){

        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment frag = fragmentManager.findFragmentByTag("download_dialog");

        if (frag != null) {
            fragmentManager.beginTransaction().remove(frag).commit();
        }

        DownloadPetaFragment alertDialogFragment = new DownloadPetaFragment();
        alertDialogFragment.show(fragmentManager, "download_dialog");

    }


    /**
     * Making json array request
     */
    private void requestDetail() {
        showProgressDialog();


        // Posting parameters to change password
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("pemesanan_status", text_status_sebelumnya);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                AppConfig.URL_PEMESANAN,new JSONObject(params),
                new Response.Listener<JSONObject>() {

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

                            tvNama.setText(pemesanan_nama);
                            tvAlamat.setText(pemesanan_alamat);
                            tvNoHp.setText(pemesanan_nohp);
                            tvPaket.setText(pemesanan_paket);
                            tvHarga.setText(pemesanan_harga);
                            tvBaju.setText(pemesanan_baju);
                            tvCelana.setText(pemesanan_celana);
                            tvRok.setText(pemesanan_rok);
                            tvTanggal.setText(pemesanan_tanggal);
                            tvStatus.setText(pemesanan_status);


                            transaksiModel.setLatitude(pemesanan_latitude);
                            transaksiModel.setLongitude(pemesanan_longitude);

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



    private void updatePemesanan( final String pemesanan_status){

        // Tag used to cancel the request
        String tag_string_req = "req_profile";

        pDialog.setMessage(getString(R.string.mengupdate_profile));
        showProgressDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_UPDATE_PEMESANAN+id_pemesanan, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Update pemesanan Response : " + response);
                hideProgressDialog();

                try {

                    JSONObject jObj = new JSONObject(response);

                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {


                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("message");

                        Log.d(AppController.TAG,errorMsg);

                        Snackbar.make(parentLayout, errorMsg, Snackbar.LENGTH_LONG).show();


                        Handler myHandler = new Handler();
                        myHandler.postDelayed(mMyRunnable, 5000);//Message will be delivered in 5 second.


                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("message");

                        Log.d(AppController.TAG,errorMsg);

                            Snackbar.make(parentLayout, errorMsg, Snackbar.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();

                    Toast.makeText(getApplicationContext(), getString(R.string.json_request_error) + e.getMessage(), Toast.LENGTH_LONG).show();

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                String e = VolleyErrorHelper.getMessage(error, getApplicationContext());

                VolleyLog.d(AppController.TAG, "Error: " + e);


                Toast.makeText(getApplicationContext(),
                        e, Toast.LENGTH_LONG).show();

                Log.d(AppController.TAG,"failed");
                //   progress.setVisibility(View.GONE);

                hideProgressDialog();
            }
        }) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                params.put("Authorization", db.getUserApi() );

                Log.d("Params",params.toString());
                return params;
            }


            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to change password
                Map<String, String> params = new HashMap<>();
                params.put("pemesanan_status", pemesanan_status);

                Log.d("Params",params.toString());

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    //http://stackoverflow.com/questions/4199191/how-to-set-delay-in-android-onclick-function
    //Here's a runnable/handler combo
    private Runnable mMyRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            //Change state here

            finish();
        }
    };


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
