package com.yuly.elaundry.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
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
import com.yuly.elaundry.adapter.TempatAdapter;
import com.yuly.elaundry.app.AppConfig;
import com.yuly.elaundry.app.AppController;
import com.yuly.elaundry.models.PesananModels;
import com.yuly.elaundry.helper.SQLiteHandler;
import com.yuly.elaundry.helper.SessionManager;
import com.yuly.elaundry.helper.VolleyErrorHelper;
import com.yuly.elaundry.peta.PetaActivity;
import com.yuly.elaundry.widgets.DividerItemDecoration;
import com.yuly.elaundry.widgets.FastScroller;

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
public class TempatFragment extends Fragment implements SearchView.OnQueryTextListener{



    //Creating a List of superheroes
    private List<PesananModels> daftarTempat;

    //Creating Views

    private SQLiteHandler db;
    private SessionManager session;

    private ProgressDialog pDialog;

    private String apiKey;

    private FastScroller mFastScroller;
    private FloatingActionButton fab;

    //JSON Array
    private JSONArray result;


    private static final String TAG = "RecyclerViewExample";
    //  private List<Anggota> feedItemList = new ArrayList<Anggota>();

    //Creating Views
    private RecyclerView mRecyclerView;
    private LayoutManager layoutManager;
    private TempatAdapter adapter;

    private  SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_transaksi, container,
                false);

        final ActionBar ab = ((AppCompatActivity) getActivity()).getSupportActionBar();
        //      ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        //       ab.setDisplayHomeAsUpEnabled(true);
   

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);

        mFastScroller = (FastScroller) v.findViewById(R.id.fastscroller);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        mFastScroller.setRecyclerView(mRecyclerView);
        fab = (FloatingActionButton)v.findViewById(R.id.floating_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), PetaActivity.class));
            }
        });

        //Initializing our superheroes list
        daftarTempat = new ArrayList<PesananModels>();

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
        JsonArrayRequest req = new JsonArrayRequest(AppConfig.URL_TEMPAT,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.d(AppController.TAG, "on Response " + response.toString());

                        //   msgResponse.setText(response.toString());
                         hideProgressDialog();



                        mSwipeRefreshLayout.setRefreshing(false);

                        //   parseJson(response);


                        //Storing the Array of JSON String to our JSON Array
                        result = response;

                        //Calling method getTempat to get the students from the JSON Array
                        getTempat(result);



                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


                String e = VolleyErrorHelper.getMessage(error, getContext());
                VolleyLog.d(AppController.TAG, "Error: " + e);


                mSwipeRefreshLayout.setRefreshing(false);

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



    private void getTempat(JSONArray j){
        //Traversing through all the items in the json array
        for(int i=0;i<j.length();i++){
            PesananModels listTempat = new PesananModels();
            try {

                JSONObject jObj = j.getJSONObject(i);

                boolean error = jObj.getBoolean("error");
                if (!error) {
                    JSONObject tempat = jObj.getJSONObject("tempat");

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

                    listTempat.setNama(nama);
                    listTempat.setAlamat(alamat);
                    listTempat.setKota(kota);


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

            daftarTempat.add(listTempat);
        }



        updateList();
        // Remember to CLEAR OUT old items before appending in the new ones

  /*          adapter.clear();
            // ...the data has come back, add new items to your adapter...
            adapter.addAll(daftarTempat);
*/

        //Setting adapter to show the items in the spinner
     //   spinner.setAdapter(new ArrayAdapter<String>(PemesananActivity.this, android.R.layout.simple_spinner_dropdown_item, students));
    }



    private void updateList() {

        //Finally initializing our adapter
        adapter = new TempatAdapter(daftarTempat, getContext());



        //Adding adapter to recyclerview
        mRecyclerView.setAdapter(adapter);

        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }





    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();


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
                        adapter.setFilter(daftarTempat);
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
        final List<PesananModels> filteredModelList = filter(daftarTempat, newText);
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
            final String text = model.getNama().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }



}
