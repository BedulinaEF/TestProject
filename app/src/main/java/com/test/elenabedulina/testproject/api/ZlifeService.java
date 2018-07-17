package com.test.elenabedulina.testproject.api;

import com.test.elenabedulina.testproject.api.models.SignInRequest;
import com.test.elenabedulina.testproject.api.models.SignInResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ZlifeService {
   @POST("sign_in")
   @Headers("Content-type: application/json")
   Call<SignInResponse> signIn(@Body SignInRequest signInModel);

    //@POST("/calls/start")


    //@PUT("/calls/{ID}/end")



    //@PUT("/calls/{ID}/rating")




}
