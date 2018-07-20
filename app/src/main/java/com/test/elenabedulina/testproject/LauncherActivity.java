package com.test.elenabedulina.testproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.test.elenabedulina.testproject.api.models.SignInRequest;
import com.test.elenabedulina.testproject.api.models.SignInResponse;

import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LauncherActivity extends BaseActivity {
    private final String TAG = LauncherActivity.class.getSimpleName();
    private EditText etInn;
    private EditText etPassword;
    private String headerClient;
    private String headerAccessToken;
    private String headerUID;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        etInn = findViewById(R.id.et_inn);
        etPassword = findViewById(R.id.et_password);

        Button btnLogin = findViewById(R.id.button_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInput()){
                makePostSignIn();}
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
        SignInRequest signInRequest = new SignInRequest();
        signInRequest.setIin(etInn.getText().toString());
        signInRequest.setPassword(etPassword.getText().toString());
        ((ApplicationZLife) getApplication()).getZlifeService().signIn(signInRequest).enqueue(new Callback<SignInResponse>() {
            @Override
            public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {
                if (response.code() >= 200 && response.code() < 300) {
                    SignInResponse bodyResponse = response.body();
                    String numberCall = bodyResponse.getContent().getCallCenter();
                    Headers headers = response.headers();
                    headerClient = headers.get("Client");
                    headerAccessToken = headers.get("Access-token");
                    headerUID = headers.get("UID");
                    savePreferences();
                    startCallActivity(numberCall);
                } else {
                    onFailure(call, new Exception(response.message()));
                }
                hideProgress();
            }

            @Override
            public void onFailure(Call<SignInResponse> call, Throwable t) {
                errorDialog();
                hideProgress();
            }
        });
    }

    public void savePreferences() {
        SharedPreferences sharedPref = this.getSharedPreferences(Constants.KEY_SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Constants.KEY_CLIENT_HEADER, headerClient);
        editor.putString(Constants.KEY_CLIENT_UID, headerUID);
        editor.putString(Constants.KEY_CLIENT_TOKEN, headerAccessToken);
        editor.apply();
    }
}




