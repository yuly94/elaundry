/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 */
package com.yuly.elaundry.kurir.fragment;



import android.app.ProgressDialog;
import android.content.Intent;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.yuly.elaundry.R;
import com.yuly.elaundry.kurir.activity.MainActivity;
import com.yuly.elaundry.kurir.app.AppConfig;
import com.yuly.elaundry.kurir.app.AppController;
import com.yuly.elaundry.kurir.helper.SQLiteHandler;
import com.yuly.elaundry.kurir.helper.SessionManager;
import com.yuly.elaundry.kurir.helper.VolleyErrorHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginFragmentVolley extends Fragment implements View.OnClickListener{
    private static final String TAG = LoginFragmentVolley.class.getSimpleName();
    private EditText        inputEmail;
    private EditText        inputPassword;
    private ProgressDialog  pDialog;
    private SessionManager  session;
    private SQLiteHandler   db;
    private TextView        tv_register,tv_reset_password;
    private Button          btnLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login,container,false);
        initViews(view);
        checkSesi();

        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        checkSesi();
    }


    private void initViews(View view){

        inputEmail = (EditText) view.findViewById(R.id.et_email);
        inputPassword = (EditText) view.findViewById(R.id.et_password);

        tv_register = (TextView)view.findViewById(R.id.tv_register);
        tv_reset_password = (TextView)view.findViewById(R.id.tv_reset_password);
        btnLogin = (Button) view.findViewById(R.id.btn_ChangePass);

        btnLogin.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        tv_reset_password.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.tv_register:
                goToRegister();
                break;

            case R.id.btn_ChangePass:

                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                // Apibila isian email dan password tidak kosong
                if (!email.isEmpty() && !password.isEmpty()) {
                    // memanggil fungsi chek login
                    checkLogin(email, password);
                } else {
                    // Memberitahu kurir untuk memasukkan password
                    Toast.makeText(getActivity(),
                            "Mohon masukkan password anda ", Toast.LENGTH_LONG)
                            .show();
                }

                break;
            case R.id.tv_reset_password:
                goToResetPassword();
                break;
        }
    }


    /**
     * function to verifikasi login dengan mengirimkan username dan password ke webserver
     * */
    private void checkLogin(final String kurir_email, final String kurir_password) {

        // Tag berikut digunakan untuk membatalkan request login
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response : " + response);
                hideDialog();

                try {

                  JSONObject jObj = new JSONObject(response);

                    boolean error = jObj.getBoolean("error");

                    // Mengecek respon webservice json apakah ada yang error atau tidak
                    if (!error) {
                        // Jika kurir berhasil login
                        // Mengecek sesi login
                        session.setLogin(true);

                        // Menyimpan data hasil respon webserver ke database sqlite
                        JSONObject kurir = jObj.getJSONObject("login");
                        String kurir_id = kurir.getString("kurir_id");
                        String kurir_nama = kurir.getString("kurir_nama");
                        String kurir_alamat = kurir.getString("kurir_alamat");
                        String kurir_nohp = kurir.getString("kurir_nohp");
                        String kurir_email = kurir.getString("kurir_email");
                        String kurir_kunci_api = kurir.getString("kurir_kunci_api");
                        String kurir_dibuat_pada = kurir.getString("kurir_dibuat_pada");

                        // Inserting row in users table
                        db.addUser(kurir_nama, kurir_alamat, kurir_nohp, kurir_email, kurir_kunci_api, kurir_id, kurir_dibuat_pada);

                        // Launch main activity
                       Intent intent = new Intent(getActivity(),
                                MainActivity.class);
                        startActivity(intent);
                     //   finish();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("message");

                            Toast.makeText(getActivity(),
                                    errorMsg, Toast.LENGTH_LONG).show();

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


                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
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

    private void goToResetPassword(){

        Fragment reset = new ResetPasswordFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_frame, reset).commit();

    }

    private void goToRegister(){

        Fragment register = new RegisterFragmentVolley();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_frame, register).commit();
    }

}
