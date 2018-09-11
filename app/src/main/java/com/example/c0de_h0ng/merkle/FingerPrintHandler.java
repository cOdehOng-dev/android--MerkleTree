package com.example.c0de_h0ng.merkle;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by c0de_h0ng on 2018. 8. 13..
 */

@TargetApi(Build.VERSION_CODES.M)
public class FingerPrintHandler extends FingerprintManager.AuthenticationCallback {

    private AppCompatActivity appCompatActivity;
    private Context appContext;
    private ImageView imageView;

    public FingerPrintHandler(Context context, AppCompatActivity activity, ImageView imgView) {
        this.appContext = context;
        this.appCompatActivity = activity;
        this.imageView = imgView;
    }

    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        Toast.makeText(appContext, "인증 오류\n" + errString, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        Toast.makeText(appContext, "도움말", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationFailed() {
        Toast.makeText(appContext, "등록되지 않은 지문입니다", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        Toast.makeText(appContext, "지문 인증 성공", Toast.LENGTH_LONG).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    imageView.setImageResource(R.drawable.ic_fingerprint_after_24dp);
                    Thread.sleep(300);
                    appCompatActivity.startActivity(new Intent(appCompatActivity, Name.class));
                    appCompatActivity.finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 300);
    }
}
