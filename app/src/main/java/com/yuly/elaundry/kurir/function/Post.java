package com.yuly.elaundry.kurir.function;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.yuly.elaundry.kurir.app.AppConfig;
import com.yuly.elaundry.kurir.app.AppController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by anonymous on 07/01/16.
 */
public class Post {

    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */

    // Progress dialog
  //  pDialog = new ProgressDialog(this);
//    pDialog.setCancelable(false);


    private void PostTask() {
        // Tag used to cancel the request
        String tag_string_req = "req_task";

    //    pDialog.setMessage("Create Task ...");
    //    showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_TASK, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("TAG", "Login Response: " + response.toString());
      //          hideDialog();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", "Login Error: " + error.getMessage());
        //        Toast.makeText(getApplicationContext(),
         //               error.getMessage(), Toast.LENGTH_LONG).show();
           //     hideDialog();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //	headers.put("Content-Type", "application/json");
                headers.put("Authorization", "18dcebdf73d93678e3ad59ed7fa8b275");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("task", "abc@abc.com");
                //	params.put("email", "abc@abc.com");
                //	params.put("password", "abc");

                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
