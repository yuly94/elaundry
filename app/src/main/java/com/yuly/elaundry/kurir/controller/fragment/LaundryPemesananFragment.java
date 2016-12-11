package com.yuly.elaundry.kurir.controller.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.yuly.elaundry.kurir.R;
import com.yuly.elaundry.kurir.controller.activity.DetailPemesananActivity;
import com.yuly.elaundry.kurir.controller.activity.RuteActivity;
import com.yuly.elaundry.kurir.controller.adapter.PemesananAdapter;
import com.yuly.elaundry.kurir.controller.app.AppConfig;
import com.yuly.elaundry.kurir.controller.app.AppController;
import com.yuly.elaundry.kurir.model.geterseter.TransaksiModel;
import com.yuly.elaundry.kurir.model.database.KurirDbHandler;
import com.yuly.elaundry.kurir.model.helper.SessionManager;
import com.yuly.elaundry.kurir.model.helper.VolleyErrorHelper;
import com.yuly.elaundry.kurir.model.listeners.RecyclerTouchListener;
import com.yuly.elaundry.kurir.view.widgets.FastScroller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by anonymous on 21/02/16.
 */
public class LaundryPemesananFragment extends Fragment implements SearchView.OnQueryTextListener{

    //Creating a List of superheroes
    private List<TransaksiModel> listPemesanan;

    //Creating Views

    private KurirDbHandler db;
    private SessionManager session;

    private ProgressDialog pDialog;

    private String apiKey;

    private FastScroller mFastScroller;
    private FloatingActionButton mFab;

    //JSON Array
    private JSONObject result;


    private static final String TAG = "RecyclerViewExample";
    //  private List<Anggota> feedItemList = new ArrayList<Anggota>();
    private RecyclerView mRecyclerView;
 //   private RecyclerView rv;

 //   private StatesRecyclerViewAdapter statesRecyclerViewAdapter;
    private View loadingView;
    private View emptyView;
    private View errorView;

    //Creating Views
    private LayoutManager layoutManager;
    private PemesananAdapter adapter;
    private String pemesanan_status = "baru memesan";
   // private SimpleStringAdapter adapter;

    private String text_tombol, status_sebelumnya,update_status;

    private  SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_transaksi_new, container,
                false);

        final ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        //ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        // ab.setDisplayHomeAsUpEnabled(true);
      //  rv = (RecyclerView) v.findViewById(R.id.recycler_view);
        mFastScroller = (FastScroller) v.findViewById(R.id.fastscroller);

      //  setContentView(R.layout.activity_main);
     //   final RecyclerView rv = (RecyclerView) findViewById(R.id.recycler_view);
      //  rv.setLayoutManager(new LinearLayoutManager(getContext()));
      //  rv.setHasFixedSize(true);
     //   loadingView = inflater.inflate(R.layout.view_loading, rv, false);
     //   emptyView = inflater.inflate(R.layout.view_empty, rv, false);
     //   errorView = inflater.inflate(R.layout.view_error, rv, false);
     //   adapter = new SimpleStringAdapter(Cheeses.sCheeseStrings);


        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        mFastScroller = (FastScroller) v.findViewById(R.id.fastscroller);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mFastScroller.setRecyclerView(mRecyclerView);



       /*
      //  rv.setLayoutManager(new LinearLayoutManager(this));
      //  rv.setHasFixedSize(true);
        loadingView = inflater.inflate(R.layout.view_loading, mRecyclerView, false);
        emptyView = inflater.inflate(R.layout.view_empty, mRecyclerView, false);
        errorView = inflater.inflate(R.layout.view_error, mRecyclerView, false);
      //adapter = new SimpleStringAdapter(Cheeses.sCheeseStrings);
        statesRecyclerViewAdapter = new StatesRecyclerViewAdapter(adapter, loadingView, emptyView, errorView);
        mRecyclerView.setAdapter(statesRecyclerViewAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
*/



        mFab = (FloatingActionButton)v.findViewById(R.id.floating_button);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 startActivity(new Intent(getActivity(), RuteActivity.class));
            }
        });

        //Initializing our superheroes list
        listPemesanan = new ArrayList<TransaksiModel>();

        // SqLite database handler
        db = new KurirDbHandler(getContext());

        // session manager
        session = new SessionManager(getContext());

        // Fetching user details from SQLite
        HashMap<String, String> user = db.getUserDetails();

        apiKey = user.get("kurir_kunci_api");

        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);

        // Lookup the swipe container view
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.my_swipeContainer);
        // Setup refresh listener which triggers new data loading
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call mSwipeRefreshLayout.setRefreshing(false)
                // once the network request has completed successfully.

                mengambilDataPemesanan();

                 adapter.clear();
            }
        });


        // Configure the refreshing colors
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


        //Get Argument that passed from activity in "data" key value
        text_tombol = getArguments().getString("TEXT_TOMBOL");
        status_sebelumnya = getArguments().getString("STATUS_SEBELUMNYA");
        update_status = getArguments().getString("UPDATE_STATUS");

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                TransaksiModel transaksi = listPemesanan.get(position);
              //  Toast.makeText(getActivity(), transaksi.getNoId() + " is selected!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), DetailPemesananActivity.class);

                intent.putExtra("PEMESANAN_ID", transaksi.getNoId());
                intent.putExtra("PEMESANAN_LATITUDE", transaksi.getLatitude());
                intent.putExtra("PEMESANAN_LONGITUDE", transaksi.getLongitude());
