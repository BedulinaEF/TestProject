package com.test.elenabedulina.testproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;


public class CallActivity extends Activity {
    private ProgressDialog dialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_call);
        Button btn_call_center = findViewById(R.id.btn_call_center);
        btn_call_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makeCallStartRequest();

            }
        });
    }

    private void makeCallStartRequest() {
        showProgress();
        // code for request
        processResponseCallStart();
        hideProgress();
        makeCall();


    }

    private void processResponseCallStart() {

    }

    private void makeCall() {
        //calling code
        doCallInfoRequest(5, "123");
        showDialogCallCenter();
    }

    private void showProgress() {
        if (dialog == null) {
            dialog = new ProgressDialog(CallActivity.this);
            dialog.setTitle(R.string.text_call_center);
        }
        dialog.show();
    }

    private void hideProgress() {
        dialog.hide();
    }


    private void doCallInfoRequest(int callDuration, String phoneNumber) {
        showProgress();
        processResponseCalling();
        hideProgress();
    }

    private void processResponseCalling() {


    }

    private void showDialogCallCenter() {
        Dialog dialogCallCenter = new AlertDialog.Builder(this)
                .setTitle(R.string.dialog_call_center_title)
                .create();
        dialogCallCenter.show();
        
    }


}
