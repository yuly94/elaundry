package com.yuly.elaundry.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yuly.elaundry.R;
import com.yuly.elaundry.activity.PemesananActivity;
import com.yuly.elaundry.app.AppConfig;
import com.yuly.elaundry.app.AppController;
import com.yuly.elaundry.app.Constants;
import com.yuly.elaundry.helper.SQLiteHandler;
import com.yuly.elaundry.helper.SessionManager;
import com.yuly.elaundry.helper.VolleyErrorHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by samsung on 21/02/16.
 */
public class ProfileFragment extends Fragment {

    private static final String TAG = ProfileFragment.class.getSimpleName();


    //Creating Views


    private SessionManager session;


    private TextView tv_name,tv_email,tv_message;
    private SharedPreferences pref;
    private EditText et_old_password,et_new_password;
    private AlertDialog dialog;
    private ProgressBar progress;
    private ProgressDialog pDialog;
    private String apiKey;
    private SQLiteHandler db;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        View v = inflater.inflate(R.layout.fragment_profile_new, container,
                false);

        Button tomEdit = (Button) v.findViewById(R.id.btn_edit);
        tomEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), PemesananActivity.class));
            }
        });

        Button tomSimpan = (Button) v.findViewById(R.id.btn_save);
        tomSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), PemesananActivity.class));
            }
        });

        TextView textNama = (TextView) v.findViewById(R.id.et_name);
        TextView textEmail = (TextView) v.findViewById(R.id.et_email);
        TextView textAlamat = (TextView) v.findViewById(R.id.et_address);
        TextView textTelepon = (TextView) v.findViewById(R.id.et_phone);

        // SqLite database handler
        db = new SQLiteHandler(getContext());

        // session manager
        session = new SessionManager(getContext());

        // Fetching user details from SQLite
        HashMap<String, String> user = db.getUserDetails();

        String nama = user.get("nama");
        String alamat = user.get("alamat");
        String telepon = user.get("telepon");
        String email = user.get("email");
        apiKey = user.get("api");

        textNama.setText(nama);
        textEmail.setText(apiKey);
        textAlamat.setText(alamat);
        textTelepon.setText(telepon);

        return v;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // findViews();
        setHasOptionsMenu(true);
    }



    private void showPasswordDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_change_password, null);
        et_old_password = (EditText)view.findViewById(R.id.et_old_password);
        et_new_password = (EditText)view.findViewById(R.id.et_new_password);
        tv_message = (TextView)view.findViewById(R.id.tv_message);
        progress = (ProgressBar)view.findViewById(R.id.progress);
        builder.setView(view);
        builder.setTitle("Change Password");
        builder.setPositiveButton("Change Password", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old_password = et_old_password.getText().toString();
                String new_password = et_new_password.getText().toString();
                if(!old_password.isEmpty() && !new_password.isEmpty()){

                    progress.setVisibility(View.VISIBLE);
                    //changePasswordProcess(old_password,new_password);
                    makeJsonObjectRequest(old_password,new_password);


                }else {

                    tv_message.setVisibility(View.VISIBLE);
                    tv_message.setText(R.string.field_empty);
                    progress.setVisibility(View.INVISIBLE);
                }
            }
        });
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_profile, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement


        if (id == R.id.action_change_pass) {
            // return true;

            showPasswordDialog();


        } else if (id == R.id.action_exit) {

            getActivity().finish();
            System.exit(0);
        }


        return super.onOptionsItemSelected(item);
    }



    public String getBodyContentType()
    {
        return "application/json; charset=utf-8";
    }




    /**
     * Making json array request
     * @param old_password
     * @param new_password
     */
    private void makeJsonObjectRequest(String old_password, String new_password) {

        // Tag used to cancel the request
        String tag_json_obj = "json_obj_req";

        String url = AppConfig.URL_CHANGEPASS;
showDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG + " On Response", response.toString());
hideDialog();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG + " On Error Response",error.getMessage());
                hideDialog();
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
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

   /*     Map<String,String> jsonParams = new HashMap<String, String>();
        jsonParams.put("old_password", old_password);
        jsonParams.put("new_password", new_password);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,AppConfig.URL_CHANGEPASS,new JSONObject(jsonParams), new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG,"User creation completed successfully");
                // Go to next activity
            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String,String> getHeaders(){
                HashMap<String, String> headers = new HashMap<String, String>();

                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization",apiKey);
                headers.put("User-agent", "My useragent");
                return headers;
            }
        };
*/

