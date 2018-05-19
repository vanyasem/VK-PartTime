package ru.semkin.ivan.parttime.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.app.NavigationPolicy;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

import ru.semkin.ivan.parttime.R;
import ru.semkin.ivan.parttime.api.request.SimpleCallback;
import ru.semkin.ivan.parttime.api.request.Users;
import ru.semkin.ivan.parttime.ui.fragment.ConfigurationChatFragment;
import ru.semkin.ivan.parttime.ui.fragment.ConfigurationGroupFragment;

/**
 * Created by Ivan Semkin on 5/6/18
 */
public class MainIntroActivity extends IntroActivity {

    private boolean mBlocked = true;
    private final static int SLIDE_WELCOME = 0;
    private final static int SLIDE_LOGIN = 1;
    @SuppressWarnings("unused")
    private final static int SLIDE_CONFIGURE_CHAT = 2;
    @SuppressWarnings("unused")
    private final static int SLIDE_CONFIGURE_GROUP = 3;

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
                .build());

        /* Enable/disable skip button */
        setButtonBackVisible(false);
        setButtonNextVisible(false);

        setNavigationPolicy(new NavigationPolicy() {
            @Override public boolean canGoForward(int position) {
                return !mBlocked || position == SLIDE_WELCOME;
            }

            @Override public boolean canGoBackward(int position) {
                return position <= SLIDE_LOGIN;
            }
        });
    }

    @SuppressWarnings("WeakerAccess")
    public static final int REQUEST_CODE = 1337;
    public static void openIntro(Activity activity) {
        Intent intent = new Intent(activity, MainIntroActivity.class);
        activity.startActivityForResult(intent, REQUEST_CODE);
    }

    public void next() {
        mBlocked = false;
        nextSlide();
        mBlocked = true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                // User passed Authorization
                Snackbar.make(MainIntroActivity.this.getContentView(),
                        R.string.intro_success, Snackbar.LENGTH_LONG).show();
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

    private void loadBasicData() {
        Users.getUserProfile(new SimpleCallback() {
            @Override
            public void onFinished() {
                addSlide(new FragmentSlide.Builder()
                        .background(R.color.colorPrimary)
                        .backgroundDark(R.color.colorPrimaryDark)
                        .fragment(new ConfigurationChatFragment())
                        .build());

                addSlide(new FragmentSlide.Builder()
                        .background(R.color.colorPrimary)
                        .backgroundDark(R.color.colorPrimaryDark)
                        .fragment(new ConfigurationGroupFragment())
                        .build());

                // Give it some time to notify fragment changes
                Handler handler = new Handler();
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        next();
                    }
                };
                handler.post(runnable);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
