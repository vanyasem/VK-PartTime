package ru.semkin.ivan.parttime.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ru.semkin.ivan.parttime.datamanager.LoginDataManager;

/**
 * Created by Ivan Semkin on 5/6/18
 */
public class SplashActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(LoginDataManager.getLoggedIn()) {
            MainActivity.openMain(this);
            finishAffinity();
        }
        else {
            MainIntroActivity.openIntro(this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        MainIntroActivity.openIntro(this);
    }
}
