package com.yuly.elaundry.konsumen.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;

import android.content.Intent;
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

import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.yuly.elaundry.konsumen.R;
import com.yuly.elaundry.konsumen.activity.LoginActivityNew;
import com.yuly.elaundry.konsumen.activity.MainActivity;
import com.yuly.elaundry.konsumen.app.AppConfig;
import com.yuly.elaundry.konsumen.app.AppController;
import com.yuly.elaundry.konsumen.app.Constants;
import com.yuly.elaundry.konsumen.helper.KonsumenDbHandler;
import com.yuly.elaundry.konsumen.helper.SessionManager;
import com.yuly.elaundry.konsumen.helper.VolleyErrorHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Yuly Nurhidayati
 * URL: elaundry.pe.hu
 * */

public class ProfileFragment extends Fragment {

    private static final String TAG = ProfileFragment.class.getSimpleName();

    //Creating Views
    private SessionManager session;

    private TextView tv_name,tv_email,tv_message;
  //  private SharedPreferences pref;
    private EditText et_password_lama,et_password_baru;
    private AlertDialog dialog;
   // private ProgressBar progress;
    private ProgressDialog pDialog;
    private String konsumen_kunci_api_auth;
    private KonsumenDbHandler db;
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

                enableView();
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String konsumen_nama = et_Nama.getText().toString();
                String konsumen_alamat = et_Alamat.getText().toString();

