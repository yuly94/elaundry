package com.yuly.elaundry.kurir.controller.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
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

import com.yuly.elaundry.kurir.R;
import com.yuly.elaundry.kurir.controller.app.AppConfig;
import com.yuly.elaundry.kurir.controller.app.AppController;
import com.yuly.elaundry.kurir.controller.app.Constants;
import com.yuly.elaundry.kurir.model.database.KonsumenDbHandler;
import com.yuly.elaundry.kurir.model.helper.SessionManager;
import com.yuly.elaundry.kurir.model.helper.VolleyErrorHelper;

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
    private String kurir_kunci_api_auth;
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

                String kurir_nama = et_Nama.getText().toString();
                String kurir_alamat = et_Alamat.getText().toString();

                String kurir_nohp = et_Telepon.getText().toString();
                String kurir_email = et_Email.getText().toString();
                if(!kurir_nama.isEmpty() && !kurir_alamat.isEmpty()&&
                        !kurir_nohp.isEmpty()&& !kurir_email.isEmpty()){

                    updateProfile(kurir_nama,kurir_alamat,kurir_nohp,kurir_email);

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
        HashMap<String, String> kurir = db.getUserDetails();

        String kurir_nama = kurir.get("kurir_nama");
        String kurir_alamat = kurir.get("kurir_alamat");
        String kurir_nohp = kurir.get("kurir_nohp");
        String kurir_email = kurir.get("kurir_email");
        kurir_kunci_api_auth = kurir.get("kurir_kunci_api");

        et_Nama.setText(kurir_nama);
        et_Email.setText(kurir_email);
        et_Alamat.setText(kurir_alamat);
        et_Telepon.setText(kurir_nohp);
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
                String kurir_password_lama = et_password_lama.getText().toString();
                String kurir_password_baru = et_password_baru.getText().toString();
                if(!kurir_password_lama.isEmpty() && !kurir_password_baru.isEmpty()){

                    changePasswordProcess(kurir_password_lama,kurir_password_baru);


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

            getActivity().finish();
            System.exit(0);
        }


        return super.onOptionsItemSelected(item);
    }



    /**
     * Making json array request
     * @param kurir_nama   nama kurir
     * @param kurir_email  email kurir
     * @param kurir_alamat alamat kurir
     * @param kurir_nohp   nohp kurir
     */

    private void updateProfile(final String kurir_nama, final String kurir_alamat, final String kurir_nohp, final String kurir_email){

        // Tag used to cancel the request
        String tag_string_req = "req_profile";

        pDialog.setMessage(getString(R.string.mengupdate_profile));
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_UPDATE_PROFILE, new Response.Listener<String>() {

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


                        JSONObject user = jObj.getJSONObject("kurir");
                        String kurir_id = user.getString("kurir_id");
                        String kurir_nama = user.getString("kurir_nama");
                        String kurir_alamat = user.getString("kurir_alamat");
                        String kurir_nohp = user.getString("kurir_nohp");
                        String kurir_email = user.getString("kurir_email");
                        String kurir_kunci_api = user.getString("kurir_kunci_api");
                        String kurir_dibuat_pada = user
                                .getString("kurir_dibuat_pada");

                        // Inserting row in users table
                        //   db.updatePassword(api, uid, created_at);

                        if (db.updateProfile(kurir_nama, kurir_alamat, kurir_nohp, kurir_email, kurir_kunci_api, kurir_id, kurir_dibuat_pada)!=0) {


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
                tv_message.setVisibility(View.VISIBLE);
                tv_message.setText(e);

                hideDialog();
            }
        }) {


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                params.put("Authorization", kurir_kunci_api_auth );

                Log.d("Params",params.toString());
                return params;
            }


            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to change password
                Map<String, String> params = new HashMap<>();
                params.put("kurir_nama", kurir_nama);
                params.put("kurir_alamat", kurir_alamat);
                params.put("kurir_nohp", kurir_nohp);
                params.put("kurir_email",kurir_email);

                Log.d("Params",params.toString());

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    /**
     * Making json array request
     * @param kurir_password_lama password lama
     * @param kurir_password_baru password baru
     */

    private void changePasswordProcess(final String kurir_password_lama,final String kurir_password_baru){

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


                        JSONObject user = jObj.getJSONObject("kurir");
                        String kurir_id = user.getString("kurir_id");
                        String kurir_kunci_api = user.getString("kurir_kunci_api");
                        String kurir_dibuat_pada = user
                                .getString("kurir_dibuat_pada");

                        // Inserting row in users table

                        if (db.updatePassword(kurir_kunci_api, kurir_id, kurir_dibuat_pada) !=0){


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
                params.put("Authorization", kurir_kunci_api_auth );

                Log.d("Params",params.toString());
                return params;
            }


            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to change password
                Map<String, String> params = new HashMap<>();
                params.put("kurir_password_lama", kurir_password_lama);
                params.put("kurir_password_baru", kurir_password_baru);

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

    /**
     * finish all activities ( quit the app )
     */
    private void quitApp() {
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("ACTION_QUIT");
        LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(broadcastIntent);

        getActivity().finish();
        System.exit(0);
    }

}
