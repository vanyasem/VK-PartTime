package ru.semkin.ivan.parttime.ui.base;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.vk.sdk.VKSdk;

import androidx.navigation.Navigation;
import ru.semkin.ivan.parttime.GlideApp;
import ru.semkin.ivan.parttime.R;
import ru.semkin.ivan.parttime.prefs.DataManager;
import ru.semkin.ivan.parttime.prefs.ProfileDataManager;
import ru.semkin.ivan.parttime.util.ActivityUtil;

/**
 * Created by Ivan Semkin on 5/7/18
 */
@SuppressWarnings("WeakerAccess")
public abstract class BaseDrawerActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected DrawerLayout drawerLayout;
    protected ActionBarDrawerToggle drawerToggle;
    protected NavigationView navigationView;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setDrawerLayout();
        setDrawerHeader();
    }

    private void setDrawerLayout() {
        drawerLayout = findViewById(R.id.drawer_layout);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                ActivityUtil.hideKeyboard(BaseDrawerActivity.this);
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
            }
        };
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(drawerToggle);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setDrawerHeader() {
        View navHeader = navigationView.getHeaderView(0);

        TextView textName = navHeader.findViewById(R.id.name);
        TextView textMood = navHeader.findViewById(R.id.mood);
        ImageView imgProfile = navHeader.findViewById(R.id.imgProfile);
        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(
                        BaseDrawerActivity.this, R.id.nav_host_fragment).navigate(R.id.profileActivity);
            }
        });

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

    // Workaround for Activity Navigation bug. To be removed
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
                Navigation.findNavController(
                        this, R.id.nav_host_fragment).navigate(R.id.nav_home);
                break;
            case R.id.nav_tasks:
                Navigation.findNavController(
                        this, R.id.nav_host_fragment).navigate(R.id.nav_tasks);
                break;
            case R.id.nav_chat:
                Navigation.findNavController(
                        this, R.id.nav_host_fragment).navigate(R.id.nav_chat);
                break;
            case R.id.nav_group:
                Navigation.findNavController(
                        this, R.id.nav_host_fragment).navigate(R.id.nav_group);
                break;
            case R.id.nav_settings:
                Navigation.findNavController(
                        this, R.id.nav_host_fragment).navigate(R.id.nav_settings);
                break;
            case R.id.nav_logout:
                logOut();
                break;
        }

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
}
