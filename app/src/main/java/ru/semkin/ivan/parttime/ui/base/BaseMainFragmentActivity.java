package ru.semkin.ivan.parttime.ui.base;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.vk.sdk.VKSdk;

import ru.semkin.ivan.parttime.GlideApp;
import ru.semkin.ivan.parttime.R;
import ru.semkin.ivan.parttime.prefs.DataManager;
import ru.semkin.ivan.parttime.prefs.ProfileDataManager;
import ru.semkin.ivan.parttime.ui.activity.ProfileActivity;
import ru.semkin.ivan.parttime.ui.activity.SettingsActivity;
import ru.semkin.ivan.parttime.ui.fragment.HomeFragment;
import ru.semkin.ivan.parttime.ui.fragment.TasksFragment;

/**
 * Created by Ivan Semkin on 5/7/18
 */
public abstract class BaseMainFragmentActivity extends BaseDrawerActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mHandler = new Handler();
        setNavBar();
        openFragment();
    }

    private void setNavBar() {
        View navHeader = navigationView.getHeaderView(0);

        TextView textName = navHeader.findViewById(R.id.name);
        TextView textMood = navHeader.findViewById(R.id.mood);
        ImageView imgProfile = navHeader.findViewById(R.id.imgProfile);
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileActivity.openProfile(BaseMainFragmentActivity.this);
            }
        });

        navigationView.setNavigationItemSelectedListener(this);

        if(ProfileDataManager.getUserName() != null) {
            textName.setText(ProfileDataManager.getUserName());
        }

        if(ProfileDataManager.getUserStatus() != null) {
            textMood.setText(ProfileDataManager.getUserStatus());
        }

        if(ProfileDataManager.getProfilePicture() != null) {
            GlideApp
                    .with(this)
                    .load(ProfileDataManager.getProfilePicture())
                    .apply(RequestOptions.circleCropTransform())
                    .into(imgProfile);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // set item as selected to persist highlight
        if (item.getGroupId() == R.id.navGrMain) {
            item.setChecked(true);
        }

        switch (id) {
            case R.id.nav_home:
                navItemIndex = ID_HOME;
                CURRENT_TAG = TAG_HOME;
                break;
            case R.id.nav_tasks:
                navItemIndex = ID_TASKS;
                CURRENT_TAG = TAG_TASKS;
                break;
            case R.id.nav_settings:
                SettingsActivity.openSettings(this);
                break;
            case R.id.nav_logout:
                logOut();
                break;
        }

        openFragment();
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logOut() {
        DataManager.clear();
        VKSdk.logout();
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getBaseContext().getPackageName());
        assert i != null;
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    private static final String TAG_HOME = "home";
    private static final String TAG_TASKS = "tasks";
    private static String CURRENT_TAG = TAG_HOME;
    private static final int ID_HOME = 0;
    private static final int ID_TASKS = 1;
    private static int navItemIndex = ID_HOME;

    public static Handler mHandler;
    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case ID_TASKS:
                return new TasksFragment();
            case ID_HOME:
            default:
                return new HomeFragment();
        }
    }

    public void openFragment() {
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // updateRead the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction
                        = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack(CURRENT_TAG);
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                if (!isFinishing())
                    fragmentTransaction.commitAllowingStateLoss();
            }
        };
        mHandler.post(mPendingRunnable);
    }
}
