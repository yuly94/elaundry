package com.yuly.elaundry.kurir.controller.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.yuly.elaundry.kurir.model.dataType.Destination;
import com.yuly.elaundry.kurir.model.database.KurirDbHandler;
import com.yuly.elaundry.kurir.model.database.Lokasi;
import com.yuly.elaundry.kurir.model.database.RouteDbHelper;


import com.yuly.elaundry.kurir.model.dijkstra.DijkstraAlgorithm;
import com.yuly.elaundry.kurir.model.dijkstra.Edge;
import com.yuly.elaundry.kurir.model.dijkstra.Graph;
import com.yuly.elaundry.kurir.model.dijkstra.Vertex;
import com.yuly.elaundry.kurir.model.geterseter.TransaksiModel;
import com.yuly.elaundry.kurir.model.helper.VolleyErrorHelper;
import com.yuly.elaundry.kurir.model.listeners.NavigatorListener;
import com.yuly.elaundry.kurir.model.listeners.PetaHandlerListener;
import com.yuly.elaundry.kurir.model.map.Navigasi;

import com.yuly.elaundry.kurir.model.peta.PetaHandler;
import com.yuly.elaundry.kurir.model.util.InstructionAdapter;
import com.yuly.elaundry.kurir.model.util.Variable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mapsforge.core.model.LatLong;
import org.mapsforge.map.android.view.MapView;
import org.mapsforge.map.layer.Layers;
import org.mapsforge.map.layer.overlay.Marker;
import org.mapsforge.map.model.MapViewPosition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;


public class PetaActions implements NavigatorListener, PetaHandlerListener {
    private Activity activity;
    protected FloatingActionButton showPositionBtn, navigationBtn, settingsBtn, controlBtn;
    protected FloatingActionButton zoomInBtn, zoomOutBtn, fabNavigasi,fab_refresh, fab_dapatkan, fab_getpoint, fab_rute;
    private ViewGroup sideBarVP,  sideBarMenuVP, navSettingsVP, navSettingsFromVP, navSettingsToVP, navInstructionVP,
            navInstructionListVP;
    private boolean menuVisible;

    private ProgressDialog pDialog;

    private KurirDbHandler db_user;
    private RouteDbHelper db_rute;
    private PetaHandler petaHandler;

    private List<Vertex> nodes;
    private List<Edge> edges;




    /**
     * true handle on start point ; false handle on end point
     */
    private boolean onStartPoint;
    private EditText fromLocalET, toLocalET;

