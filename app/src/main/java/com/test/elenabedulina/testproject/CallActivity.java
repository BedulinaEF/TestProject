package com.test.elenabedulina.testproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.test.elenabedulina.testproject.api.models.CallStartResponse;
import com.test.elenabedulina.testproject.api.models.CallsIdEndRequest;
import com.test.elenabedulina.testproject.api.models.RaitingRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CallActivity extends BaseActivity {
    private final String TAG = CallActivity.class.getSimpleName();
    public String callCenterNumber;
    public String callID;
    BroadcastReceiver phoneStateBroadcastReceiver;

    protected void registerReceiver() {
        phoneStateBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String phoneState = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
                if (TelephonyManager.EXTRA_STATE_IDLE.equals(phoneState)) {
                    getDataCallDuration();
                    doCallInfoRequest();
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PHONE_STATE");
        registerReceiver(phoneStateBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(phoneStateBroadcastReceiver);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerReceiver();
        callCenterNumber = getIntent().getStringExtra(Constants.CALL_CENTER_NUMBER_NAME);
        setContentView(R.layout.activity_call);
        Button btn_call_center = findViewById(R.id.btn_call_center);
        btn_call_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissionCall();
            }
        });
    }

    private void requestPermissionCall() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CALL_PHONE,
                        Manifest.permission.READ_CALL_LOG,
                        Manifest.permission.READ_PHONE_STATE)
                .withListener(new MultiplePermissionsListener() {
                                  @Override
                                  public void onPermissionsChecked(MultiplePermissionsReport report) {
                                      if (report.areAllPermissionsGranted()) {
                                          makeCallStartRequest();
                                      } else {
                                          showPermissionDialog();
                                      }
                                  }

                                  @Override
                                  public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                      token.continuePermissionRequest();
                                  }
                              }
                ).check();
    }


    private void showPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.permission_title)
                .setPositiveButton(R.string.permission_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        requestPermissionCall();
                    }
                })
                .setNegativeButton(R.string.permission_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .show();
    }

    private void makeCallStartRequest() {
        showProgress();
        processResponseCallStart();
    }

    private void processResponseCallStart() {
        ((ApplicationZLife) getApplication()).getZlifeService().getId().enqueue(new retrofit2.Callback<CallStartResponse>() {
            @Override
            public void onResponse(Call<CallStartResponse> call, Response<CallStartResponse> response) {
                if (response.code() >= 200 && response.code() < 300) {
                    CallStartResponse bodyResponse = response.body();
                    callID = bodyResponse.getContent().getId();
                    makeCall();
                } else {
                    onFailure(call, new Exception(response.message()));
                }
                hideProgress();
            }

            @Override
            public void onFailure(Call<CallStartResponse> call, Throwable t) {
                errorDialog();
                hideProgress();
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void makeCall() {
        Intent makeCallIntent = new Intent(Intent.ACTION_CALL);
        makeCallIntent.setData(Uri.parse("tel:" + callCenterNumber));
        this.startActivity(makeCallIntent);
    }

    private void doCallInfoRequest() {
        showProgress();
        showDialogCallCenter();
        processResponseCalling();
    }

    private void processResponseCalling() {
        CallsIdEndRequest callsIdEndRequest = new CallsIdEndRequest();
        String osVersion = Build.VERSION.RELEASE;
        callsIdEndRequest.setCallDuration(getDataCallDuration());
        callsIdEndRequest.setOs(Constants.os);
        callsIdEndRequest.setOsVersion(osVersion);
        ((ApplicationZLife) getApplication()).getZlifeService().putID(callID, callsIdEndRequest).enqueue(new Callback<CallsIdEndRequest>() {
            @Override
            public void onResponse(Call<CallsIdEndRequest> call, Response<CallsIdEndRequest> response) {
                if (response.code() <= 200 && response.code() > 300) {
                    onFailure(call, new Exception(response.message()));
                }
                hideProgress();
            }

            @Override
            public void onFailure(Call<CallsIdEndRequest> call, Throwable t) {
                errorDialog();
                hideProgress();
            }
        });
    }

    @SuppressLint("MissingPermission")
    private String getDataCallDuration() {
        Cursor managedCursor = null;
        String callDuration = "";
        Uri contacts = CallLog.Calls.CONTENT_URI;
        try {
            managedCursor = this.getContentResolver().query(contacts, null, null, null, null);
            int calDuration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
            managedCursor.moveToLast();
            callDuration = managedCursor.getString(calDuration);
        } catch (Throwable throwable) {
            Log.e(TAG, throwable.toString());

        } finally {
            if (managedCursor != null) {
                managedCursor.close();
            }
        }
        return callDuration;
    }

    private void doDialogRequest(String answer) {
        RaitingRequest raitingRequest = new RaitingRequest();
        raitingRequest.setIsHelpful(answer);
        ((ApplicationZLife) getApplication()).getZlifeService().putAnswer(callID, raitingRequest).enqueue(new Callback<RaitingRequest>() {
            @Override
            public void onResponse(Call<RaitingRequest> call, Response<RaitingRequest> response) {
                if (response.code() <= 200 && response.code() > 300) {
                    onFailure(call, new Exception(response.message()));
                }
            }

            @Override
            public void onFailure(Call<RaitingRequest> call, Throwable t) {
                errorDialog();
                hideProgress();
            }
        });
    }

    private void showDialogCallCenter() {
        Dialog dialogCallCenter = new AlertDialog.Builder(this)
                .setTitle(R.string.call_center_message)
                .setNeutralButton(R.string.fail_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String answer = getString(R.string.fail_btn).toLowerCase();
                        doDialogRequest(answer);
                    }
                })
                .setNegativeButton(R.string.no_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String answer = getString(R.string.no_btn).toLowerCase();
                        doDialogRequest(answer);
                    }
                })
                .setPositiveButton(R.string.yes_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String answer = getString(R.string.yes_btn).toLowerCase();
                        doDialogRequest(answer);
                    }
                })
                .create();
        dialogCallCenter.show();
    }
}
