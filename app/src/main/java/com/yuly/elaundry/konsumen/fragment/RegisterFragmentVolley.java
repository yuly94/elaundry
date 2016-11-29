/**
 * Author: Yuly Nurhidayati
 */
package com.yuly.elaundry.konsumen.fragment;


import android.app.ProgressDialog;

import android.content.Intent;
import android.os.Bundle;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.yuly.elaundry.konsumen.R;

import com.yuly.elaundry.konsumen.activity.MainActivity;
import com.yuly.elaundry.konsumen.app.AppConfig;
import com.yuly.elaundry.konsumen.app.AppController;
import com.yuly.elaundry.konsumen.helper.KonsumenDbHandler;
import com.yuly.elaundry.konsumen.helper.SessionManager;
import com.yuly.elaundry.konsumen.helper.VolleyErrorHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterFragmentVolley extends Fragment implements View.OnClickListener{
    private static final String TAG = RegisterFragmentVolley.class.getSimpleName();
    private EditText inputNama;
    private EditText inputAlamat;
    private EditText inputNoHp;
    private EditText inputEmail;
    private EditText inputPassword;
    private EditText inputConfirm;
    private KonsumenDbHandler db;
    private ProgressDialog pDialog;
    private SessionManager session;

    private AppCompatButton btn_register;
    private TextView tv_login;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register,container,false);
        initViews(view);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        return view;
    }

    private void checkSesi(){

        // SQLite database handler
        db = new KonsumenDbHandler(getActivity());

        // Session manager
        session = new SessionManager(getActivity());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            //  finish();
        }

    }

    private void initViews(View view){

        inputNama = (EditText) view.findViewById(R.id.et_name);
        inputAlamat = (EditText) view.findViewById(R.id.et_address);
        inputNoHp = (EditText) view.findViewById(R.id.et_phone);
        inputEmail = (EditText) view.findViewById(R.id.et_email);
        inputPassword = (EditText) view.findViewById(R.id.et_password);
        inputConfirm = (EditText) view.findViewById(R.id.et_confirm_password);

        btn_register = (AppCompatButton)view.findViewById(R.id.btn_register);
        tv_login = (TextView)view.findViewById(R.id.tv_login);

        btn_register.setOnClickListener(this);
        tv_login.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tv_login:
                goToLogin();
                break;

            case R.id.btn_register:

                // Register Button Click event

                        String konsumen_nama = inputNama.getText().toString().trim();
                        String konsumen_alamat = inputAlamat.getText().toString().trim();
                        String konsumen_noHp = inputNoHp.getText().toString().trim();
                        String konsumen_email = inputEmail.getText().toString().trim();
                        String konsumen_password = inputPassword.getText().toString().trim();
                        String konsumen_password_confirm = inputConfirm.getText().toString().trim();


                        if (!konsumen_nama.isEmpty() && !konsumen_alamat.isEmpty() && !konsumen_noHp.isEmpty() && !konsumen_email.isEmpty() && !konsumen_password.isEmpty() && !konsumen_password_confirm.isEmpty()) {
                            if (konsumen_password.equals(konsumen_password_confirm)) {
                                registerUser(konsumen_nama, konsumen_alamat, konsumen_noHp, konsumen_email, konsumen_password);
                            } else {
                                Toast.makeText(getActivity(),
                                        "Password konfirmasi tidak sama", Toast.LENGTH_LONG)
                                        .show();
                            }
                        } else {
                            Toast.makeText(getActivity(),
                                    "Mohon masukkan data secara lengkap", Toast.LENGTH_LONG)
                                    .show();
                        }


                break;

        }

    }

    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    private void registerUser(final String konsumen_nama, final String konsumen_alamat, final String konsumen_nohp, final String konsumen_email,
                              final String konsumen_password) {
        // Tag used to cancel the request
        String tag_string_req = "req_registrasi";

        pDialog.setMessage("Mendaftarkan, mohon tunggu ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_REGISTRASI, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response registrasi: " + response);
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);

                    boolean error = jObj.getBoolean("error");

                    // Mengecek respon webservice json apakah ada yang error atau tidak
                    if (!error) {

/*
                        // Menyimpan data hasil respon webserver ke database sqlite

                        JSONObject user = jObj.getJSONObject("registrasi");
                        String konsumen_id = jObj.getString("konsumen_id");
                        String konsumen_nama = user.getString("konsumen_nama");
                        String konsumen_alamat = user.getString("konsumen_alamat");
                        String konsumen_nohp = user.getString("konsumen_nohp");
                        String konsumen_email = user.getString("konsumen_email");
                        String konsumen_kunci_api = user.getString("konsumen_kunci_api");
                        String konsumen_dibuat_pada = user.getString("konsumen_dibuat_pada");

                        // memasukkan data user ke database sqlite
                        db.addUser(konsumen_nama, konsumen_alamat, konsumen_nohp, konsumen_email, konsumen_kunci_api, konsumen_id, konsumen_dibuat_pada);

*/

                        Toast.makeText(getActivity(), "Registrasi anda berhasil, silahkan check email untuk aktifasi", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        goToLogin();

                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(getActivity(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                String e = VolleyErrorHelper.getMessage(error, getActivity());
                VolleyLog.d(AppController.TAG, "Error: " + e);


                Toast.makeText(getActivity(),
                        e, Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {



            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("konsumen_nama", konsumen_nama);
                params.put("konsumen_alamat",konsumen_alamat);
                params.put("konsumen_nohp",konsumen_nohp);
                params.put("konsumen_email", konsumen_email);
                params.put("konsumen_password", konsumen_password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void goToLogin(){

        Fragment login = new LoginFragmentVolley();

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_frame, login).commit();
    }
}
