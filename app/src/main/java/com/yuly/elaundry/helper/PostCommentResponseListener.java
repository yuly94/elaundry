package com.yuly.elaundry.helper;

import com.android.volley.VolleyError;

/**
 * Created by anonymous on 18/07/16.
 */
public interface PostCommentResponseListener {
    public void requestStarted();
    public void requestCompleted();
    public void requestEndedWithError(VolleyError error);
}