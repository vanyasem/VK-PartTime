package ru.semkin.ivan.parttime.ui.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.app.OnNavigationBlockedListener;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import ru.semkin.ivan.parttime.R;
import ru.semkin.ivan.parttime.repository.GetUsers;
import ru.semkin.ivan.parttime.datamanager.LoginDataManager;

/**
 * Created by Ivan Semkin on 5/6/18
 */
public class MainIntroActivity extends IntroActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
         /*
           Standard slide (like Google's intros)
          */
        addSlide(new SimpleSlide.Builder()
                .title(R.string.intro_welcome)
                .image(R.mipmap.ic_launcher)
                .background(R.color.colorPrimary)
                .backgroundDark(R.color.colorPrimaryDark)
                .build());


        addSlide(new SimpleSlide.Builder()
                .title(R.string.intro_auth)
                .description(R.string.intro_just_click)
                .image(R.drawable.ic_account_circle)
                .background(R.color.colorPrimary)
                .backgroundDark(R.color.colorPrimaryDark)
                .buttonCtaClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        VKSdk.login(MainIntroActivity.this,
                                "wall", "friends", "messages", "groups");
                    }
                })
                .buttonCtaLabel(R.string.intro_sign_in)
                .canGoForward(false)
                .build());

        /* Enable/disable skip button */
        setButtonBackVisible(false);

        /* Add a listener to detect when users try to go to a page they can't go to */
        addOnNavigationBlockedListener(new OnNavigationBlockedListener() {
            @Override
            public void onNavigationBlocked(int position, int direction) {
                Snackbar.make(findViewById(android.R.id.content),
                        R.string.intro_must_login, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    public static final int REQUEST_CODE = 1337;
    public static void openIntro(Activity activity) {
        Intent intent = new Intent(activity, MainIntroActivity.class);
        activity.startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                // User passed Authorization
                Snackbar.make(MainIntroActivity.this.getContentView(),
                        R.string.intro_success, Snackbar.LENGTH_INDEFINITE).show();
                loadBasicData();
            }
            @Override
            public void onError(VKError error) {
                // User didn't pass Authorization
                Snackbar.make(MainIntroActivity.this.getContentView(),
                        R.string.intro_error, Snackbar.LENGTH_LONG).show();
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private final BroadcastReceiver syncFinishedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, Intent intent) {
            LoginDataManager.setLoggedIn(true);
            MainActivity.openMain(MainIntroActivity.this);
            finishAffinity();
        }
    };
    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(syncFinishedReceiver,
                new IntentFilter(GetUsers.USER_GET_SYNC_FINISHED));
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(syncFinishedReceiver);
    }

    private void loadBasicData() {
        GetUsers getUsers = new GetUsers(this);
        getUsers.getUserProfile();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}