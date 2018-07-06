package com.test.elenabedulina.testproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

public class CallActivity  extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);





        Button btn_call_center = findViewById(R.id.btn_call_center);
        btn_call_center.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ProgressDialog dialog=new ProgressDialog(CallActivity.this);
                dialog.setTitle(R.string.text_call_center);
                dialog.show();



            }

        });
    }


}
