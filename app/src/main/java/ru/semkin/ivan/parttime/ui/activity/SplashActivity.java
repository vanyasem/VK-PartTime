package ru.semkin.ivan.parttime.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Ivan Semkin on 5/6/18
 */
public class SplashActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1337;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openIntro();
    }

    private void openIntro() {
        Intent intent = new Intent(SplashActivity.this, MainIntroActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    private void openMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        openMain();
    }
}
