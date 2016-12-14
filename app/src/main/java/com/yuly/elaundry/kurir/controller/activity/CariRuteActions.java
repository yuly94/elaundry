package com.yuly.elaundry.kurir.controller.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;

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

import com.yuly.elaundry.kurir.model.database.KurirDbHandler;
import com.yuly.elaundry.kurir.model.database.Lokasi;
import com.yuly.elaundry.kurir.model.database.RouteDbHelper;

import com.yuly.elaundry.kurir.model.geterseter.TransaksiModel;
import com.yuly.elaundry.kurir.model.helper.VolleyErrorHelper;

import com.yuly.elaundry.kurir.model.peta.CariRuteHandler;

import com.yuly.elaundry.kurir.model.util.Variable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mapsforge.core.model.LatLong;
import org.mapsforge.map.android.view.MapView;

import org.mapsforge.map.model.MapViewPosition;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class CariRuteActions  {
    private Activity activity;
    protected FloatingActionButton showPositionBtn, tombolMenu;
    protected FloatingActionButton zoomInBtn, zoomOutBtn, fabNavigasi,fab_refresh, fab_dapatkan, fab_getpoint, fab_rute;
    private ViewGroup menuNavigasiPeta,  sideBarMenuVP;
    private boolean menuVisible;

    private ProgressDialog pDialog;

    private KurirDbHandler db_user;
    private RouteDbHelper db_rute;


    public CariRuteActions(AppCompatActivity activity, MapView mapView) {
        this.activity = activity;

        this.showPositionBtn = (FloatingActionButton) activity.findViewById(R.id.fab_location);

        this.tombolMenu = (FloatingActionButton) activity.findViewById(R.id.fab_menu);

        this.fab_rute = (FloatingActionButton) activity.findViewById(R.id.fab_rute);

        this.zoomInBtn = (FloatingActionButton) activity.findViewById(R.id.fab_besarkan);
        this.zoomOutBtn = (FloatingActionButton) activity.findViewById(R.id.fab_kecilkan);

        this.fabNavigasi = (FloatingActionButton) activity.findViewById(R.id.fab_navigasi);

        this.fab_getpoint = (FloatingActionButton) activity.findViewById(R.id.fab_getpoint);

        this.fab_refresh = (FloatingActionButton) activity.findViewById(R.id.fab_refresh);

        this.fab_dapatkan = (FloatingActionButton) activity.findViewById(R.id.fab_dapatkan);

       // view groups managed by separate layout xml file : //map_sidebar_layout/map_sidebar_menu_layout
        this.menuNavigasiPeta = (ViewGroup) activity.findViewById(R.id.menu_nafigasi_peta);
        this.sideBarMenuVP = (ViewGroup) activity.findViewById(R.id.group_tombol_navigasi);
        
        this.menuVisible = false;

        //http://stackoverflow.com/questions/1561803/android-progressdialog-show-crashes-with-getapplicationcontext

        // SqLite database handler
        db_user = new KurirDbHandler(this.activity);
        // SqLite database handler
        db_rute = new RouteDbHelper(this.activity);

        tombolMenuHandler();
        
        zoomControlHandler(mapView);
        showMyLocation(mapView);
        //navBtnHandler();

        mengambilData();
        //menghitungJarak();

        getPoint();

        fabProsesRute();
    }


    private void fabProsesRute(){

        fab_rute.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {

                membuatTable();

                memprosesRute();

            }

        });
    }


    private void membuatTable() {

        pDialog = new ProgressDialog(this.activity);
        pDialog.setMessage("memuat ....");
        pDialog.setCancelable(false);

        showProgressDialog();

        hapusSemuaJarak();
        hapusSemuaHistoryJarak();

        List<Lokasi> listlokasi = db_rute.getAllLokasi();
        // Reading all lokasi konsumen

        for(int i=1; i<= listlokasi.size() ;i++){

            for(int k=(i-1); k > 0 ;--k){

                Lokasi lokA = db_rute.getLokasi(i);
                // Writing Contacts to log
                Log.d("daftar lokasi dari : ",+lokA.getId() + " : "+lokA.getLatitude()+ " : "+ lokA.getLongitude());

                Lokasi lokB = db_rute.getLokasi(k);
                // Writing Contacts to log
                Log.d("daftar lokasi tujuan: ",+lokB.getId() + " : "+lokB.getLatitude()+ " : "+ lokB.getLongitude());


                String jarak = CariRuteHandler.getPetaHandler().menghitungJarak(Double.parseDouble(lokA.getLatitude()), Double.parseDouble(lokA.getLongitude()),
                        Double.parseDouble(lokB.getLatitude()),Double.parseDouble(lokB.getLongitude()));

                Log.d("Jarak lokasi :", jarak+" Meter");

                Lokasi jarak_konsumen_01 = new Lokasi( i,k, jarak, 1);

                long id_01 =  db_rute.buatJarakKonsumen(jarak_konsumen_01);

                db_rute.buatHistoryJarakKonsumen(jarak_konsumen_01);

                System.out.print("daftar id : "+id_01);
                System.out.print("X"+i);
                System.out.print("&"+k);
                System.out.println("");
            }

            for(int j=(i+1); j <= listlokasi.size() ;++j){


                Lokasi lokA = db_rute.getLokasi(i);
                // Writing Contacts to log
                Log.d("daftar lokasi dari : ",+lokA.getId() + " : "+lokA.getLatitude()+ " : "+ lokA.getLongitude());

                Lokasi lokB = db_rute.getLokasi(j);
                // Writing Contacts to log
                Log.d("daftar lokasi tujuan: ",+lokB.getId() + " : "+lokB.getLatitude()+ " : "+ lokB.getLongitude());


                String jarak = CariRuteHandler.getPetaHandler().menghitungJarak(Double.parseDouble(lokA.getLatitude()), Double.parseDouble(lokA.getLongitude()),
                        Double.parseDouble(lokB.getLatitude()),Double.parseDouble(lokB.getLongitude()));

                Log.d("Jarak lokasi :", jarak+" Meter");

                Lokasi jarak_konsumen_01 = new Lokasi( i,j, jarak, 1);

                long id_02 =  db_rute.buatJarakKonsumen(jarak_konsumen_01);

                db_rute.buatHistoryJarakKonsumen(jarak_konsumen_01);


                System.out.print("daftar id : "+id_02);

                System.out.print("#"+i);

                System.out.print("*"+j);
                System.out.println("");
            }
            //generate a new line
            // System.out.println();
        }

        Log.d("table jarak","tabel berhasil dibuat ");

        hideProgressDialog();

        Toast.makeText(activity, "tabel jarak berhasil dibuat", Toast.LENGTH_LONG).show();
    }

    private void memprosesRute(){

        hapusSemuaPath();

        //CariRuteHandler.getPetaHandler().hapusPath();

        List<Lokasi> listLokasi = db_rute.getAllLokasi();

      //  for(Lokasi daftarLokasi : listLokasi) {
        for(int i=0; i < (listLokasi.size()-1) ;i++){

           Log.d("Jalur id :",
                   String.valueOf(listLokasi.get(i).getId()));

            Log.d("Nilai I :",
                    String.valueOf(i));

            // Lokasi urutan = db_rute.getPath(dariJarak);
            Lokasi lastPath = db_rute.getLastPath();

            int dariJarak = (lastPath.getTujuan() < 1) ? 1 : lastPath.getTujuan();

            Log.d("Next Path :",
                    String.valueOf(dariJarak));

            Log.d("dari jarak", String.valueOf(dariJarak));


            Lokasi urutan = db_rute.getTujuan(dariJarak);

            Log.d("urutan :", "dari : "+ String.valueOf(urutan.getDari()) +
                    " tujuan : " + String.valueOf(urutan.getTujuan()));

            Lokasi lokasiDari = db_rute.getLokasi(urutan.getDari());
            Lokasi lokasiTujuan = db_rute.getLokasi(urutan.getTujuan());

            Log.d("Lokasi Dari : ", " latitude : " +lokasiDari.getLatitude() +" longitude : " +lokasiDari.getLongitude());
            Log.d("Lokasi Tujuan : "," latitude : " +lokasiTujuan.getLatitude() +" longitude : " +lokasiTujuan.getLongitude());

            String jarakLokasi = CariRuteHandler.getPetaHandler().menghitungJarak(Double.parseDouble(lokasiDari.getLatitude()), Double.parseDouble(lokasiDari.getLongitude()),
                    Double.parseDouble(lokasiTujuan.getLatitude()),Double.parseDouble(lokasiTujuan.getLongitude()));

            CariRuteHandler.getPetaHandler().calcPath(Double.parseDouble(lokasiDari.getLatitude()), Double.parseDouble(lokasiDari.getLongitude()),
                    Double.parseDouble(lokasiTujuan.getLatitude()),Double.parseDouble(lokasiTujuan.getLongitude()));

            Log.d("Jarak lokasi :", jarakLokasi+" Meter");

            Lokasi jarak_konsumen_01 = new Lokasi(urutan.getDari(), urutan.getTujuan(), jarakLokasi, 1);

            Toast.makeText(activity,"Jarak dari lokasi : "+ String.valueOf(urutan.getDari()) +
                    " ke lokasi : " + String.valueOf(urutan.getTujuan() +
                    " berjarak : " +jarakLokasi+" Meter"),Toast.LENGTH_SHORT).show();

            long id_02 = db_rute.buatPathKonsumen(jarak_konsumen_01);

            System.out.print("daftar id : " + id_02);

            if (listLokasi.get(i).getId() == 1){
                long id_01 = db_rute.deleteJarakB(1);
                // Writing Contacts to log
                Log.d("Delete : ", String.valueOf(id_01));
            }

            long id_AB = db_rute.deleteJarakAB(urutan.getTujuan(),urutan.getDari());
            // Writing Contacts to log
            Log.d("Delete : ", String.valueOf(id_AB));

            // Reading all lokasi konsumen
            Log.d("Delete : ", "all lokasi tujuan yg sudah dilewati ..");
            List<Lokasi> deleteLokB = db_rute.getAllJarakB(urutan.getTujuan());

            for (Lokasi lokasiB : deleteLokB) {
                String log = "Delete Id: " + lokasiB.getId();

                long id_BA = db_rute.deleteJarakB(urutan.getTujuan());
                // Writing Contacts to log
                Log.d("Delete : ", String.valueOf(id_BA));
            }

        }

    }


    private void hapusSemuaJarak() {
        // Reading all lokasi konsumen
        Log.d("Reading: ", "Reading all lokasi..");
        List<Lokasi> lok = db_rute.getAllJarak();

        for (Lokasi lokasi : lok) {
            String log = "Delete Id: " + lokasi.getId();

            long id = db_rute.deleteJarak(lokasi.getId());
            // Writing Contacts to log
            Log.d("Delete : ", String.valueOf(id));
        }

       // Toast.makeText(activity, "membuat ulang table jarak", Toast.LENGTH_LONG).show();
    }

    private void hapusSemuaHistoryJarak() {
        // Reading all lokasi konsumen
        Log.d("Reading: ", "Reading all lokasi..");
        List<Lokasi> lok = db_rute.getAllHistoryJarak();

        for (Lokasi lokasi : lok) {
            String log = "Delete Id: " + lokasi.getId();

            long id = db_rute.deleteHistoryJarak(lokasi.getId());
            // Writing Contacts to log
            Log.d("Delete : ", String.valueOf(id));
        }

        // Toast.makeText(activity, "membuat ulang table jarak", Toast.LENGTH_LONG).show();
    }

    private void hapusSemuaPath() {
        // Reading all lokasi konsumen
        Log.d("Reading: ", "Reading all lokasi..");
        List<Lokasi> lok = db_rute.getAllPath();

        for (Lokasi lokasi : lok) {
            String log = "Delete Id: " + lokasi.getId();

            long id = db_rute.deletePath(lokasi.getId());
            // Writing Contacts to log
            Log.d("Delete : ", String.valueOf(id));
        }

        // Toast.makeText(activity, "membuat ulang table jarak", Toast.LENGTH_LONG).show();
    }


    private void bacaSemuaPoint() {
        // Reading all lokasi konsumen
        Log.d("Reading: ", "Reading point...");
        List<Lokasi> lokasiList = db_rute.getAllLokasi();

        for (Lokasi lokasi : lokasiList) {

            Lokasi lok = db_rute.getLokasi(lokasi.getId());
            // Writing Contacts to log
            Log.d("daftar lokasi : ",+lok.getId() + " : "+lok.getLatitude()+ " : "+ lok.getLongitude());


        }
    }

    private void hapusSemuaLokasi() {
        // Reading all lokasi konsumen
        Log.d("Reading: ", "Reading all lokasi..");
        List<Lokasi> lok = db_rute.getAllLokasi();

        for (Lokasi lokasi : lok) {
            String log = "Delete Id: " + lokasi.getId();

            long id = db_rute.deleteLokasi(lokasi.getId());
            // Writing Contacts to log
            Log.d("Delete : ", String.valueOf(id));
        }
    }

    private void mengambilData(){

        fabNavigasi.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mengambilDataPemesanan();
            }

        });
    }


    private void getPoint(){

        fab_getpoint.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {

                bacaSemuaPoint();
            }

        });
    }

    /**
     * Mengambil data pemesanan baru
     */

    //http://stackoverflow.com/questions/28344448/how-to-send-json-object-to-server-using-volley-in-andorid
    private void mengambilDataPemesanan() {

        hapusSemuaLokasi();
        Toast.makeText(activity, "membuat ulang lokasi konsumen", Toast.LENGTH_LONG).show();

        if (CariRuteActivity.getmCurrentLocation() != null) {

            Lokasi lokasi_konsumen = new Lokasi("000", "0",String.valueOf(CariRuteActivity.getmCurrentLocation().getLatitude()),
                    String.valueOf(CariRuteActivity.getmCurrentLocation().getLongitude()), "0", 1);

            long id_kurir = db_rute.createLokasiKonsumen(lokasi_konsumen);
            Log.d("ID Kurir ", String.valueOf(id_kurir));
        }

        pDialog = new ProgressDialog(this.activity);
        pDialog.setMessage("memuat ....");
        pDialog.setCancelable(false);

        showProgressDialog();

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
                            boolean error = response.getBoolean("error");

                            if (!error) {
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

                                    Lokasi lokasi_konsumen = new Lokasi( konsumen_id,pemesanan_id, pemesanan_latitude, pemesanan_longitude, "0",1);

                                    long id = db_rute.createLokasiKonsumen(lokasi_konsumen);


                                    Log.d("ID", String.valueOf(id));
                                    Log.i("TAG La : ",pemesanan_latitude);
                                    Log.i("TAG Lo : ",pemesanan_longitude);

                                   // addMarker(mcLatLong);
                                    CariRuteActivity maps2 = new CariRuteActivity();
                                    maps2.tambakanMarker(Double.valueOf(pemesanan_latitude),Double.valueOf(pemesanan_longitude));


                                     }
                                Toast.makeText(activity, "lokasi konsumen berhasil didapatkan", Toast.LENGTH_LONG).show();

                                //updateList();
                            } else {

                                JSONArray message = response.getJSONArray("message");

                                Toast.makeText(activity, message.toString(), Toast.LENGTH_SHORT).show();

                            }


                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                    }


                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


                String e = VolleyErrorHelper.getMessage(error, activity);
                VolleyLog.d(AppController.TAG, "Error: " + e);

                hideProgressDialog();

                Toast.makeText(activity,
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
        if (!pDialog.isShowing()) {
            pDialog.show();
        }

        Log.d("Show Progress", "here");

    }

    private void hideProgressDialog() {
        if (pDialog.isShowing()) {
            pDialog.hide();
        }
    }

    /**
     * start button: control button handler FAB
     */

    private void tombolMenuHandler() {
        final ScaleAnimation anim = new ScaleAnimation(0, 1, 0, 1);
        anim.setFillBefore(true);
        anim.setFillAfter(true);
        anim.setFillEnabled(true);
        anim.setDuration(300);
        anim.setInterpolator(new OvershootInterpolator());

        tombolMenu.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (isMenuVisible()) {
                    setMenuVisible(false);
                    sideBarMenuVP.setVisibility(View.INVISIBLE);
                    tombolMenu.setImageResource(R.drawable.ic_keyboard_arrow_up_white_24dp);
                    tombolMenu.startAnimation(anim);
                } else {
                    setMenuVisible(true);
                    sideBarMenuVP.setVisibility(View.VISIBLE);
                    tombolMenu.setImageResource(R.drawable.ic_keyboard_arrow_down_white_24dp);
                    tombolMenu.startAnimation(anim);
                }
            }
        });
    }

    /**
     * implement zoom btn
     */
    protected void zoomControlHandler(final MapView mapView) {
        zoomInBtn.setImageResource(R.drawable.ic_add_white_24dp);
        zoomOutBtn.setImageResource(R.drawable.ic_remove_white_24dp);


        zoomInBtn.setOnClickListener(new View.OnClickListener() {
            MapViewPosition mvp = mapView.getModel().mapViewPosition;

            @Override public void onClick(View v) {
                if (mvp.getZoomLevel() < Variable.getVariable().getZoomLevelMax()) mvp.zoomIn();
            }
        });


        zoomOutBtn.setOnClickListener(new View.OnClickListener() {
            MapViewPosition mvp = mapView.getModel().mapViewPosition;

            @Override public void onClick(View v) {
                if (mvp.getZoomLevel() > Variable.getVariable().getZoomLevelMin()) mvp.zoomOut();
            }
        });
    }

    /**
     * move map to my current location as the center of the screen
     */
    protected void showMyLocation(final MapView mapView) {
        showPositionBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (CariRuteActivity.getmCurrentLocation() != null) {
                    showPositionBtn.setImageResource(R.drawable.ic_my_location_white_24dp);
                    CariRuteHandler.getPetaHandler().centerPointOnMap(
                            new LatLong(CariRuteActivity.getmCurrentLocation().getLatitude(),
                                    CariRuteActivity.getmCurrentLocation().getLongitude()), 0);

 /*                                       mapView.getModel().mapViewPosition.setMapPosition(new MapPosition(
                                               new LatLong(MapActivity.getmCurrentLocation().getLatitude(),
                                                       MapActivity.getmCurrentLocation().getLongitude()),
                                                mapView.getModel().mapViewPosition.getZoomLevel()));*/

                } else {
                    showPositionBtn.setImageResource(R.drawable.ic_location_searching_white_24dp);
                    Toast.makeText(activity, "No Location Available", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * @return side bar menu visibility status
     */
    public boolean isMenuVisible() {
        return menuVisible;
    }

    /**
     * side bar menu visibility
     *
     * @param menuVisible
     */
    public void setMenuVisible(boolean menuVisible) {
        this.menuVisible = menuVisible;
    }


    /**
     * called from Map activity when onBackpressed
     *
     * @return false no actions will perform; return true MapActivity will be placed back in the activity stack
     */


    private void log(String str) {
        Log.i(this.getClass().getSimpleName(), "-----------------" + str);
    }
}