package com.test.elenabedulina.testproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.test.elenabedulina.testproject.api.ZlifeService;
import com.test.elenabedulina.testproject.api.models.SignInRequest;
import com.test.elenabedulina.testproject.api.models.SignInResponse;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LauncherActivity extends BaseActivity {
    private EditText etInn;
    private EditText etPassword;
    private ZlifeService zlifeService;
    private final String TAG=LauncherActivity.class.getSimpleName();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        etInn = findViewById(R.id.et_inn_id);
        etPassword = findViewById(R.id.et_password_id);


        Button btnLogin = findViewById(R.id.button_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                makePostSignIn();

            }
        });
    }

    private boolean checkInput() {
        String regexInn = "[0-9]{12}";
        String regexPassword = "[a-zA-Z0-9]{6,12}";
        boolean innIsCorrect = etInn.getText().toString().matches(regexInn);
        boolean passwordIsCorrect = etPassword.getText().toString().matches(regexPassword);

        if (!innIsCorrect) {
            etInn.setError(getString(R.string.wrong_inn));
        }

        if (!passwordIsCorrect) {
            etPassword.setError(getString(R.string.wrong_password));
        }

        return innIsCorrect && passwordIsCorrect;
    }

    public void makePostSignIn() {
        showProgress();
        doSingIn();


    }

    private void startCallActivity(String number) {

        Intent launcherIntent = new Intent(LauncherActivity.this,
                CallActivity.class);
        launcherIntent.putExtra(Constants.CALL_CENTER_NUMBER_NAME, number);

        if (checkInput()) {
            startActivity(launcherIntent);
        }
    }

    private void doSingIn() {
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pc-staging.zlife.kz/api/v1/patient/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        zlifeService = retrofit.create(ZlifeService.class);
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setIin(etInn.getText().toString());
        signInRequest.setPassword(etPassword.getText().toString());
            zlifeService.signIn(signInRequest).enqueue(new Callback<SignInResponse>() {
                @Override
                public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {
                    if (response.code()>=200&&response.code()<300) {
                        SignInResponse bodyResponse = response.body();
                        String numberCall=bodyResponse.getContent().getCallCenter();
                        startCallActivity(numberCall);

                    } else {

                        onFailure(call, new Exception(response.message()));

                    }
                    hideProgress();
                }

                @Override
                public void onFailure(Call<SignInResponse> call, Throwable t) {
                    errorDialog();
                    Log.e(TAG, t.toString());
                    hideProgress(); 

                }


            });


    }



}




