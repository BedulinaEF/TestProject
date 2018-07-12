package com.test.elenabedulina.testproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class CallActivity extends Activity {
    private ProgressDialog dialog = new ProgressDialog(CallActivity.this);

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

    private void makeCallStartRequest(){
        showProgress();
        // code for request
        processResponseCallStart();
        hideProgress();
        makeCall();
        dialogCallCenter();

    }
    private void processResponseCallStart(){

    }

    private void makeCall() {
        //calling code
        doCallInfoRequest(5, "123");
    }

    private void showProgress() {
        dialog.setTitle(R.string.text_call_center);
        dialog.show();
    }

    private void hideProgress() {
        dialog.hide();
    }


    private void doCallInfoRequest( int callDuration, String phoneNumber) {
        showProgress();
        processResponseCalling();
        hideProgress();
    }

    private void processResponseCalling() {

    }
    private Dialog dialogCallCenter(){
        final int dialog_exit=1;
        showDialog(dialog_exit);
        protected Dialog onCreateDialog(int id){
            if (id==dialog_exit) {
                AlertDialog.Builder adb=new AlertDialog.Builder(this);
                adb.setTitle(R.string.dialog_title);
                adb.setMessage(R.string.dialog_message);
                adb.setPositiveButton(R.string.yes_btn, dialogClickListener);
                adb.setNegativeButton(R.string.no_btn, dialogClickListener);
                adb.setNeutralButton(R.string.fail_btn, dialogClickListener);
                adb.setCancelable(false);
                return adb.create();
            }
            return super.onCreateDialog(id);
        }
        View.OnClickListener dialogClickListener=new View.OnClickListener(){
            @Override

            public void onClick(DialogInterface dialog, int btn_choise){
                switch (btn_choise){
                    case Dialog.BUTTON_POSITIVE;
                    saveData();
                    finish();
                    break;
                    case Dialog.BUTTON_NEGATIVE;
                    finish();
                    break;
                    case Dialog.BUTTON_NEUTRAL;
                    break;
                }
            }

        };

        public void saveData(){
            Toast.makeText(this, R.string.dialog_saved_data, Toast.LENGTH_SHORT).show();
        }

    }



}