                String konsumen_nohp = et_Telepon.getText().toString();
                String konsumen_email = et_Email.getText().toString();
                if(!konsumen_nama.isEmpty() && !konsumen_alamat.isEmpty()&&
                        !konsumen_nohp.isEmpty()&& !konsumen_email.isEmpty()){

                    updateProfile(konsumen_nama,konsumen_alamat,konsumen_nohp,konsumen_email);

                }else {
                    if (getView()!=null){
                        Snackbar.make(getView(), R.string.input_masih_kosong, Snackbar.LENGTH_LONG).show();
                    }
                    Log.d(TAG,getString(R.string.gagal_update_profile));
                }

            }
        });

        // SqLite database handler
        db = new KonsumenDbHandler(getContext());

        try {


        updateDataProfile();
        } catch (Exception e) {
            Log.e(ProfileFragment.class.getSimpleName(), String.valueOf(e));
        }
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
        HashMap<String, String> konsumen = db.getUserDetails();

        String konsumen_nama = konsumen.get("konsumen_nama");
        String konsumen_alamat = konsumen.get("konsumen_alamat");
        String konsumen_nohp = konsumen.get("konsumen_nohp");
        String konsumen_email = konsumen.get("konsumen_email");
        konsumen_kunci_api_auth = konsumen.get("konsumen_kunci_api");

        et_Nama.setText(konsumen_nama);
        et_Email.setText(konsumen_email);
        et_Alamat.setText(konsumen_alamat);
        et_Telepon.setText(konsumen_nohp);
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
        et_password_lama = (EditText)view.findViewById(R.id.et_password_lama);
        et_password_baru = (EditText)view.findViewById(R.id.et_password_baru);
        tv_message = (TextView)view.findViewById(R.id.tv_message);
        //progress = (ProgressBar)view.findViewById(R.id.progress);
        builder.setView(view);
        builder.setTitle(R.string.mengganti_password);
        builder.setPositiveButton(R.string.mengganti_password, new DialogInterface.OnClickListener() {

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
                String konsumen_password_lama = et_password_lama.getText().toString();
                String konsumen_password_baru = et_password_baru.getText().toString();
                if(!konsumen_password_lama.isEmpty() && !konsumen_password_baru.isEmpty()){

                     changePasswordProcess(konsumen_password_lama,konsumen_password_baru);


                }else {

                    if (getView()!=null){
                        Snackbar.make(getView(), R.string.field_empty, Snackbar.LENGTH_LONG).show();
                    }

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

            session.setLogin(false);
            db.deleteUsers();

            // Launching the login activity
            Intent intent = new Intent(getActivity(), LoginActivityNew.class);
            startActivity(intent);

            getActivity().finish();
            System.exit(0);
        }


        return super.onOptionsItemSelected(item);
    }



    /**
     * Making json array request
     * @param konsumen_nama   nama konsumen
     * @param konsumen_email  email konsumen
     * @param konsumen_alamat alamat konsumen
     * @param konsumen_nohp   nohp konsumen
     */

    private void updateProfile(final String konsumen_nama, final String konsumen_alamat, final String konsumen_nohp, final String konsumen_email){

        // Tag used to cancel the request
        String tag_string_req = "req_profile";

        pDialog.setMessage(getString(R.string.mengupdate_profile));
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_CHANGEPROFILE, new Response.Listener<String>() {




            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Update profile Response : " + response);
                hideDialog();

                try {

                    JSONObject jObj = new JSONObject(response);

                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // Now store the user in SQLite


                        JSONObject user = jObj.getJSONObject("konsumen");
                        String konsumen_id = user.getString("konsumen_id");
                        String konsumen_nama = user.getString("konsumen_nama");
                        String konsumen_alamat = user.getString("konsumen_alamat");
                        String konsumen_nohp = user.getString("konsumen_nohp");
                        String konsumen_email = user.getString("konsumen_email");
                        String konsumen_kunci_api = user.getString("konsumen_kunci_api");
                        String konsumen_dibuat_pada = user
                                .getString("konsumen_dibuat_pada");

                        // Inserting row in users table
                        //   db.updatePassword(api, uid, created_at);

                        if (db.updateProfile(konsumen_nama, konsumen_alamat, konsumen_nohp, konsumen_email, konsumen_kunci_api, konsumen_id, konsumen_dibuat_pada)!=0) {


                            updateDataProfile();

                            if(getView()!=null) {
                                Snackbar.make(getView(), R.string.update_profile_berhasil, Snackbar.LENGTH_LONG).show();
                            }
                            disableView();

                        } else {
                            if(getView()!=null) {
                                Snackbar.make(getView(), R.string.update_profile_gagal, Snackbar.LENGTH_LONG).show();
                            }

                            Log.d(TAG,getString(R.string.update_profile_gagal));
                        }

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("message");

                        Log.d(Constants.TAG,errorMsg);

                        if(getView()!=null) {
                            Snackbar.make(getView(), R.string.update_profile_gagal, Snackbar.LENGTH_LONG).show();
                        }

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();

                    Toast.makeText(getActivity(), getString(R.string.json_request_error) + e.getMessage(), Toast.LENGTH_LONG).show();

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
             //   progress.setVisibility(View.GONE);
                //tv_message.setVisibility(View.VISIBLE);
                //tv_message.setText(e);

                hideDialog();
            }
        }) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                params.put("Authorization", konsumen_kunci_api_auth );

                Log.d("Params",params.toString());
                return params;
            }


            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to change password
                Map<String, String> params = new HashMap<>();
                params.put("konsumen_nama", konsumen_nama);
                params.put("konsumen_alamat", konsumen_alamat);
                params.put("konsumen_nohp", konsumen_nohp);
                params.put("konsumen_email",konsumen_email);

                Log.d("Params",params.toString());

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    /**
     * Making json array request
     * @param konsumen_password_lama password lama
     * @param konsumen_password_baru password baru
     */

    private void changePasswordProcess(final String konsumen_password_lama,final String konsumen_password_baru){

        // Tag used to cancel the request
        String tag_string_req = getString(R.string.permintaan_mengganti_password);

        pDialog.setMessage(getString(R.string.menggganti_password));
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_GANTI_PASSWORD, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Mengganti password response : " + response);
                hideDialog();

                try {

                    JSONObject jObj = new JSONObject(response);

                    Log.d("response", jObj.toString());

                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // Now store the user in SQLite


                        JSONObject user = jObj.getJSONObject("konsumen");
                        String konsumen_id = user.getString("konsumen_id");
                        String konsumen_kunci_api = user.getString("konsumen_kunci_api");
                        String konsumen_dibuat_pada = user
                                .getString("konsumen_dibuat_pada");

                        // Inserting row in users table

                        if (db.updatePassword(konsumen_kunci_api, konsumen_id, konsumen_dibuat_pada) !=0){


                            updateDataProfile();

                            if(getView()!=null) {
                                Snackbar.make(getView(), R.string.update_password_berhasil, Snackbar.LENGTH_LONG).show();
                            }

                            dialog.hide();

                        } else {
                            if(getView()!=null) {
                                Snackbar.make(getView(), R.string.update_password_gagal, Snackbar.LENGTH_LONG).show();
                            }

                            Log.d(TAG,getString(R.string.update_password_gagal));
                        }

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("message");

                        Log.d(Constants.TAG,errorMsg);

                        dialog.hide();

                        if(getView()!=null) {
                            Snackbar.make(getView(), errorMsg, Snackbar.LENGTH_LONG).show();
                        }

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();

                    Toast.makeText(getActivity(), getString(R.string.json_request_error) + e.getMessage(), Toast.LENGTH_LONG).show();

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                String e = VolleyErrorHelper.getMessage(error, getActivity());

                VolleyLog.d(AppController.TAG, "Error : " + e);


                Toast.makeText(getActivity(),
                        e, Toast.LENGTH_LONG).show();

                Log.d(Constants.TAG,"failed");
                //   progress.setVisibility(View.GONE);
                tv_message.setVisibility(View.VISIBLE);
                tv_message.setText(e);

                hideDialog();
            }
        }) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                params.put("Authorization", konsumen_kunci_api_auth );

                Log.d("Params",params.toString());
                return params;
            }


            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to change password
                Map<String, String> params = new HashMap<>();
                params.put("konsumen_password_lama", konsumen_password_lama);
                params.put("konsumen_password_baru", konsumen_password_baru);

                Log.d("Params",params.toString());

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
            pDialog.setMessage(getString(R.string.memuat));
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