    public PetaActions(AppCompatActivity activity, MapView mapView) {
        this.activity = activity;

        this.showPositionBtn = (FloatingActionButton) activity.findViewById(R.id.fab_location);

        this.controlBtn = (FloatingActionButton) activity.findViewById(R.id.fab_menu);

        this.fab_rute = (FloatingActionButton) activity.findViewById(R.id.fab_rute);

        this.zoomInBtn = (FloatingActionButton) activity.findViewById(R.id.fab_besarkan);
        this.zoomOutBtn = (FloatingActionButton) activity.findViewById(R.id.fab_kecilkan);

        this.fabNavigasi = (FloatingActionButton) activity.findViewById(R.id.fab_navigasi);

        this.fab_getpoint = (FloatingActionButton) activity.findViewById(R.id.fab_getpoint);

        this.fab_refresh = (FloatingActionButton) activity.findViewById(R.id.fab_refresh);

        this.fab_dapatkan = (FloatingActionButton) activity.findViewById(R.id.fab_dapatkan);

       // view groups managed by separate layout xml file : //map_sidebar_layout/map_sidebar_menu_layout
        this.sideBarVP = (ViewGroup) activity.findViewById(R.id.menu_nafigasi_peta);
        this.sideBarMenuVP = (ViewGroup) activity.findViewById(R.id.group_tombol_navigasi);

 /*     this.navSettingsVP = (ViewGroup) activity.findViewById(R.id.nav_settings_layout);
        this.navSettingsFromVP = (ViewGroup) activity.findViewById(R.id.nav_settings_from_layout);
        this.navSettingsToVP = (ViewGroup) activity.findViewById(R.id.nav_settings_to_layout);*/

        //this.navInstructionVP = (ViewGroup) activity.findViewById(R.id.nav_instruction_layout); // TODO
/*
        this.navInstructionListVP = (ViewGroup) activity.findViewById(R.id.nav_instruction_list_layout);
        //form location and to location textView
        this.fromLocalET = (EditText) activity.findViewById(R.id.nav_settings_from_local_et);
        this.toLocalET = (EditText) activity.findViewById(R.id.nav_settings_to_local_et);

        */

        this.menuVisible = false;
        this.onStartPoint = true;

      //  MapHandler.getPetaHandler().setMapHandlerListener(this);
        //Navigasi.getNavigator().addListener(this);


        //http://stackoverflow.com/questions/1561803/android-progressdialog-show-crashes-with-getapplicationcontext

        // SqLite database handler
        db_user = new KurirDbHandler(this.activity);
        // SqLite database handler
        db_rute = new RouteDbHelper(this.activity);

        controlBtnHandler();
        zoomControlHandler(mapView);
        showMyLocation(mapView);
        //navBtnHandler();

        mengambilData();
        menghitungJarak();

        buatPoly();

        getPoint();

        fabProsesRute();
    }


