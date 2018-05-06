package ru.semkin.ivan.parttime.ui.activity;

 import android.os.Bundle;
 import android.support.design.widget.Snackbar;

 import com.heinrichreimersoftware.materialintro.app.IntroActivity;
 import com.heinrichreimersoftware.materialintro.app.OnNavigationBlockedListener;
 import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
 import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

import ru.semkin.ivan.parttime.R;
import ru.semkin.ivan.parttime.ui.fragment.IntroLoginFragment;

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
                .canGoForward(true)
                .build());


        addSlide(new FragmentSlide.Builder()
                .background(R.color.colorPrimary)
                .backgroundDark(R.color.colorPrimaryDark)
                .fragment(new IntroLoginFragment())
                .canGoForward(false)
                .canGoBackward(false)
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

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}