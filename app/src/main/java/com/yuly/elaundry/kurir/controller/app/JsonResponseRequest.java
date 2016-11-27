package com.yuly.elaundry.kurir.controller.app;

/**
 * Created by anonymous on 27/11/16.
 */

/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.android.volley.Request;



import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * A canned request for retrieving the response body at a given URL as a String.
 */
public class JsonResponseRequest extends Request<JSONObject> {
    private final Listener<JSONObject> mListener;

    /**
     * Creates a new request with the given method.
     *
     * @param method        the request {@link com.android.volley.Request.Method} to use
     * @param url           URL to fetch the string at
     * @param listener      Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public JsonResponseRequest(int method, String url, Listener<JSONObject> listener,
                               ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
    }

    /**
     * Creates a new GET request.
     *
     * @param post
     * @param jsonObject
     * @param url           URL to fetch the string at
     * @param listener      Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public JsonResponseRequest(int post, JSONObject jsonObject, String url, Listener<JSONObject> listener, ErrorListener errorListener) {
        this(Request.Method.GET, url, listener, errorListener);
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(parsed);
        } catch (JSONException e) {
            jsonObject = null;
        }
        return Response.success(jsonObject, HttpHeaderParser.parseCacheHeaders(response));
    }
}