    private void fabProsesRute(){

        fab_rute.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {

                membuatTable();
/*
                Dijkstra_dua eks = new Dijkstra_dua();
                eks.eksekusi();*/
               // memprosesDijkstra();
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


                String jarak = PetaHandler.getPetaHandler().menghitungJarak(Double.parseDouble(lokA.getLatitude()), Double.parseDouble(lokA.getLongitude()),
                        Double.parseDouble(lokB.getLatitude()),Double.parseDouble(lokB.getLongitude()));



                Log.d("Jarak lokasi :", jarak+" Meter");

                Lokasi jarak_konsumen_01 = new Lokasi( i,k, jarak, 1);

                long id_01 =  db_rute.buatJarakKonsumen(jarak_konsumen_01);


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


                String jarak = PetaHandler.getPetaHandler().menghitungJarak(Double.parseDouble(lokA.getLatitude()), Double.parseDouble(lokA.getLongitude()),
                        Double.parseDouble(lokB.getLatitude()),Double.parseDouble(lokB.getLongitude()));

                Log.d("Jarak lokasi :", jarak+" Meter");

                Lokasi jarak_konsumen_01 = new Lokasi( i,j, jarak, 1);

                long id_02 =  db_rute.buatJarakKonsumen(jarak_konsumen_01);


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

        List<Lokasi> listLokasi = db_rute.getAllLokasi();

      //  for(Lokasi daftarLokasi : listLokasi) {
        for(int i=1; i < listLokasi.size() ;i++){

           Log.d("Jalur id :",
                   String.valueOf(listLokasi.get(i).getId()));

            // Lokasi urutan = db_rute.getPath(dariJarak);
            Lokasi lastPath = db_rute.getLastPath();

            int dariJarak = (lastPath.getTujuan() < 1) ? 1 : lastPath.getTujuan();


            Log.d("dari jarak", String.valueOf(dariJarak));


            Lokasi urutan = db_rute.getTujuan(dariJarak);

            Log.d("urutan :", "dari : "+ String.valueOf(urutan.getDari()) +
                    " tujuan : " + String.valueOf(urutan.getTujuan()));



            Lokasi lokasiDari = db_rute.getLokasi(urutan.getDari());
            Lokasi lokasiTujuan = db_rute.getLokasi(urutan.getTujuan());

            Log.d("Lokasi Dari : ", " latitude : " +lokasiDari.getLatitude() +" longitude : " +lokasiDari.getLongitude());
            Log.d("Lokasi Tujuan : "," latitude : " +lokasiTujuan.getLatitude() +" longitude : " +lokasiTujuan.getLongitude());

            String jarakLokasi = PetaHandler.getPetaHandler().menghitungJarak(Double.parseDouble(lokasiDari.getLatitude()), Double.parseDouble(lokasiDari.getLongitude()),
                    Double.parseDouble(lokasiTujuan.getLatitude()),Double.parseDouble(lokasiTujuan.getLongitude()));


            PetaHandler.getPetaHandler().calcPath(Double.parseDouble(lokasiDari.getLatitude()), Double.parseDouble(lokasiDari.getLongitude()),
                    Double.parseDouble(lokasiTujuan.getLatitude()),Double.parseDouble(lokasiTujuan.getLongitude()));


            Log.d("Jarak lokasi :", jarakLokasi+" Meter");


            Lokasi jarak_konsumen_01 = new Lokasi(urutan.getDari(), urutan.getTujuan(), jarakLokasi, 1);

            Toast.makeText(activity,"Jarak dari lokasi : "+ String.valueOf(urutan.getDari()) +
                    " ke lokasi : " + String.valueOf(urutan.getTujuan() +
                    " berjarak : " +jarakLokasi+" Meter"),Toast.LENGTH_SHORT).show();

            long id_02 = db_rute.buatPathKonsumen(jarak_konsumen_01);

            System.out.print("daftar id : " + id_02);




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


/*
            long id_AB = db_rute.deleteJarakAB(urutan.getDari(),urutan.getTujuan());
            // Writing Contacts to log
            Log.d("Delete : ", String.valueOf(id_AB));
*/




        }

    }

    private void memprosesDijkstra() {

        nodes = new ArrayList<Vertex>();

        edges = new ArrayList<Edge>();

        List<Lokasi> listJarak = db_rute.getAllJarak();


        for (int i = 0; i <9; i++) {
            Vertex location = new Vertex("Node_" + i, "Node_" + i);
            nodes.add(location);
        }

/*        for (Lokasi jarak : listJarak) {

            addLane("Edge_"+jarak.getId(),  jarak.getDari(),   jarak.getTujuan(), 1);

            Log.d("daftar Edge :", jarak.getId()+" : "+  jarak.getDari() +" : "+ jarak.getTujuan()+" : "+ jarak.getJarakAb());


        }*/

/*
        addLane("Edge_1",  1, 2, 6658); //
        addLane("Edge_2",  1, 3, 8845);
        addLane("Edge_3",  1, 4, 8716);
        addLane("Edge_4",  1, 5, 19251);
       // addLane("Edge_5",  2, 1, 6658);
        addLane("Edge_5",  2, 3, 5777);
        addLane("Edge_6",  2, 4, 5649);
        addLane("Edge_7",  2, 5, 4945);//
       // addLane("Edge_9",  3, 2, 4609);
       // addLane("Edge_10", 3, 1, 7677);
        addLane("Edge_8",  3, 4, 2014);
        addLane("Edge_9",  3, 5, 3344);
      //  addLane("Edge_13",  4, 3, 2083); //4
      //  addLane("Edge_14",  4, 2, 5812);
      //  addLane("Edge_15",  4, 1, 8880);
        addLane("Edge_10",  4, 5, 4291);
       // addLane("Edge_17",  5, 4, 4312); //3
       // addLane("Edge_18",  5, 3, 3434);
       // addLane("Edge_19",  5, 2, 5627);
      //  addLane("Edge_20",  5, 1, 19905);*/

        addLane("Edge_1",1,2,7);
        addLane("Edge_2",1,3,9);
        addLane("Edge_3",1,6,14);
        addLane("Edge_4",2,3,10);
        addLane("Edge_5",2,4,15);
        addLane("Edge_6",3,4,11);
        addLane("Edge_7",3,6,2);
        addLane("Edge_8",4,5,6);
        addLane("Edge_9",5,6,9);

/*
        addLane("Edge_0", 0, 1, 85);
        addLane("Edge_1", 0, 2, 217);
        addLane("Edge_2", 0, 4, 173);
        addLane("Edge_3", 2, 6, 186);
        addLane("Edge_4", 2, 7, 103);
        addLane("Edge_5", 3, 7, 183);
        addLane("Edge_6", 5, 8, 250);
        addLane("Edge_7", 8, 9, 84);
        addLane("Edge_8", 7, 9, 167);
        addLane("Edge_9", 4, 9, 502);
        addLane("Edge_10", 9, 10, 40);
        addLane("Edge_11", 1, 10, 600);*/

        //tambahLane();

        // Lets check from location Loc_1 to Loc_10
        Graph graph = new Graph(nodes, edges);
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);
        dijkstra.execute(nodes.get(1));
        LinkedList<Vertex> path = dijkstra.getPath(nodes.get(6));

        assertNotNull(path);
        assertTrue(path.size() > 1);

        for (Vertex vertex : path) {
            System.out.println(vertex);
        }

    }

