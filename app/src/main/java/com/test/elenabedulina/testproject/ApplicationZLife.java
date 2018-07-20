package com.test.elenabedulina.testproject;

import android.app.Application;
import android.content.SharedPreferences;

import com.test.elenabedulina.testproject.api.ZlifeService;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApplicationZLife extends Application {
    private String clientData;
    private String uIDData;
    private String tokenData;
    private ZlifeService zlifeService;

    public ZlifeService getZlifeService() {
        return zlifeService;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        addHeaders();
    }
    public void addHeaders(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        takePref();
                        Request request = chain.request().newBuilder()
                                .addHeader(Constants.KEY_CLIENT_HEADER, clientData)
                                .addHeader(Constants.KEY_CLIENT_UID, uIDData)
                                .addHeader(Constants.KEY_CLIENT_TOKEN, tokenData)
                                .build();
                        return chain.proceed(request);
                    }

                })

                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pc-staging.zlife.kz/api/v1/patient/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        zlifeService = retrofit.create(ZlifeService.class);
    }

    public void takePref() {
        SharedPreferences sharedPref = this.getSharedPreferences(Constants.KEY_SHARED_PREF, LauncherActivity.MODE_PRIVATE);
//        String headerClient = getResources().getString(R.string.key_header_client);
//        String headerUID = getResources().getString(R.string.key_header_uid);
//        String headerToken = getResources().getString(R.string.key_header_token);
        clientData = sharedPref.getString(Constants.KEY_CLIENT_HEADER, Constants.KEY_HEADER_CLIENT);
        uIDData = sharedPref.getString(Constants.KEY_CLIENT_UID, Constants.KEY_HEADER_UID);
        tokenData = sharedPref.getString(Constants.KEY_CLIENT_TOKEN, Constants.KEY_HEADER_TOKEN);
    }
}
