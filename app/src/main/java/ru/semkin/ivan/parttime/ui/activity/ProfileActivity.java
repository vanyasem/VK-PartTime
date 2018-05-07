package ru.semkin.ivan.parttime.ui.activity;

import android.os.Bundle;
import android.view.View;

import ru.semkin.ivan.parttime.R;
import ru.semkin.ivan.parttime.ui.base.BaseActivity;
import ru.semkin.ivan.parttime.util.ActivityUtil;

public class ProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ActivityUtil.setActionBar(this);
    }

    public void saveData(View view) {
        //todo implement
    }

    public void close(View view) {
        finish();
    }

    public void onPickImage(View view) {
        //todo implement
    }
}
