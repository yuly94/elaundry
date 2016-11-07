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
import com.android.volley.NetworkResponse;
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
import com.yuly.elaundry.helper.PostCommentResponseListener;
import com.yuly.elaundry.helper.SQLiteHandler;
import com.yuly.elaundry.helper.SessionManager;
import com.yuly.elaundry.helper.VolleyErrorHelper;
import com.yuly.elaundry.models.User;

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
    private EditText et_Nama,et_Alamat,et_Email,et_Telepon;
    private Button btnEdit,btnSave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile_new, container,
                false);

        et_Nama = (EditText) v.findViewById(R.id.et_name);
        et_Email = (EditText) v.findViewById(R.id.et_email);
        et_Alamat = (EditText) v.findViewById(R.id.et_address);
        et_Telepon = (EditText) v.findViewById(R.id.et_phone);
        btnEdit = (Button) v.findViewById(R.id.btn_edit);
        btnSave = (Button) v.findViewById(R.id.btn_save);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // startActivity(new Intent(getActivity(), PemesananActivity.class));
                enableView();



            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  startActivity(new Intent(getActivity(), PemesananActivity.class));

                String nama = et_Nama.getText().toString();
                String alamat = et_Alamat.getText().toString();

                String telepon = et_Telepon.getText().toString();
                String email = et_Email.getText().toString();
                if(!nama.isEmpty() && !alamat.isEmpty()&&
                        !telepon.isEmpty()&& !email.isEmpty()){


                    changeProfile(nama,alamat,telepon,email);

                }else {

                    Snackbar.make(getView(), R.string.field_empty, Snackbar.LENGTH_LONG).show();
                    Log.d(TAG,"Update profile failed");
                }

            }
        });

        // SqLite database handler
        db = new SQLiteHandler(getContext());

        updateDataProfile();
        disableView();

        return v;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // findViews();
        setHasOptionsMenu(true);
    }




    private void updateDataProfile(){

        // session manager
        session = new SessionManager(getContext());

        // Fetching user details from SQLite
        HashMap<String, String> user = db.getUserDetails();

        String nama = user.get("nama");
        String alamat = user.get("alamat");
        String telepon = user.get("telepon");
        String email = user.get("email");
        apiKey = user.get("api");

        et_Nama.setText(nama);
        et_Email.setText(email);
        et_Alamat.setText(alamat);
        et_Telepon.setText(telepon);
    }


    private void disableView(){
        et_Nama.setEnabled(false);
        et_Email.setEnabled(false);
        et_Alamat.setEnabled(false);
        et_Telepon.setEnabled(false);
        btnSave.setEnabled(false);
        btnEdit.setEnabled(true);

    }

    private void enableView(){
        et_Nama.setEnabled(true);
        et_Email.setEnabled(true);
        et_Alamat.setEnabled(true);
        et_Telepon.setEnabled(true);
        btnEdit.setEnabled(false);
        btnSave.setEnabled(true);
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

                     changePasswordProcess(old_password,new_password);


                }else {

                    Snackbar.make(getView(), R.string.field_empty, Snackbar.LENGTH_LONG).show();

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



    /**
     * Making json array request
     * @param nama
     * @param email
     * @param alamat
     * @param telepon
     */

    private void changeProfile(final String nama,final String alamat,final String telepon,final String email){

        // Tag used to cancel the request
        String tag_string_req = "req_profile";

        pDialog.setMessage("Updating your profile, please wait ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_CHANGEPROFILE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response);
                hideDialog();

                try {

                    JSONObject jObj = new JSONObject(response);

                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // Now store the user in SQLite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String nama = user.getString("nama");
                        String alamat = user.getString("alamat");
                        String telepon = user.getString("nohp");
                        String email = user.getString("email");
                        String api = user.getString("api_key");
                        String created_at = user
                                .getString("created_at");

                        // Inserting row in users table
                        //   db.updatePassword(api, uid, created_at);

                        if (db.updateProfile(nama, alamat, telepon, email, uid, created_at)!=0) {


                            updateDataProfile();

                            if(getView()!=null) {
                                Snackbar.make(getView(), R.string.update_profile_success, Snackbar.LENGTH_LONG).show();
                            }
                            disableView();

                        } else {
                            Log.d(TAG,"update password failed, please try again");
                        }

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("message");

                        Log.d(Constants.TAG,errorMsg);

                        if(getView()!=null) {
                            Snackbar.make(getView(), R.string.update_profile_failed, Snackbar.LENGTH_LONG).show();
                        }

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
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                params.put("Authorization", apiKey);

                Log.d("Params",params.toString());
                return params;
            }


            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to change password
                Map<String, String> params = new HashMap<String, String>();
                params.put("nama", nama);
                params.put("alamat", alamat);
                params.put("telepon", telepon);
                params.put("email",email);

                Log.d("Params",params.toString());

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }





    /**
     * Making json array request
     * @param old_password
     * @param new_password
     */

    private void changePasswordProcess(final String old_password,final String new_password){

       // Tag used to cancel the request
        String tag_string_req = "req_change_pass";

        pDialog.setMessage("Changing password, please wait ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_CHANGEPASS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response);
                hideDialog();

                try {

                    JSONObject jObj = new JSONObject(response);

                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // password successfully changed

                        // Now store the user in SQLite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String nama = user.getString("nama");
                        String alamat = user.getString("alamat");
                        String nohp = user.getString("nohp");
                        String email = user.getString("email");
                        String api = user.getString("api_key");
                        String created_at = user
                                .getString("created_at");

                        // Inserting row in users table
                     //   db.updatePassword(api, uid, created_at);

                        if (db.updatePassword(api, uid, created_at)!=0) {

                            progress.setVisibility(View.GONE);
                            tv_message.setVisibility(View.GONE);
                            updateDataProfile();
                            dialog.dismiss();
                            Snackbar.make(getView(), "berhasil", Snackbar.LENGTH_LONG).show();
                        } else {
                            Log.d(TAG,"update password failed, please try again");
                        }

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
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                params.put("Authorization", apiKey);
                return params;
            }


            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to change password
                Map<String, String> params = new HashMap<String, String>();
                params.put("password_lama", old_password);
                params.put("password_baru", new_password);


                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }





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