    private void addLane(String laneId, int sourceLocNo, int destLocNo,
                         int duration) {
        Edge lane = new Edge(laneId,nodes.get(sourceLocNo), nodes.get(destLocNo), duration );
        edges.add(lane);
    }

    private void tambahLane() {
        List<Lokasi> listJarak = db_rute.getAllJarak();
        for (Lokasi jarak : listJarak) {

            addLane("Edge_"+jarak.getId(),  jarak.getDari(),   jarak.getTujuan(), 1);

            Log.d("daftar Edge :", jarak.getId()+" : "+  jarak.getDari() +" : "+ jarak.getTujuan()+" : "+ jarak.getJarakAb());

            Edge lane = new Edge("Edge_"+jarak.getId(),nodes.get(jarak.getDari()), nodes.get(jarak.getTujuan()), jarak.getJarakAb() );
            edges.add(lane);

        }

    }

    /**
     * add end point marker to map
     *
     * @param endPoint
     */
    private void addToMarker(LatLong endPoint) {
        PetaHandler.getPetaHandler().addEndMarker(endPoint);
    }

    /**
     * add start point marker to map
     *
     * @param startPoint
     */
    private void addFromMarker(LatLong startPoint) {
        PetaHandler.getPetaHandler().addStartMarker(startPoint);
    }


    /**
     * add start point marker to map
     *
     * @param Point
     */
    private void addMarker(LatLong Point) {
        PetaHandler.getPetaHandler().tambahMarkerMerah(Point);
    }


    private void hitungJarak() {

        PetaHandler.getPetaHandler().hitungPath(-7.768428684206199,112.00151054708566,
                -7.767706382776794,112.01162539826053);
    }

