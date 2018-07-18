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

import com.test.elenabedulina.testproject.api.models.CallStartResponse;

import retrofit2.Call;
import retrofit2.Response;


public class CallActivity extends BaseActivity {
    public String callCenterNumber;

    private final String TAG = CallActivity.class.getSimpleName();



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
        makeCall();


    }

    private void processResponseCallStart() {
            ((ApplicationZLife)getApplication()).getZlifeService().getId().enqueue(new retrofit2.Callback<CallStartResponse>() {
                @Override
                public void onResponse(Call<CallStartResponse> call, Response<CallStartResponse> response) {
                    if (response.code() >= 200 && response.code() < 300) {
                        CallStartResponse bodyResponse = response.body();
                        String takenID = bodyResponse.getId();

                    } else {

                        onFailure(call, new Exception(response.message()));
                    }
                    hideProgress();
                }

                @Override
                public void onFailure(Call<CallStartResponse> call, Throwable t) {
                    errorDialog();
                    hideProgress();
                    Log.e(TAG, t.toString());

                }

            });
        }




    private void makeCall() {
        //calling code
        Intent makeCallIntent= new Intent(Intent.ACTION_DIAL);
        makeCallIntent.setData(Uri.parse("tel:"+callCenterNumber));
        this.startActivity(makeCallIntent);
        doCallInfoRequest(5, callCenterNumber);

    }





    private void doCallInfoRequest(int callDuration, String callCenterNumber) {
        showProgress();
        processResponseCalling();
        hideProgress();
        showDialogCallCenter();
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