/*
        Map<String, String> jsonParams = new HashMap<String, String>();
        jsonParams.put("old_password", old_password);
        jsonParams.put("new_password", new_password);

        JsonObjectRequest myRequest = new JsonObjectRequest(
                Request.Method.POST,
                AppConfig.URL_CHANGEPASS,
                new JSONObject(jsonParams),

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            VolleyLog.v("Response:%n %s", response.toString(4));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("User-agent", "My useragent");
                headers.put("Authorization", apiKey);
                return headers;
            }
        };
        AppController.getInstance().addToRequestQueue(myRequest, "tag");
*/

      /*  // HTTP POST
        String url = AppConfig.URL_CHANGEPASS;
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("old_password", old_password);
            jsonObject.put("new_password", new_password);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    // do something...
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // do something...
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    final Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Authorization", apiKey);
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };
            requestQueue.add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

*/

     /*   HashMap<String, String> params = new HashMap<String, String>();
        params.put("old_password", old_password);
        params.put("new_password", new_password);



        JsonObjectRequest req = new JsonObjectRequest(AppConfig.URL_CHANGEPASS, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            VolleyLog.v("Response:%n %s", response.toString(4));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", apiKey);
                return headers;
            }
        };


// add the request object to the queue to be executed
        AppController.getInstance().addToRequestQueue(req);
*/
/*
    //    showDialog();

            */
/*Post data*//*

        Map<String, String> jsonParams = new HashMap<String, String>();

        jsonParams.put("old_password", old_password);
        jsonParams.put("new_password", new_password);



        JsonObjectRequest postRequest = new JsonObjectRequest( Request.Method.POST, AppConfig.URL_CHANGEPASS,

                new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

            //        hideDialog();
                        //   Success Handler
                        try {
                            VolleyLog.v("Response:%n %s", response.toString(4));

                            Log.d(Constants.TAG,"failed");
                            progress.setVisibility(View.GONE);
                            tv_message.setVisibility(View.VISIBLE);
                            tv_message.setText(response.toString(4));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //   Handle Error

                        String e = VolleyErrorHelper.getMessage(error, getActivity());

                        VolleyLog.d(AppController.TAG, "Error: " + e);


                        Toast.makeText(getActivity(),
                                e, Toast.LENGTH_LONG).show();

                        Log.d(Constants.TAG,"failed");
                        progress.setVisibility(View.GONE);
                        tv_message.setVisibility(View.VISIBLE);
                        tv_message.setText(e);

                  //      hideDialog();


                    }
                }) {
*/
/*            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("Authorization", apiKey);
                return headers;
            }*//*


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", apiKey);
                return headers;
            }

        };


        // Adding request to request queue
        String tag_json_object = "object_req";
     //   AppController.getInstance().addToRequestQueue(postRequest, tag_json_object);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(postRequest);
*/

    }



    /**
     * function to verify login details in mysql db
     * */
    private void changePasswordProcess(final String old_password,final String new_password){


        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();


        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_CHANGEPASS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response);
                hideDialog();

                try {

                    //JSONException: Value of type java.lang.String cannot be converted to JSONObject
                    //   response = response.replace("\\\"","'");
                    //    JSONObject jObj = new JSONObject(response.substring(1,response.length()-1));

                    //    JSONObject jObj =new JSONObject("{"+response}");
                    JSONObject jObj = new JSONObject(response);

                    //      JSONObject jObj =   new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));

                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session

                        progress.setVisibility(View.GONE);
                        tv_message.setVisibility(View.GONE);
                        dialog.dismiss();
                        Snackbar.make(getView(), "berhasil", Snackbar.LENGTH_LONG).show();

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("message");

                        Log.d(Constants.TAG,"failed");
                        progress.setVisibility(View.GONE);
                        tv_message.setVisibility(View.VISIBLE);
                        tv_message.setText(errorMsg);


                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();

                    Toast.makeText(getActivity(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                String e = VolleyErrorHelper.getMessage(error, getActivity());

                VolleyLog.d(AppController.TAG, "Error: " + e);


                Toast.makeText(getActivity(),
                        e, Toast.LENGTH_LONG).show();

                Log.d(Constants.TAG,"failed");
                progress.setVisibility(View.GONE);
                tv_message.setVisibility(View.VISIBLE);
                tv_message.setText(e);

                hideDialog();
            }
        }) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", apiKey);
                return headers;
            }


            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("old_password", old_password);
                params.put("new_password", new_password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }




/*
    private void changePasswordProcess(String old_password,String new_password){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
      //  user.setEmail(email);
        user.setOld_password(old_password);
        user.setNew_password(new_password);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.CHANGE_PASSWORD_OPERATION);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                if(resp.getResult().equals(Constants.SUCCESS)){
                    progress.setVisibility(View.GONE);
                    tv_message.setVisibility(View.GONE);
                    dialog.dismiss();
                    Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();

                }else {
                    progress.setVisibility(View.GONE);
                    tv_message.setVisibility(View.VISIBLE);
                    tv_message.setText(resp.getMessage());

                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Log.d(Constants.TAG,"failed");
                progress.setVisibility(View.GONE);
                tv_message.setVisibility(View.VISIBLE);
                tv_message.setText(t.getLocalizedMessage());


            }
        });
    }
*/


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
            pDialog.setMessage("Loading...");
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