    private void hitungJarakAbc() {

        PetaHandler.getPetaHandler().calcPathX();
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




    private void buatTablex(){

        Log.d("Reading: ", "Reading all lokasi..");
        List<Lokasi> listlokasi = db_rute.getAllJarak();

        for (Lokasi lokasi : listlokasi) {
            String log = "Id: " + lokasi.getDari() + " ,Latitude : " + lokasi.getTujuan() + " ,Longitude : " + lokasi.getJarakAb();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }

        List<Lokasi> listJarak = db_rute.getAllJarak();


        //Graph.Edge[] stockArr = new Graph.Edge[listJarak.size()];
        String[] stockArr = new String[listJarak.size()];
        stockArr = listJarak.toArray(stockArr);

        System.out.print(Arrays.toString(stockArr));
        Log.d("Tag", String.valueOf(stockArr));

        Log.d("Tag", "ini");



/*        Graph.Edge[] GRAPH = {

               // graph ()

                new Graph.Edge("a", "b", (int) 7.5),
                new Graph.Edge("a", "c", (int) 9.9),
                new Graph.Edge("a", "f", (int) 14.3),
                new Graph.Edge("b", "c", (int) 10.7),
                new Graph.Edge("b", "d", (int) 4.4),
                new Graph.Edge("c", "d", (int) 11.4),
                new Graph.Edge("c", "f", (int) 2.1),
                new Graph.Edge("d", "e", (int) 6.2),

                new Graph.Edge("e", "f", (int) 9.3)
        };*/


/*
            String START = "a";
            String END = "e";


        Graph g = new Graph(stockArr);
        g.dijkstra(START);
        System.out.println("&");
        g.printPath(END);

*/

        // g.printAllPaths();
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





    private void bacaPoint() {
        // Reading all lokasi konsumen
        Log.d("Reading: ", "Reading all lokasi..");
        List<Lokasi> listpoint = db_rute.getAllPoint();

        for (Lokasi lokasi : listpoint) {
            String log = "Id: " + lokasi.getId() + " ,Latitude : " + lokasi.getLatitude() + " ,Longitude : " + lokasi.getLongitude();
            // Writing Contacts to log
            Log.d("Name: ", log);


        }


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

    private void menghitungJarak(){

        fab_refresh.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                hitungJarak();

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

    private void buatPoly(){

        fab_dapatkan.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {


                hitungJarakAbc();

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

/*                                    listPesanan.setNoId(pemesanan_id);
                                    listPesanan.setPemesananId(pemesanan_id);
                                    listPesanan.setNama(konsumen_nama);
                                    listPesanan.setNoHp(konsumen_nohp);
                                    listPesanan.setHarga(pemesanan_harga);
                                    listPesanan.setLatitude(pemesanan_latitude);
                                    listPesanan.setLongitude(pemesanan_longitude);
                                    listPesanan.setKonsumenId(konsumen_id);
                                    listPesanan.setAlamat(pemesanan_alamat);
                                    listPesanan.setTanggal(pemesanan_tanggal);*/

                                    Lokasi lokasi_konsumen = new Lokasi( konsumen_id,pemesanan_id, pemesanan_latitude, pemesanan_longitude, "0",1);

                                    long id = db_rute.createLokasiKonsumen(lokasi_konsumen);

                                    // buatMarker((-7.817117399999998), (112.0287791));



                                    Log.d("ID", String.valueOf(id));
                                    Log.i("TAG La : ",pemesanan_latitude);
                                    Log.i("TAG Lo : ",pemesanan_longitude);
                                 //   LatLong mcLatLong = new LatLong(Double.valueOf(pemesanan_latitude),Double.valueOf(pemesanan_longitude));

                                   // petaHandler.tambahMarkerMerah(mcLatLong);

                                   // buatMarkerHijau(mcLatLong,mapView);


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



    private void buatMarkerHijau(LatLong latLong,  MapView mapView) {
        Layers layers = mapView.getLayerManager().getLayers();
        Marker marker = petaHandler.createMarker(latLong, R.drawable.ic_place_green_24dp);
        if (marker != null) {
            layers.add(marker);
        }
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
     * settings layout:
     * <p>
     * from item handler: when from item is clicked
     */
    private void settingsFromItemHandler() {
        final ViewGroup fromFieldVG = (ViewGroup) activity.findViewById(R.id.map_nav_settings_from_item);
        fromFieldVG.setOnTouchListener(new View.OnTouchListener() {
            @Override public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        fromFieldVG.setBackgroundColor(activity.getResources().getColor(R.color.my_primary_light));
                        return true;
                    case MotionEvent.ACTION_UP:
                        fromFieldVG.setBackgroundColor(activity.getResources().getColor(R.color.my_primary));
                        navSettingsVP.setVisibility(View.INVISIBLE);
                        navSettingsFromVP.setVisibility(View.VISIBLE);
                        return true;
                }
                return false;
            }
        });
        //        from layout
        //clear button
        ImageButton fromLayoutClearBtn = (ImageButton) activity.findViewById(R.id.nav_settings_from_clear_btn);
        fromLayoutClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                navSettingsVP.setVisibility(View.VISIBLE);
                navSettingsFromVP.setVisibility(View.INVISIBLE);
            }
        });
        //  from layout: items
        useCurrentLocationHandler();
        //        chooseFromFavoriteHandler();TODO
        pointOnMapHandler();
    }

    /**
     * from layout : point item view group
     * <p>
     * preform actions when point on map item is clicked
     */
    private void pointOnMapHandler() {
        final ViewGroup pointItem = (ViewGroup) activity.findViewById(R.id.map_nav_settings_from_point);
        pointItem.setOnTouchListener(new View.OnTouchListener() {
            @Override public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        pointItem.setBackgroundColor(activity.getResources().getColor(R.color.my_primary_light));
                        return true;
                    case MotionEvent.ACTION_UP:
                        pointItem.setBackgroundColor(activity.getResources().getColor(R.color.my_primary));
                        onStartPoint = true;
                        navSettingsFromVP.setVisibility(View.INVISIBLE);
                        //touch on map
                        Toast.makeText(activity, "Touch on Map to choose your start Location", Toast.LENGTH_SHORT)
                                .show();
                        PetaHandler.getPetaHandler().setNeedLocation(true);
                        return true;
                }
                return false;
            }
        });
    }

