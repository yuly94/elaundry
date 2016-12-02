package com.yuly.elaundry.kurir.controller.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.graphhopper.routing.Path;
import com.graphhopper.util.Constants;
import com.graphhopper.util.Helper;
import com.graphhopper.util.PointList;
import com.graphhopper.util.ProgressListener;
import com.graphhopper.util.StopWatch;
import com.yuly.elaundry.kurir.R;
import com.yuly.elaundry.kurir.controller.app.AppConfig;
import com.yuly.elaundry.kurir.controller.app.AppController;
import com.yuly.elaundry.kurir.model.database.KurirDbHandler;
import com.yuly.elaundry.kurir.model.database.Lokasi;
import com.yuly.elaundry.kurir.model.database.RouteDbHelper;
import com.yuly.elaundry.kurir.model.geterseter.TransaksiModel;
import com.yuly.elaundry.kurir.model.helper.VolleyErrorHelper;
import com.yuly.elaundry.kurir.model.map.MapHandler;
import com.yuly.elaundry.kurir.model.map.Tracking;
import com.yuly.elaundry.kurir.model.peta.AndroidDownloader;
import com.yuly.elaundry.kurir.model.peta.AndroidHelper;
import com.yuly.elaundry.kurir.model.peta.GHAsyncTask;
import com.yuly.elaundry.kurir.model.util.Variable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mapsforge.core.graphics.Bitmap;
import org.mapsforge.core.graphics.Paint;
import org.mapsforge.core.graphics.Style;
import org.mapsforge.core.model.LatLong;
import org.mapsforge.core.model.MapPosition;
import org.mapsforge.core.model.Point;
import org.mapsforge.map.android.graphics.AndroidGraphicFactory;
import org.mapsforge.map.android.util.AndroidUtil;
import org.mapsforge.map.android.view.MapView;
import org.mapsforge.map.layer.Layer;
import org.mapsforge.map.layer.Layers;
import org.mapsforge.map.layer.cache.TileCache;
import org.mapsforge.map.layer.overlay.Marker;
import org.mapsforge.map.layer.overlay.Polyline;
import org.mapsforge.map.layer.renderer.TileRendererLayer;
import org.mapsforge.map.model.MapViewPosition;
import org.mapsforge.map.reader.MapDataStore;
import org.mapsforge.map.reader.MapFile;
import org.mapsforge.map.rendertheme.InternalRenderTheme;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PetaActivity2 extends AppCompatActivity implements LocationListener{
    private MapView mapView;
    private GraphHopper hopper;
    private LatLong start;
    private LatLong end;
    private Spinner localSpinner;
    private Button localButton;
    private Spinner remoteSpinner;
    private Button remoteButton;
    private volatile boolean prepareInProgress = false;
    private volatile boolean shortestPathRunning = false;
    private String currentArea = AppConfig.NAMA_PETA;
    private String fileListURL = AppConfig.URL_PETA;
    private String prefixURL = fileListURL;
    private String downloadURL;
    private File mapsFolder;
    private TileCache tileCache;
    private TileRendererLayer tileRendererLayer;

    private LocationManager locationManager;

    private static Location mCurrentLocation;
    private Marker mPositionMarker;
    private Location mLastLocation;

    private ProgressDialog pDialog;

    private KurirDbHandler db_user;
    private RouteDbHelper db_rute;

    private Layers layers;

    private FloatingActionButton fab_location;

    private String TAG = PetaActivity2.class.getSimpleName();

   // private Layers mylayers = mapView.getLayerManager().getLayers();

    private AksiPeta mapActions;


    protected boolean onMapTap(LatLong tapLatLong, Point layerXY, Point tapXY) {
        if (!isReady())
            return false;

        if (shortestPathRunning) {
            logUser("Calculation still in progress");
            return false;
        }
      //  layers = mapView.getLayerManager().getLayers();

        if (start != null && end == null) {
            end = tapLatLong;
            shortestPathRunning = true;
            Marker marker = createMarker(tapLatLong, R.drawable.ic_place_red_24dp);
            if (marker != null) {
                layers.add(marker);
            }

            calcPath(start.latitude, start.longitude, end.latitude,
                    end.longitude);
        } else {
            start = tapLatLong;
            end = null;
            // remove all layers but the first one, which is the map
            while (layers.size() > 1) {
                layers.remove(1);
            }

            Marker marker = createMarker(start, R.drawable.ic_place_green_24dp);
            if (marker != null) {
                layers.add(marker);
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        AndroidGraphicFactory.createInstance(getApplication());

        setContentView(R.layout.activity_peta_baru);
        this.mapView = (MapView) findViewById(R.id.mapView);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.my_toolbar);

        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Peta");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            // Thema
           // themeUtils.onActivityCreateSetTheme(this, getSupportActionBar(), this);
        }


        layers = mapView.getLayerManager().getLayers();

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

        tileCache = AndroidUtil.createTileCache(this, getClass().getSimpleName(), mapView.getModel().displayModel.getTileSize(),
                1f, mapView.getModel().frameBufferModel.getOverdrawFactor());

        final EditText input = new EditText(this);
        input.setText(currentArea);
        boolean greaterOrEqKitkat = Build.VERSION.SDK_INT >= 19;
        if (greaterOrEqKitkat) {
            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                logUser("Elaundry tidak bisa berfungsi tanpa penyimpanan external!");
                return;
            }
            mapsFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    "/elaundry/maps/");
        } else
            mapsFolder = new File(Environment.getExternalStorageDirectory(), "/elaundry/maps/");

        if (!mapsFolder.exists()) {
            mapsFolder.mkdirs();

            TextView welcome = (TextView) findViewById(R.id.welcome);
            welcome.setText("Selamat datang");//+ Constants.VERSION + "!");
            welcome.setPadding(6, 3, 3, 3);
            localSpinner = (Spinner) findViewById(R.id.locale_area_spinner);
            localButton = (Button) findViewById(R.id.locale_button);
            remoteSpinner = (Spinner) findViewById(R.id.remote_area_spinner);
            remoteButton = (Button) findViewById(R.id.remote_button);
            // TODO get user confirmation to download
            // if (AndroidHelper.isFastDownload(this))
            chooseAreaFromRemote();
            chooseAreaFromLocal();

            // chooseDirectLocal();

        } else {

            chooseDirectLocal();

        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_dapatkan);
        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO: 01/12/16  menghitung jarak route menggunakan jikstra dari titik 1 ke titik 2
                hitungJarak();

            }
        });

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab_dapatkan_dapat);
        fab2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                bacaLokasi();
            }
        });


        FloatingActionButton fab3 = (FloatingActionButton) findViewById(R.id.fab_refresh);
        fab3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                hapusSemuaLokasi();
            }
        });

        FloatingActionButton fab4 = (FloatingActionButton) findViewById(R.id.fab_menu);
        fab4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                mengambilDataPemesanan();

               // buatMarker((-7.817117399999998), (112.0287791));
              //  LatLong mcLatLong = new LatLong(-7.768428684206199,112.00151054708566);
              //  buatMarker(mcLatLong);
            }
        });


        fab_location = (FloatingActionButton) findViewById(R.id.fab_location);
        fab_location.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                //mengambilDataPemesanan();

                mapView.getModel().mapViewPosition.setMapPosition(new MapPosition(
                        new LatLong(PetaActivity2.getmCurrentLocation().getLatitude(),
                                PetaActivity2.getmCurrentLocation().getLongitude()),
                        mapView.getModel().mapViewPosition.getZoomLevel()));
            }
        });



        FloatingActionButton fab_besarkan = (FloatingActionButton) findViewById(R.id.fab_besarkan);
        fab_besarkan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                MapViewPosition mvp = mapView.getModel().mapViewPosition;
                if (mvp.getZoomLevel() < Variable.getVariable().getZoomLevelMax()) mvp.zoomIn();

            }
        });


        FloatingActionButton fab_kecilkan = (FloatingActionButton) findViewById(R.id.fab_kecilkan);
        fab_kecilkan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                MapViewPosition mvp = mapView.getModel().mapViewPosition;
                if (mvp.getZoomLevel() < Variable.getVariable().getZoomLevelMax()) mvp.zoomOut();

            }
        });



        //http://stackoverflow.com/questions/1561803/android-progressdialog-show-crashes-with-getapplicationcontext
        pDialog = new ProgressDialog(PetaActivity2.this);
        pDialog.setMessage(String.valueOf(R.string.memuat));
        pDialog.setCancelable(false);


        // SqLite database handler
        db_user = new KurirDbHandler(getApplicationContext());
        // SqLite database handler
        db_rute = new RouteDbHelper(getApplicationContext());


    }


    private void hitungJarak() {
        calcPath(-7.768428684206199,112.00151054708566,
                -7.767706382776794,112.01162539826053);
    }


    private void buatMarker(LatLong latLong) {

        Marker marker = createMarker(latLong, R.drawable.ic_place_red_24dp);
        if (marker != null) {
            layers.add(marker);
        }
    }

    private void buatMarkerHijau(LatLong latLong) {

        Marker marker = createMarker(latLong, R.drawable.ic_place_green_24dp);
        if (marker != null) {
            layers.add(marker);
        }
    }

    private void bacaLokasi() {
        // Reading all lokasi konsumen
        Log.d("Reading: ", "Reading all lokasi..");
        List<Lokasi> listlokasi = db_rute.getAllLokasi();

        for (Lokasi lokasi : listlokasi) {
            String log = "Id: " + lokasi.getId() + " ,Latitude : " + lokasi.getLatitude() + " ,Longitude : " + lokasi.getLongitude();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }
    }


    private void hapusSemuaLokasi() {
        // Reading all lokasi konsumen
        Log.d("Reading: ", "Reading all lokasi..");
        List<Lokasi> contacts = db_rute.getAllLokasi();

        for (Lokasi lokasi : contacts) {
            String log = "Delete Id: " + lokasi.getId();

            long id = db_rute.deleteLokasi(lokasi.getId());
            // Writing Contacts to log
            Log.d("Delete : ", String.valueOf(id));
        }
    }


    /**
     * Mengambil data pemesanan baru
     */

    //http://stackoverflow.com/questions/28344448/how-to-send-json-object-to-server-using-volley-in-andorid
    private void mengambilDataPemesanan() {
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

                                    Lokasi lokasi_konsumen = new Lokasi(listPesanan.getPemesananId(), String.valueOf(listPesanan.getLatitude()), String.valueOf(listPesanan.getLongitude()), "sada", 1);

                                    long id = db_rute.createLokasiKonsumen(lokasi_konsumen);

                                    // buatMarker((-7.817117399999998), (112.0287791));
                                    LatLong mcLatLong = new LatLong(Double.valueOf(pemesanan_latitude),Double.valueOf(pemesanan_longitude));
                                    buatMarker(mcLatLong);

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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (hopper != null)
            hopper.close();

        hopper = null;
        // necessary?
        System.gc();

        // Cleanup Mapsforge
        this.mapView.getLayerManager().getLayers().remove(this.tileRendererLayer);
        this.tileRendererLayer.onDestroy();
        this.tileCache.destroy();
        this.mapView.getModel().mapViewPosition.destroy();
        this.mapView.destroy();
        AndroidGraphicFactory.clearResourceMemoryCache();
    }

    boolean isReady() {
        // only return true if already loaded
        if (hopper != null)
            return true;

        if (prepareInProgress) {
            logUser("Preparation still in progress");
            return false;
        }
        logUser("Prepare finished but hopper not ready. This happens when there was an error while loading the files");
        return false;
    }

    private void initFiles(String area) {
        prepareInProgress = true;
        currentArea = area;
        downloadingFiles();
    }

    private void chooseAreaFromLocal() {
        List<String> nameList = new ArrayList<String>();
        String[] files = mapsFolder.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return filename != null
                        && (filename.endsWith(".ghz") || filename
                        .endsWith("-gh"));
            }
        });
        for (String file : files) {
            nameList.add(file);
        }

        if (nameList.isEmpty())
            return;

        chooseArea(localButton, localSpinner, nameList,
                new MySpinnerListener() {
                    @Override
                    public void onSelect(String selectedArea, String selectedFile) {
                        initFiles(selectedArea);
                    }
                });
    }

    private void chooseDirectLocal() {

        initFiles("indonesia_jawatimur_kediringanjuk");
    }

    private void chooseAreaFromRemote() {
        new GHAsyncTask<Void, Void, List<String>>() {
            protected List<String> saveDoInBackground(Void... params)
                    throws Exception {
                String[] lines = new AndroidDownloader().downloadAsString(fileListURL, false).split("\n");
                List<String> res = new ArrayList<String>();
                for (String str : lines) {
                    int index = str.indexOf("href=\"");
                    if (index >= 0) {
                        index += 6;
                        int lastIndex = str.indexOf(".ghz", index);
                        if (lastIndex >= 0)
                            res.add(prefixURL + str.substring(index, lastIndex)
                                    + ".ghz");
                    }
                }

                return res;
            }

            @Override
            protected void onPostExecute(List<String> nameList) {
                if (nameList.isEmpty()) {
                    logUser("Peta tidak tersedia di : " + fileListURL);
                    return;
                } else if (hasError()) {
                    getError().printStackTrace();
                    logUser("apakah terkoneksi internet ? gagal dalam mendapatkan peta: "
                            + getErrorMessage());
                    return;
                }
                MySpinnerListener spinnerListener = new MySpinnerListener() {
                    @Override
                    public void onSelect(String selectedArea, String selectedFile) {
                        if (selectedFile == null
                                || new File(mapsFolder, selectedArea + ".ghz").exists()
                                || new File(mapsFolder, selectedArea + "-gh").exists()) {
                            downloadURL = null;
                        } else {
                            downloadURL = selectedFile;
                        }
                        initFiles(selectedArea);
                    }
                };
                chooseArea(remoteButton, remoteSpinner, nameList,
                        spinnerListener);
            }
        }.execute();
    }

    private void chooseArea(Button button, final Spinner spinner,
                            List<String> nameList, final MySpinnerListener mylistener) {
        final Map<String, String> nameToFullName = new TreeMap<String, String>();
        for (String fullName : nameList) {
            String tmp = Helper.pruneFileEnd(fullName);
            if (tmp.endsWith("-gh"))
                tmp = tmp.substring(0, tmp.length() - 3);

            tmp = AndroidHelper.getFileName(tmp);
            nameToFullName.put(tmp, fullName);
        }
        nameList.clear();
        nameList.addAll(nameToFullName.keySet());
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, nameList);
        spinner.setAdapter(spinnerArrayAdapter);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Object o = spinner.getSelectedItem();
                if (o != null && o.toString().length() > 0 && !nameToFullName.isEmpty()) {
                    String area = o.toString();
                    mylistener.onSelect(area, nameToFullName.get(area));
                } else {
                    mylistener.onSelect(null, null);
                }
            }
        });
    }



    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onStop() {
        super.onStop();


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

           // Layers layers = mapView.getLayerManager().getLayers();
            //MapHandler.getMapHandler().removeLayer(layers, mPositionMarker);
            removeLayer(layers, mPositionMarker);
           // mPositionMarker = MapHandler.getMapHandler().createMarker(mcLatLong, R.drawable.ic_my_location_dark_24dp);

            Marker marker = createMarker(mcLatLong, R.drawable.ic_place_blue_24dp);

            layers.add(marker);

           // mapActions.showPositionBtn.setImageResource(R.drawable.ic_my_location_white_24dp);
//            fab_location.setImageResource(R.drawable.ic_gps_fixed_black_24dp);

        } else {
            //mapActions.showPositionBtn.setImageResource(R.drawable.ic_location_searching_white_24dp);
  //          fab_location.setImageResource(R.drawable.ic_gps_not_fixed_black_24dp);
        }
    }

    public void removeLayer(Layers layers, Layer layer) {
        if (layers != null && layer != null && layers.contains(layer)) {
            layers.remove(layer);
        }
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

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }


    public void onProviderEnabled(String provider) {
        Toast.makeText(getBaseContext(), "Gps hidup!! ", Toast.LENGTH_SHORT).show();
    }

    public void onProviderDisabled(String provider) {
        Toast.makeText(getBaseContext(), "Gps mati !!", Toast.LENGTH_SHORT).show();
    }




    public interface MySpinnerListener {
        void onSelect(String selectedArea, String selectedFile);
    }

    void downloadingFiles() {
        final File areaFolder = new File(mapsFolder, currentArea + "-gh");
        if (downloadURL == null || areaFolder.exists()) {
            loadMap(areaFolder);
            return;
        }

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("mengunduh dan mengekstrak peta dari : " + downloadURL);
        dialog.setIndeterminate(false);
        dialog.setMax(100);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.show();

        new GHAsyncTask<Void, Integer, Object>() {
            protected Object saveDoInBackground(Void... _ignore)
                    throws Exception {
                String localFolder = Helper.pruneFileEnd(AndroidHelper.getFileName(downloadURL));
                localFolder = new File(mapsFolder, localFolder + "-gh").getAbsolutePath();
                log("mengunduh dan mengekstrak peta dari " + downloadURL + " ke " + localFolder);
                AndroidDownloader downloader = new AndroidDownloader();
                downloader.setTimeout(30000);
                downloader.downloadAndUnzip(downloadURL, localFolder,
                        new ProgressListener() {
                            @Override
                            public void update(long val) {
                                publishProgress((int) val);
                            }
                        });
                return null;
            }

            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                dialog.setProgress(values[0]);
            }

            protected void onPostExecute(Object _ignore) {
                dialog.hide();
                if (hasError()) {
                    String str = "mengalami error saat mendapatkan data peta :" + getErrorMessage();
                    log(str, getError());
                    logUser(str);
                } else {
                    loadMap(areaFolder);
                }
            }
        }.execute();
    }

    void loadMap(File areaFolder) {
        logUser("memuat map");
        MapDataStore mapDataStore = new MapFile(new File(areaFolder, currentArea + ".map"));

        mapView.getLayerManager().getLayers().clear();

        tileRendererLayer = new TileRendererLayer(tileCache, mapDataStore,
                mapView.getModel().mapViewPosition, false, true, AndroidGraphicFactory.INSTANCE) {
            @Override
            public boolean onLongPress(LatLong tapLatLong, Point layerXY, Point tapXY) {
                return onMapTap(tapLatLong, layerXY, tapXY);
            }
        };
        tileRendererLayer.setTextScale(1.5f);
        tileRendererLayer.setXmlRenderTheme(InternalRenderTheme.OSMARENDER);
        mapView.getModel().mapViewPosition.setMapPosition(new MapPosition(mapDataStore.boundingBox().getCenterPoint(), (byte) 15));
        mapView.getLayerManager().getLayers().add(tileRendererLayer);

        // setContentView(mapView);


        mapView.setClickable(true);
        mapView.getMapScaleBar().setVisible(true);
        mapView.setBuiltInZoomControls(false);
        mapView.getMapZoomControls().setZoomLevelMin((byte) 10);
        mapView.getMapZoomControls().setZoomLevelMax((byte) 20);

        // mapView = (MapView) findViewById(R.id.mapView);

        loadGraphStorage();

        checkGpsAvailability();

        getMyLastLocation();
        updateCurrentLocation(null);
    }

    void loadGraphStorage() {
        logUser("memuat graph (" + Constants.VERSION + ") ... ");
        new GHAsyncTask<Void, Void, Path>() {
            protected Path saveDoInBackground(Void... v) throws Exception {
                GraphHopper tmpHopp = new GraphHopper().forMobile();
                tmpHopp.load(new File(mapsFolder, currentArea).getAbsolutePath());
                log("menemukan graph " + tmpHopp.getGraphHopperStorage().toString() + ", nodes:" + tmpHopp.getGraphHopperStorage().getNodes());
                hopper = tmpHopp;
                return null;
            }

            protected void onPostExecute(Path o) {
                if (hasError()) {
                    logUser("error terjadi saat membuat grapf:"
                            + getErrorMessage());
                } else {
                    logUser("selesai membuat graft, selahkan tekan lama untuk membuat rute");
                }

                finishPrepare();
            }
        }.execute();
    }

    private void finishPrepare() {
        prepareInProgress = false;
    }

    private Polyline createPolyline(GHResponse response) {
        Paint paintStroke = AndroidGraphicFactory.INSTANCE.createPaint();
        paintStroke.setStyle(Style.STROKE);
        paintStroke.setColor(Color.argb(200, 0, 0xCC, 0x33));
        paintStroke.setDashPathEffect(new float[]
                {
                        25, 15
                });
        paintStroke.setStrokeWidth(4);

        // TODO: new mapsforge version wants an mapsforge-paint, not an android paint.
        // This doesn't seem to support transparceny
        //paintStroke.setAlpha(128);
        Polyline line = new Polyline((Paint) paintStroke, AndroidGraphicFactory.INSTANCE);
        List<LatLong> geoPoints = line.getLatLongs();
        PointList tmp = response.getPoints();

        RouteDbHelper db = new RouteDbHelper(getApplicationContext());

        Log.d("Size", String.valueOf(response.getPoints().getSize()));

        for (int i = 0; i < response.getPoints().getSize(); i++)


        {
            geoPoints.add(new LatLong(tmp.getLatitude(i), tmp.getLongitude(i)));

            //
            Log.d(String.valueOf(tmp.getLatitude(i)), String.valueOf(tmp.getLongitude(i)));

            Lokasi lokasi_konsumen = new Lokasi("123", String.valueOf(tmp.getLatitude(i)), String.valueOf(tmp.getLongitude(i)), String.valueOf(response.getDistance()), 1);

            long id = db.createLokasiKonsumen(lokasi_konsumen);

            Log.d("IDx", String.valueOf(id));
        }


       // Log.d("LOKASI From DB", String.valueOf(db.getAllLokasi()));

        return line;
    }

    private Marker createMarker(LatLong p, int resource) {
      //  Drawable drawable = getResources().getDrawable(resource);


      //  Marker marker = createMarker(latLong, R.drawable.ic_place_red_24dp);

      //  Bitmap bitmap = AndroidGraphicFactory.convertToBitmap(drawable);
     //   return new Marker(p, bitmap, 0, -bitmap.getHeight() / 2);

        return null;
    }

    public void calcPath(final double fromLat, final double fromLon,
                         final double toLat, final double toLon) {

        log("mengkalkulasi path ...");

        new AsyncTask<Void, Void, GHResponse>() {
            float time;

            protected GHResponse doInBackground(Void... v) {
                StopWatch sw = new StopWatch().start();

                GHRequest req = new GHRequest(fromLat, fromLon, toLat, toLon).
                        setAlgorithm(AlgorithmOptions.DIJKSTRA_BI);

                req.getHints().
                        put("instructions", "false");
                GHResponse resp = hopper.route(req);
                time = sw.stop().getSeconds();
                return resp;
            }

            protected void onPostExecute(GHResponse resp) {
                if (!resp.hasErrors()) {
                    log("from:" + fromLat + "," + fromLon + " to:" + toLat + ","
                            + toLon + " found path with distance:" + resp.getDistance()
                            / 1000f + ", nodes:" + resp.getPoints().getSize() + ", time:"
                            + time + " " + resp.getDebugInfo());
                    logUser("the route is " + (int) (resp.getDistance() / 100) / 10f
                            + "km long, time:" + resp.getTime() / 60000f + "min, debug:" + time);

                    mapView.getLayerManager().getLayers().add(createPolyline(resp));

                    mapView.getLayerManager().getLayers().add(createPolyline(resp));

                    Log.d("Resp", resp.toString());


                    mapView.getLayerManager().getLayers().add(createPolyline(resp));
                    //mapView.redraw();
                } else {
                    logUser("Error:" + resp.getErrors());
                }
                shortestPathRunning = false;
            }
        }.execute();
    }

    private void log(String str) {
        Log.i("GH", str);
    }

    private void log(String str, Throwable t) {
        Log.i("GH", str, t);
    }

    private void logUser(String str) {
        log(str);
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }

    private static final int NEW_MENU_ID = Menu.FIRST + 1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, NEW_MENU_ID, 0, "Google");
        // menu.add(0, NEW_MENU_ID + 1, 0, "Other");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case NEW_MENU_ID:
                if (start == null || end == null) {
                    logUser("tap screen to set start and end of route");
                    break;
                }
                Intent intent = new Intent(Intent.ACTION_VIEW);
                // get rid of the dialog
                intent.setClassName("com.google.android.apps.maps",
                        "com.google.android.maps.MapsActivity");
                intent.setData(Uri.parse("http://maps.google.com/maps?saddr="
                        + start.latitude + "," + start.longitude + "&daddr="
                        + end.latitude + "," + end.longitude));
                startActivity(intent);
                break;

            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return true;
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




}
