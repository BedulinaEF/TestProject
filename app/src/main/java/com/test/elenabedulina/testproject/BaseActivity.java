package com.test.elenabedulina.testproject;

import android.app.Activity;
import android.app.ProgressDialog;

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

}