    /**
     * choose from favorite list handler: preform actions when choose from favorite item is clicked
     */
    private void chooseFromFavoriteHandler() {
        //create a list view
        //read from Json file inflater to RecyclerView
        final ViewGroup chooseFavorite = (ViewGroup) activity.findViewById(R.id.map_nav_settings_from_favorite);
        chooseFavorite.setOnTouchListener(new View.OnTouchListener() {
            @Override public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        chooseFavorite.setBackgroundColor(activity.getResources().getColor(R.color.my_primary_light));
                        return true;
                    case MotionEvent.ACTION_UP:
                        chooseFavorite.setBackgroundColor(activity.getResources().getColor(R.color.my_primary));
                        //TODO
                        return true;
                }
                return false;
            }
        });
    }

    /**
     * current location handler: preform actions when current location item is clicked
     */
    private void useCurrentLocationHandler() {
        final ViewGroup useCurrentLocal = (ViewGroup) activity.findViewById(R.id.map_nav_settings_from_current);
        useCurrentLocal.setOnTouchListener(new View.OnTouchListener() {
            @Override public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        useCurrentLocal.setBackgroundColor(activity.getResources().getColor(R.color.my_primary_light));
                        return true;
                    case MotionEvent.ACTION_UP:
                        useCurrentLocal.setBackgroundColor(activity.getResources().getColor(R.color.my_primary));
                        if (CariRuteActivity.getmCurrentLocation() != null) {
                            Destination.getDestination().setStartPoint(
                                    new LatLong(CariRuteActivity.getmCurrentLocation().getLatitude(),
                                            CariRuteActivity.getmCurrentLocation().getLongitude()));
                            addFromMarker(Destination.getDestination().getStartPoint());
                            fromLocalET.setText(Destination.getDestination().getStartPointToString());
                            navSettingsFromVP.setVisibility(View.INVISIBLE);
                            navSettingsVP.setVisibility(View.VISIBLE);
                            activeNavigator();
                        } else {
                            Toast.makeText(activity, "Current Location not available, Check your GPS signal!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        return true;
                }
                return false;
            }
        });
    }

    /**
     * when use press on the screen to get a location form map
     *
     * @param latLong
     */
    @Override public void onPressLocation(LatLong latLong) {
        if (onStartPoint) {
            Destination.getDestination().setStartPoint(latLong);
            addFromMarker(latLong);
            fromLocalET.setText(Destination.getDestination().getStartPointToString());
        } else {
            Destination.getDestination().setEndPoint(latLong);
            addToMarker(latLong);
            toLocalET.setText(Destination.getDestination().getEndPointToString());
        }
        navSettingsVP.setVisibility(View.VISIBLE);
        activeNavigator();
    }

    /**
     * calculate path calculating (running) true NOT running or finished false
     *
     * @param shortestPathRunning
     */
    @Override public void pathCalculating(boolean shortestPathRunning) {
        if (!shortestPathRunning && Navigasi.getNavigator().getGhResponse() != null) {
            activeDirections();
        }
    }

    /**
     * drawer polyline on map , active navigator instructions(directions) if on
     */
    private void activeNavigator() {
        LatLong startPoint = Destination.getDestination().getStartPoint();
        LatLong endPoint = Destination.getDestination().getEndPoint();
        if (startPoint != null && endPoint != null) {
            // show path finding process
            navSettingsVP.setVisibility(View.INVISIBLE);

            View pathfinding = activity.findViewById(R.id.map_nav_settings_path_finding);
            pathfinding.setVisibility(View.VISIBLE);
            pathfinding.bringToFront();
            PetaHandler petaHandler = PetaHandler.getPetaHandler();
            petaHandler.calcPath(startPoint.latitude, startPoint.longitude, endPoint.latitude, endPoint.longitude);
            if (Variable.getVariable().isDirectionsON()) {
                petaHandler.setNeedPathCal(true);
                //rest running at
            }
        }
    }

    /**
     * active directions, and directions view
     */
    private void activeDirections() {
        RecyclerView instructionsRV;
        RecyclerView.Adapter instructionsAdapter;
        RecyclerView.LayoutManager instructionsLayoutManager;

        instructionsRV = (RecyclerView) activity.findViewById(R.id.nav_instruction_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        instructionsRV.setHasFixedSize(true);

        // use a linear layout manager
        instructionsLayoutManager = new LinearLayoutManager(activity);
        instructionsRV.setLayoutManager(instructionsLayoutManager);

        // specify an adapter (see also next example)
        instructionsAdapter = new InstructionAdapter(Navigasi.getNavigator().getGhResponse().getInstructions());
        instructionsRV.setAdapter(instructionsAdapter);

    }



/*

    */
/**
     * handler clicks on nav button
     *//*

    private void navBtnHandler() {
        navigationBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                sideBarVP.setVisibility(View.INVISIBLE);
                if (Navigasi.getNavigator().isOn()) {
                    navInstructionListVP.setVisibility(View.VISIBLE);
                } else {
                    navSettingsVP.setVisibility(View.VISIBLE);
                }
            }
        });
    }
*/


    /**
     * start button: control button handler FAB
     */

    private void controlBtnHandler() {
        final ScaleAnimation anim = new ScaleAnimation(0, 1, 0, 1);
        anim.setFillBefore(true);
        anim.setFillAfter(true);
        anim.setFillEnabled(true);
        anim.setDuration(300);
        anim.setInterpolator(new OvershootInterpolator());

        controlBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (isMenuVisible()) {
                    setMenuVisible(false);
                    sideBarMenuVP.setVisibility(View.INVISIBLE);
                    controlBtn.setImageResource(R.drawable.ic_keyboard_arrow_up_white_24dp);
                    controlBtn.startAnimation(anim);
                } else {
                    setMenuVisible(true);
                    sideBarMenuVP.setVisibility(View.VISIBLE);
                    controlBtn.setImageResource(R.drawable.ic_keyboard_arrow_down_white_24dp);
                    controlBtn.startAnimation(anim);
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
                    PetaHandler.getPetaHandler().centerPointOnMap(
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
     * the change on navigator: navigation is used or not
     *
     * @param on
     */
    @Override public void statusChanged(boolean on) {
        if (on) {
            navigationBtn.setImageResource(R.drawable.ic_directions_white_24dp);
        } else {
            navigationBtn.setImageResource(R.drawable.ic_navigation_white_24dp);
        }
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