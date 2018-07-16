package com.test.elenabedulina.testproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class LauncherActivity extends BaseActivity {
    private EditText etInn;
    private EditText etPassword;


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
        getDataSingIn();
        hideProgress();
        startCallActivity();

    }

    private void startCallActivity() {
        Intent launcherIntent = new Intent(LauncherActivity.this,
                CallActivity.class);
        launcherIntent.putExtra(Constants.CALL_CENTER_NUMBER_NAME,Constants.CALL_CENTER_NUMBER) ;

        if (checkInput()) {
            startActivity(launcherIntent);
        }
    }

    private void getDataSingIn() {
    }








}




