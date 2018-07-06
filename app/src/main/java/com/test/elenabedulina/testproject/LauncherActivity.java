package com.test.elenabedulina.testproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class LauncherActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        EditText etInn=findViewById(R.id.et_inn_id);


        EditText etPassword=findViewById(R.id.et_password_id);


        Button btnLogin = findViewById(R.id.button_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent launcherIntent=new Intent(LauncherActivity.this,
                        CallActivity.class);
                startActivity(launcherIntent);



            }
        });



        }
}
