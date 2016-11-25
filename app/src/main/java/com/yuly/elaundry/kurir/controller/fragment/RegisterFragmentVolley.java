/**
 * Author: Yuly Nurhidayati
 */
package com.yuly.elaundry.kurir.controller.fragment;


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

import com.yuly.elaundry.kurir.R;
import com.yuly.elaundry.kurir.controller.activity.MainActivity;
import com.yuly.elaundry.kurir.controller.app.AppConfig;
import com.yuly.elaundry.kurir.controller.app.AppController;
import com.yuly.elaundry.kurir.model.database.KonsumenDbHandler;
import com.yuly.elaundry.kurir.model.helper.SessionManager;
import com.yuly.elaundry.kurir.model.helper.VolleyErrorHelper;

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

                        String kurir_nama = inputNama.getText().toString().trim();
                        String kurir_alamat = inputAlamat.getText().toString().trim();
                        String kurir_noHp = inputNoHp.getText().toString().trim();
                        String kurir_email = inputEmail.getText().toString().trim();
                        String kurir_password = inputPassword.getText().toString().trim();
                        String kurir_password_confirm = inputConfirm.getText().toString().trim();


                        if (!kurir_nama.isEmpty() && !kurir_alamat.isEmpty() && !kurir_noHp.isEmpty() && !kurir_email.isEmpty() && !kurir_password.isEmpty() && !kurir_password_confirm.isEmpty()) {
                            if (kurir_password.equals(kurir_password_confirm)) {
                                registerUser(kurir_nama, kurir_alamat, kurir_noHp, kurir_email, kurir_password);
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
    private void registerUser(final String kurir_nama, final String kurir_alamat, final String kurir_nohp, final String kurir_email,
                              final String kurir_password) {
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
                        String kurir_id = jObj.getString("kurir_id");
                        String kurir_nama = user.getString("kurir_nama");
                        String kurir_alamat = user.getString("kurir_alamat");
                        String kurir_nohp = user.getString("kurir_nohp");
                        String kurir_email = user.getString("kurir_email");
                        String kurir_kunci_api = user.getString("kurir_kunci_api");
                        String kurir_dibuat_pada = user.getString("kurir_dibuat_pada");

                        // memasukkan data user ke database sqlite
                        db.addUser(kurir_nama, kurir_alamat, kurir_nohp, kurir_email, kurir_kunci_api, kurir_id, kurir_dibuat_pada);

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
                params.put("kurir_nama", kurir_nama);
                params.put("kurir_alamat",kurir_alamat);
                params.put("kurir_nohp",kurir_nohp);
                params.put("kurir_email", kurir_email);
                params.put("kurir_password", kurir_password);

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
