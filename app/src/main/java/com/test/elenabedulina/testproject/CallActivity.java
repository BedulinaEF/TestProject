package com.test.elenabedulina.testproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class CallActivity extends BaseActivity {
    public String callCenterNumber;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callCenterNumber=getIntent().getStringExtra(Constants.CALL_CENTER_NUMBER_NAME);


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
        Intent makeCallIntent= new Intent(Intent.ACTION_DIAL);
        makeCallIntent.setData(Uri.parse("tel:"+callCenterNumber));
        this.startActivity(makeCallIntent);
        doCallInfoRequest(5, callCenterNumber);
        showDialogCallCenter();
    }





    private void doCallInfoRequest(int callDuration, String callCenterNumber) {
        showProgress();
        processResponseCalling();
        hideProgress();
    }

    private void processResponseCalling() {


    }
    private void doDialogRequest(String  answer){
        Log.d(CallActivity.class.getSimpleName(), answer);

    }

    private void showDialogCallCenter() {
        Dialog dialogCallCenter = new AlertDialog.Builder(this)
                .setTitle(R.string.call_center_message)
                .setNeutralButton(R.string.fail_btn, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String answer=getString(R.string.fail_btn).toLowerCase();
                        doDialogRequest(answer);

                    }
                })
                .setNegativeButton(R.string.no_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String answer=getString(R.string.no_btn).toLowerCase();
                        doDialogRequest(answer);
                    }
                })
                .setPositiveButton(R.string.yes_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String answer=getString(R.string.yes_btn).toLowerCase();
                        doDialogRequest(answer);
                    }
                })

                .create();
        dialogCallCenter.show();

    }


}
