package com.test.elenabedulina.testproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class LauncherActivity extends Activity {
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
                Intent launcherIntent = new Intent(LauncherActivity.this,
                        CallActivity.class);
                if (checkInput()) {
                    startActivity(launcherIntent);
                }
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
}
