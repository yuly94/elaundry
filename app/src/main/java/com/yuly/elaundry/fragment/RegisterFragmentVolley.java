/**
 * Author: Yuly Nurhidayati
 */
package com.yuly.elaundry.fragment;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.yuly.elaundry.R;

import com.yuly.elaundry.activity.MainActivity;
import com.yuly.elaundry.app.AppConfig;
import com.yuly.elaundry.app.AppController;
import com.yuly.elaundry.helper.SQLiteHandler;
import com.yuly.elaundry.helper.SessionManager;
import com.yuly.elaundry.helper.VolleyErrorHelper;

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
    private SQLiteHandler db;
    private Button btnRegister;
    private TextView btnLinkToLogin;
    private ProgressDialog pDialog;
    private SessionManager session;

    private AppCompatButton btn_register;
    private TextView tv_login;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login,container,false);
        initViews(view);
        checkSesi();
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        return view;
    }

    private void checkSesi(){

        // SQLite database handler
        db = new SQLiteHandler(getActivity());

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

        // Progress dialog
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);


        btn_register = (AppCompatButton)view.findViewById(R.id.btn_register);
        tv_login = (TextView)view.findViewById(R.id.tv_login);

        btn_register.setOnClickListener(this);
        tv_login.setOnClickListener(this);

/*
        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getActivity(),
                        LoginActivity.class);
                startActivity(i);
                finish();
            }
        });*/

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tv_login:
                goToLogin();
                break;

            case R.id.btn_register:


                // Register Button Click event

                        String nama = inputNama.getText().toString().trim();
                        String alamat = inputAlamat.getText().toString().trim();
                        String noHp = inputNoHp.getText().toString().trim();
                        String email = inputEmail.getText().toString().trim();
                        String password = inputPassword.getText().toString().trim();
                        String password_confirm = inputConfirm.getText().toString().trim();


                        if (!nama.isEmpty() && !alamat.isEmpty() && !noHp.isEmpty() && !email.isEmpty() && !password.isEmpty() && !password_confirm.isEmpty()) {
                            if (password.equals(password_confirm)) {
                                registerUser(nama, alamat, noHp, email, password);
                            } else {
                                Toast.makeText(getActivity(),
                                        "The Password Confirm not same", Toast.LENGTH_LONG)
                                        .show();
                            }
                        } else {
                            Toast.makeText(getActivity(),
                                    "Please enter your details!", Toast.LENGTH_LONG)
                                    .show();
                        }


                break;

        }

    }

    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    private void registerUser(final String nama, final String alamat, final String nohp, final String email,
                              final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response);
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String nama = user.getString("nama");
                        String alamat = user.getString("alamat");
                        String noHp = user.getString("nohp");
                        String email = user.getString("email");
                        String api = user.getString("api_key");
                        String created_at = user.getString("created_at");

                        // Inserting row in users table
                        db.addUser(nama, alamat, noHp, email, api, uid, created_at);

                        Toast.makeText(getActivity(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

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
                params.put("nama", nama);
                params.put("alamat",alamat);
                params.put("nohp",nohp);
                params.put("email", email);
                params.put("password", password);

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

        Fragment login = new LoginFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame,login);
        ft.commit();
    }
}
