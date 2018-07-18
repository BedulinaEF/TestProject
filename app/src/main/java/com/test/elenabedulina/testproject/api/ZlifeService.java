package com.test.elenabedulina.testproject.api;

import com.test.elenabedulina.testproject.api.models.CallStartResponse;
import com.test.elenabedulina.testproject.api.models.CallsIdEndRequest;
import com.test.elenabedulina.testproject.api.models.RaitingRequest;
import com.test.elenabedulina.testproject.api.models.SignInRequest;
import com.test.elenabedulina.testproject.api.models.SignInResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ZlifeService {
   @POST("sign_in")
   @Headers("Content-type: application/json")
   Call<SignInResponse> signIn(@Body SignInRequest signInModel);

    @POST("calls/start")
   Call<CallStartResponse> getId();


    @PUT("calls/{ID}/end")
    Call<CallsIdEndRequest> putID(@Path("ID") String id,@Body CallsIdEndRequest callsIdEndRequest);


    @PUT("calls/{ID}/rating")
    Call<RaitingRequest> putAnswer(@Path("ID") String id, @Body RaitingRequest raitingRequest);





}
