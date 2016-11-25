/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 */
package com.yuly.elaundry.kurir.controller.fragment;



import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class ChangePassFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = ChangePassFragment.class.getSimpleName();
    private EditText inputOldPass;
    private EditText inputNewPass;
    private ProgressDialog pDialog;
    private SessionManager session;
    private KonsumenDbHandler db;
    private TextView tv_register,tv_reset_password;
    private Button btn_ChangePass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_changepass,container,false);
        initViews(view);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

    }


    private void initViews(View view){

        inputOldPass = (EditText) view.findViewById(R.id.et_email);
        inputNewPass = (EditText) view.findViewById(R.id.et_password);

        btn_ChangePass = (Button) view.findViewById(R.id.btn_ChangePass);

        btn_ChangePass.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_ChangePass:

                String oldPass = inputOldPass.getText().toString().trim();
                String newPass = inputNewPass.getText().toString().trim();

                // Check for empty data in the form
                if (!oldPass.isEmpty() && !newPass.isEmpty()) {
                    // login user
                    checkLogin(oldPass, newPass);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getActivity(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }

                break;
        }
    }




    /**
     * function to verify login details in mysql db
     * */
    private void checkLogin(final String email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response);
                hideDialog();

                try {

                  JSONObject jObj = new JSONObject(response);

                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);

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
                        db.addUser(nama, alamat, nohp, email, api, uid, created_at);

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


}
