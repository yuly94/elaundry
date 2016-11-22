package com.yuly.elaundry.kurir.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import com.yuly.elaundry.R;
import com.yuly.elaundry.kurir.adapter.TransaksiAdapter;
import com.yuly.elaundry.kurir.app.AppConfig;
import com.yuly.elaundry.kurir.app.AppController;
import com.yuly.elaundry.kurir.models.PesananModels;
import com.yuly.elaundry.kurir.helper.SQLiteHandler;
import com.yuly.elaundry.kurir.helper.SessionManager;
import com.yuly.elaundry.kurir.helper.VolleyErrorHelper;
import com.yuly.elaundry.kurir.widgets.FastScroller;

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
public class TransaksiFragment extends Fragment implements SearchView.OnQueryTextListener{

    //Creating a List of superheroes
    private List<PesananModels> listPemesanan;

    //Creating Views

    private SQLiteHandler db;
    private SessionManager session;

    private ProgressDialog pDialog;

    private String apiKey;

    private FastScroller mFastScroller;
    private FloatingActionButton mFab;

    //JSON Array
    private JSONArray result;


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
    private TransaksiAdapter adapter;
   // private SimpleStringAdapter adapter;

    private  SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_transaksi, container,
                false);

        final ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        //ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        // ab.setDisplayHomeAsUpEnabled(true);
      //  rv = (RecyclerView) v.findViewById(R.id.recycler_view);
        mFastScroller = (FastScroller) v.findViewById(R.id.fastscroller);

      //  setContentView(R.layout.activity_main_ori);
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
     //   mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
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

//                startActivity(new Intent(getActivity(), ActivityPemesanan.class));
            }
        });

        //Initializing our superheroes list
        listPemesanan = new ArrayList<PesananModels>();

        // SqLite database handler
        db = new SQLiteHandler(getContext());

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

                makeJsonArryReq();

                 adapter.clear();
            }
        });


        // Configure the refreshing colors
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);



        makeJsonArryReq();

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
    private void makeJsonArryReq() {
        showProgressDialog();
        mSwipeRefreshLayout.setRefreshing(true);
        JsonArrayRequest req = new JsonArrayRequest(AppConfig.URL_TRANSAKSI,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.d(AppController.TAG, "on Response " + response.toString());

                        //   msgResponse.setText(response.toString());
                        hideProgressDialog();

                        mSwipeRefreshLayout.setRefreshing(false);


                        //Storing the Array of JSON String to our JSON Array
                        result = response;

                        //Calling method getStudents to get the students from the JSON Array
                        getStudents(result);



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
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", apiKey);
                return headers;
            }


        };

        // Adding request to request queue
        String tag_json_arry = "jarray_req";
        AppController.getInstance().addToRequestQueue(req, tag_json_arry);

        // Cancelling request
        // ApplicationController.getInstance().getRequestQueue().cancelAll(tag_json_arry);
    }



    private void getStudents(JSONArray j){
        //Traversing through all the items in the json array
        for(int i=0;i<j.length();i++){
            PesananModels listPesanan = new PesananModels();
            try {

                JSONObject jObj = j.getJSONObject(i);

                boolean error = jObj.getBoolean("error");
                if (!error) {


                    JSONObject tempat = jObj.getJSONObject("transaksi");

                    String transaksi_id = tempat.getString("transaksi_id");
                    String konsumen_id = tempat.getString("konsumen_id");
                    String pemesanan_id = tempat.getString("pemesanan_id");
                    String kurir_pengambil_id = tempat.getString("kurir_pengambil_id");
                    String kurir_pengantar_id = tempat.getString("kurir_pengantar_id");
                    String tempat_id = tempat.getString("tempat_id");
                    String pembayaran_id = tempat.getString("pembayaran_id");
                    String tanggal_transaksi = tempat.getString("tanggal_transaksi");


                    Log.d(AppController.TAG, "id : " + transaksi_id);
                    Log.d(AppController.TAG, "nama : " + konsumen_id);
                    Log.d(AppController.TAG, "alamat : " + pemesanan_id);
                    Log.d(AppController.TAG, "kota : " + kurir_pengambil_id);
                    Log.d(AppController.TAG, "provinsi : " + kurir_pengantar_id);
                    Log.d(AppController.TAG, "latitude : " + tempat_id);
                    Log.d(AppController.TAG, "longitude : " + pembayaran_id);
                    Log.d(AppController.TAG, "creates : " + tanggal_transaksi);


                    listPesanan.setName(transaksi_id);
                    listPesanan.setRank(Integer.parseInt(pemesanan_id));
                    listPesanan.setRealName(tanggal_transaksi);

                } else {

                    // Error occurred in registration. Get the error
                    // message
                    String errorMsg = jObj.getString("message");
                    Toast.makeText(getContext(),
                            errorMsg, Toast.LENGTH_LONG).show();
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

            listPemesanan.add(listPesanan);
        }



         updateList();

    }



    private void updateList() {

        //Finally initializing our adapter
       adapter = new TransaksiAdapter(listPemesanan, getContext());

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
        final List<PesananModels> filteredModelList = filter(listPemesanan, newText);
        adapter.setFilter(filteredModelList);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    private List<PesananModels> filter(List<PesananModels> models, String query) {
        query = query.toLowerCase();

        final List<PesananModels> filteredModelList = new ArrayList<PesananModels>();
        for (PesananModels model : models) {
            final String text = model.getRealName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }



}