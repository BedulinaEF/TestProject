package com.test.elenabedulina.testproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;

public abstract class BaseActivity extends Activity{


    private ProgressDialog dialog;
    protected void showProgress(){
        if (dialog == null) {
            dialog = new ProgressDialog(BaseActivity.this);
            dialog.setTitle(R.string.sign_in);
        }
        dialog.show();

    }
    protected void hideProgress(){
        dialog.hide();
    }
    public void errorDialog(){
        final Dialog errorDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.error_dialog)
                .setNeutralButton(R.string.ok, new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialogInterface, int i){
                        dialogInterface.cancel();
                    }
                })
                .create();
        errorDialog.show();
    }


}





