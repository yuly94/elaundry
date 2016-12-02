package com.yuly.elaundry.kurir.controller.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.routing.AlgorithmOptions;
import com.graphhopper.util.StopWatch;
import com.yuly.elaundry.kurir.R;
import com.yuly.elaundry.kurir.controller.app.AppConfig;
import com.yuly.elaundry.kurir.controller.app.AppController;
import com.yuly.elaundry.kurir.model.database.KurirDbHandler;
import com.yuly.elaundry.kurir.model.database.Lokasi;
import com.yuly.elaundry.kurir.model.database.RouteDbHelper;
import com.yuly.elaundry.kurir.model.geterseter.TransaksiModel;
import com.yuly.elaundry.kurir.model.helper.VolleyErrorHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RuteActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    private KurirDbHandler db_user;
    private RouteDbHelper db_rute;
    private GraphHopper hopper;
    private static final String TAG = RuteActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rute);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_satu);
        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab_dua);

        //http://stackoverflow.com/questions/1561803/android-progressdialog-show-crashes-with-getapplicationcontext
        pDialog = new ProgressDialog(RuteActivity.this);
        pDialog.setMessage(String.valueOf(R.string.memuat));
        pDialog.setCancelable(false);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                //bacaLokasi();

//                PetaActivity peta = new PetaActivity();
               // peta.calcPath( 112.0178286, 112.0178286,
               // 112.0287791, 112.0287791 );

                hitungJarak();

            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                mengambilDataPemesanan();
            }
        });




        // SqLite database handler
        db_user = new KurirDbHandler(getApplicationContext());
        // SqLite database handler
        db_rute = new RouteDbHelper(getApplicationContext());

    }



    private void hitungJarak(){
       calcPath( -7.848015599999999, 112.0178286,
               -7.817117399999998, 112.0287791 );
    }


        //

    public void calcPath( final double fromLat, final double fromLon,
                          final double toLat, final double toLon )
    {

        log("mengkalkulasi path ...");

        new AsyncTask<Void, Void, GHResponse>()
        {
            float time;

            protected GHResponse doInBackground( Void... v )
            {
                StopWatch sw = new StopWatch().start();

                GHRequest req = new GHRequest(fromLat, fromLon, toLat, toLon).
                        setAlgorithm(AlgorithmOptions.DIJKSTRA_BI);

                req.getHints().
                        put("instructions", "false");
                GHResponse resp = hopper.route(req);
                time = sw.stop().getSeconds();
                return resp;
            }

            protected void onPostExecute( GHResponse resp )
            {
                if (!resp.hasErrors())
                {
                    log("from:" + fromLat + "," + fromLon + " to:" + toLat + ","
                            + toLon + " found path with distance:" + resp.getDistance()
                            / 1000f + ", nodes:" + resp.getPoints().getSize() + ", time:"
                            + time + " " + resp.getDebugInfo());
                    logUser("the route is " + (int) (resp.getDistance() / 100) / 10f
                            + "km long, time:" + resp.getTime() / 60000f + "min, debug:" + time);

                //    mapView.getLayerManager().getLayers().add(createPolyline(resp));

                //    mapView.getLayerManager().getLayers().add(createPolyline(resp));

                    Log.d("Resp",resp.toString());





                    //mapView.getLayerManager().getLayers().add(createPolyline(resp));
                    //mapView.redraw();
                } else
                {
                    logUser("Error:" + resp.getErrors());
                }
               // shortestPathRunning = false;
            }
        }.execute();
    }



    private void bacaLokasi(){
        // Reading all lokasi konsumen
        Log.d("Reading: ", "Reading all lokasi..");
        List<Lokasi> contacts = db_rute.getAllLokasi();

        for (Lokasi lokasi : contacts) {
            String log = "Id: " + lokasi.getId() + " ,Latitude : " + lokasi.getLatitude() + " ,Longitude : " + lokasi.getLongitude();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }
    }

    /**
     * Mengambil data pemesanan baru
     */

    //http://stackoverflow.com/questions/28344448/how-to-send-json-object-to-server-using-volley-in-andorid
    private void mengambilDataPemesanan() {
       showProgressDialog();
        // mSwipeRefreshLayout.setRefreshing(true);



        // Posting parameters untuk mengambil data pemesanan
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("pemesanan_status", "baru memesan");

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                AppConfig.URL_PEMESANAN, new JSONObject(params),
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
                                    TransaksiModel listPesanan = new TransaksiModel();

                                    JSONObject jObj = obj.getJSONObject(i);

                                    Log.d("TAG", jObj.toString());

                                    String pemesanan_no = jObj.getString("pemesanan_no");
                                    String pemesanan_id = jObj.getString("pemesanan_id");
                                    String konsumen_id = jObj.getString("konsumen_id");
                                    String konsumen_nama = jObj.getString("konsumen_nama");
                                    String konsumen_nohp = jObj.getString("konsumen_nohp");
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

                                   // Log.i(AppController.TAG, "parsing berhasil");

                                    listPesanan.setNoId(pemesanan_id);
                                    listPesanan.setPemesananId(pemesanan_id);
                                    listPesanan.setNama(konsumen_nama);
                                    listPesanan.setNoHp(konsumen_nohp);
                                    listPesanan.setHarga(pemesanan_harga);
                                    listPesanan.setLatitude(pemesanan_latitude);
                                    listPesanan.setLongitude(pemesanan_longitude);
                                    listPesanan.setKonsumenId(konsumen_id);
                                    listPesanan.setAlamat(pemesanan_alamat);
                                    listPesanan.setTanggal(pemesanan_tanggal);

                                    Lokasi lokasi_konsumen = new Lokasi(listPesanan.getPemesananId(),String.valueOf(listPesanan.getLatitude()), String.valueOf(listPesanan.getLongitude()),"sada",1);

                                    long id = db_rute.createLokasiKonsumen(lokasi_konsumen);

                                    Log.d("ID", String.valueOf(id));

                                }

                                //updateList();
                            } else {

                                JSONArray message = response.getJSONArray("message");

                                Toast.makeText(getApplicationContext(), message.toString(), Toast.LENGTH_SHORT).show();

                            }


                        } catch (JSONException e) {

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
             * */

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", db_user.getUserApi()); //"a72d24f68128083f2904c6ce38fa232f97f2cab1");//"ef9bd71d1a704132aa366d1aa870f8be1faa44db");
                headers.put("User-agent", System.getProperty("http.agent"));
                return headers;
            }

        };

        // Adding request to request queue
        String tag_json_arry = "jobj_req";
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_arry);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_arry);
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

    private void log( String str )
    {
        Log.i("GH", str);
    }

    private void log( String str, Throwable t )
    {
        Log.i("GH", str, t);
    }

    private void logUser( String str )
    {
        log(str);
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }
}
