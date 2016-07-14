package com.yuly.elaundry.app;



import com.yuly.elaundry.models.ServerRequest;
import com.yuly.elaundry.models.ServerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RequestInterface {

    @POST("elaundry/v1/")
    Call<ServerResponse> operation(@Body ServerRequest request);

}