/*                intent.putExtra("TEXT_TOMBOL", "mengambil laundry");
                intent.putExtra("STATUS_SEBELUMNYA", "baru memesan");
                intent.putExtra("UPDATE_STATUS", "pengambilan laundry");*/

                intent.putExtra("TEXT_TOMBOL", text_tombol);
                intent.putExtra("STATUS_SEBELUMNYA", status_sebelumnya);
                intent.putExtra("UPDATE_STATUS", update_status);

                startActivity(intent);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        mengambilDataPemesanan();

      //  getPemesanan();

        return v;

    }

/*
    public void getLoading() {

        statesRecyclerViewAdapter.setState(StatesRecyclerViewAdapter.STATE_LOADING);
    }

    public void onEmptyClicked(View view) {
        statesRecyclerViewAdapter.setState(StatesRecyclerViewAdapter.STATE_EMPTY);
    }

    public void onErrorClicked(View view) {
        statesRecyclerViewAdapter.setState(StatesRecyclerViewAdapter.STATE_ERROR);
    }

    public void getList() {
        statesRecyclerViewAdapter.setState(StatesRecyclerViewAdapter.STATE_NORMAL);
    }
*/


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // findViews();
        setHasOptionsMenu(true);
    }



    /**
     * Making json array request
     */

    //http://stackoverflow.com/questions/28344448/how-to-send-json-object-to-server-using-volley-in-andorid
    private void mengambilDataPemesanan() {
        showProgressDialog();
       // mSwipeRefreshLayout.setRefreshing(true);

        // Posting parameters untuk mengambil data pemesanan
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("pemesanan_status", pemesanan_status);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                AppConfig.URL_PEMESANAN,new JSONObject(params),
                new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());


                        Log.d(AppController.TAG, "on Response " + response.toString());
                        //   msgResponse.setText(response.toString());
                        hideProgressDialog();

                        mSwipeRefreshLayout.setRefreshing(false);



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

                            Log.i(AppController.TAG, "parsing berhasil");

                            listPesanan.setNoId(pemesanan_id);
                            listPesanan.setLatitude(pemesanan_latitude);
                            listPesanan.setLongitude(pemesanan_longitude);
                            listPesanan.setNama(konsumen_nama);
                            listPesanan.setNoHp(konsumen_nohp);
                            listPesanan.setHarga(pemesanan_harga);
                            listPesanan.setKonsumenId(konsumen_id);
                            listPesanan.setAlamat(pemesanan_alamat);
                            listPesanan.setTanggal(pemesanan_tanggal);


                            listPemesanan.add(listPesanan);
                        }

                        updateList();
                    }
                    else {

                        JSONArray message = response.getJSONArray("message");

                        Toast.makeText(getActivity(), message.toString(), Toast.LENGTH_SHORT).show();

                    }


                }
                catch (JSONException e) {

                    e.printStackTrace();
                }

            }


                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


                String e = VolleyErrorHelper.getMessage(error, getContext());
                VolleyLog.d(AppController.TAG, "Error: " + e);

                hideProgressDialog();

                Toast.makeText(getContext(),
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
                    headers.put("Authorization", db.getUserApi()); //"a72d24f68128083f2904c6ce38fa232f97f2cab1");//"ef9bd71d1a704132aa366d1aa870f8be1faa44db");
                    headers.put("User-agent", System.getProperty("http.agent"));
                    return headers;
                }

            };

        // Adding request to request queue
        String tag_json_arry = "jobj_req";
        AppController.getInstance().addToRequestQueue(jsonObjReq , tag_json_arry);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_arry);
    }





    private void updateList() {

        //Finally initializing our adapter
       adapter = new PemesananAdapter(listPemesanan, getContext());

        //Adding adapter to recyclerview
      mRecyclerView.setAdapter(adapter);

    //    statesRecyclerViewAdapter = new StatesRecyclerViewAdapter(adapter, loadingView, emptyView, errorView);
    //    rv.setAdapter(adapter);
    //    rv.setAdapter(statesRecyclerViewAdapter);
    //    mFastScroller.setRecyclerView(rv);
    //    rv.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));




        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }


    private void showProgressDialog() {
        if (!pDialog.isShowing()){
            pDialog.show();
        }

       //  getLoading();

    /*     try {
            statesRecyclerViewAdapter.setState(StatesRecyclerViewAdapter.STATE_LOADING);
        } catch (Exception e){
            Log.d("Error", String.valueOf(e));
        }*/

        Log.d("Show Progress", "here");

    }

    private void hideProgressDialog() {
        if (pDialog.isShowing()){
            pDialog.hide();
        }

    //  getList();
       /* statesRecyclerViewAdapter.setState(StatesRecyclerViewAdapter.STATE_NORMAL);*/

/*
        try {
            statesRecyclerViewAdapter.setState(StatesRecyclerViewAdapter.STATE_NORMAL);
        } catch (Exception e){
            Log.d("Error", String.valueOf(e));
        }*/
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.item_menu_main, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when collapsed
                        //adapter.setFilter(mCountryModel);
                       adapter.setFilter(listPemesanan);
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        return true; // Return true to expand action view
                    }

                });
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<TransaksiModel> filteredModelList = filter(listPemesanan, newText);
        adapter.setFilter(filteredModelList);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    private List<TransaksiModel> filter(List<TransaksiModel> models, String query) {
        query = query.toLowerCase();

        final List<TransaksiModel> filteredModelList = new ArrayList<TransaksiModel>();
        for (TransaksiModel model : models) {
            final String text = model.getNama().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }



}