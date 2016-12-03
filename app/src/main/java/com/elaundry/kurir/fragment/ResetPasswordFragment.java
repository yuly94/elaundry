package com.elaundry.kurir.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.elaundry.kurir.R;
import com.elaundry.kurir.app.AppConfig;
import com.elaundry.kurir.app.AppController;
import com.elaundry.kurir.helper.VolleyErrorHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ResetPasswordFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = ResetPasswordFragment.class.getSimpleName();

    private AppCompatButton btn_reset;
    private EditText et_email,et_code,et_password;
    private TextView tv_timer;
    private ProgressBar progress;
    private boolean isResetInitiated = false;
    private String email;
    private CountDownTimer countDownTimer;
    private ProgressDialog pDialog;
    private TextView tv_login;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lupa_password,container,false);
        initViews(view);

        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        return view;
    }

    private void initViews(View view){

        btn_reset = (AppCompatButton)view.findViewById(R.id.btn_reset);
        tv_timer = (TextView)view.findViewById(R.id.timer);
        et_code = (EditText)view.findViewById(R.id.et_code);
        et_email = (EditText)view.findViewById(R.id.et_email);
        et_password = (EditText)view.findViewById(R.id.et_password);
        et_password.setVisibility(View.GONE);
        et_code.setVisibility(View.GONE);
        tv_timer.setVisibility(View.GONE);
        btn_reset.setOnClickListener(this);
        progress = (ProgressBar)view.findViewById(R.id.progress);

        tv_login = (TextView)view.findViewById(R.id.tv_login);
        tv_login.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

                case R.id.tv_login:
                    goToLogin();
                    break;

                case R.id.btn_reset:

                if(!isResetInitiated) {

                    email = et_email.getText().toString();
                    if (!email.isEmpty()) {

                        initiateResetPasswordProcess(email);
                    } else {

                        Snackbar.make(getView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
                    }
                } else {

                    String code = et_code.getText().toString();
                    String password = et_password.getText().toString();

                    if(!code.isEmpty() && !password.isEmpty()){

                       finishResetPasswordProcess(email,code,password);
                    } else {

                        Snackbar.make(getView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
                    }

                }

                break;
        }
    }



/*
    private void initiateResetPasswordProcess(String email){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setEmail(email);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.RESET_PASSWORD_INITIATE);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();

                if(resp.getResult().equals(Constants.SUCCESS)){

                    Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();
                    et_email.setVisibility(View.GONE);
                    et_code.setVisibility(View.VISIBLE);
                    et_password.setVisibility(View.VISIBLE);
                    tv_timer.setVisibility(View.VISIBLE);
                    btn_reset.setText("Change Password");
                    isResetInitiated = true;
                    startCountdownTimer();

                } else {

                    Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();

                }
                progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                progress.setVisibility(View.INVISIBLE);
                Log.d(Constants.TAG,"failed");
                Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();

            }
        });
    }

    private void finishResetPasswordProcess(String email,String code, String password){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setEmail(email);
        user.setCode(code);
        user.setPassword(password);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.RESET_PASSWORD_FINISH);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();

                if(resp.getResult().equals(Constants.SUCCESS)){

                    Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();
                    countDownTimer.cancel();
                    isResetInitiated = false;
                    goToLogin();

                } else {

                    Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();

                }
                progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                progress.setVisibility(View.INVISIBLE);
                Log.d(Constants.TAG,"failed");
                Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();

            }
        });
    }*/


    /**
     * function to verify login details in mysql db
     * */
    private void initiateResetPasswordProcess(final String kurir_email) {
        // Tag used to cancel the request
        String tag_string_req = "req_Mereset";

        pDialog.setMessage("Mereset password ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_FORGOT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Mereset Response: " + response);
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

                        Snackbar.make(getView(), "Reset code successfully sent", Snackbar.LENGTH_LONG).show();
                        et_email.setVisibility(View.GONE);
                        et_code.setVisibility(View.VISIBLE);
                        et_password.setVisibility(View.VISIBLE);
                        tv_timer.setVisibility(View.VISIBLE);
                        btn_reset.setText("Change Password");
                        isResetInitiated = true;
                        startCountdownTimer();

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

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    /**
     * function to verify login details in mysql db
     * */
    private void finishResetPasswordProcess(final String kurir_email,final String kurir_kode_password,final String kurir_password) {
        // Tag used to cancel the request
        String tag_string_req = "req_Mereset";

        pDialog.setMessage("Memperbarui password ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_RESET, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Mereset Response: " + response);
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

                        Snackbar.make(getView(), "Reset successfully", Snackbar.LENGTH_LONG).show();
                        countDownTimer.cancel();
                        isResetInitiated = false;
                        goToLogin();

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
                params.put("kurir_kode_reset", kurir_kode_password);
                params.put("kurir_password", kurir_password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    
    private void startCountdownTimer(){
        countDownTimer = new CountDownTimer(180000, 1000) {

            public void onTick(long millisUntilFinished) {
                tv_timer.setText("Time remaining : " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                Snackbar.make(getView(), "Waktu habis, silahkan mencoba kembali", Snackbar.LENGTH_LONG).show();
                 goToLogin();
            }
        }.start();
    }

   private void goToLogin(){

        Fragment login = new LoginFragmentVolley();


       FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
       fragmentManager.beginTransaction()
               .replace(R.id.fragment_frame, login).commit();
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
