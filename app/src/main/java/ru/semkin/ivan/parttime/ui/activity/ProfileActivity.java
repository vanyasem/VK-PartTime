package ru.semkin.ivan.parttime.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.semkin.ivan.parttime.GlideApp;
import ru.semkin.ivan.parttime.R;
import ru.semkin.ivan.parttime.datamanager.ProfileDataManager;
import ru.semkin.ivan.parttime.ui.base.BaseActivity;
import ru.semkin.ivan.parttime.util.ActivityUtil;
import ru.semkin.ivan.parttime.util.Util;

public class ProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        ActivityUtil.setActionBar(this);

        loadSavedData();
    }

    public static void openProfile(View view, Context context) {
        Intent intent = new Intent(context, ProfileActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.content)
    LinearLayout content;
    @BindView(R.id.textName)
    TextView mTextName;
    @BindView(R.id.textStatus)
    TextView mTextStatus;
    @BindView(R.id.textGender)
    TextView mTextGender;
    @BindView(R.id.textBirthday)
    TextView mTextBirthday;
    @BindView(R.id.textCountry)
    TextView mTextCountry;
    @BindView(R.id.textCity)
    TextView mTextCity;
    @BindView(R.id.boxName)
    RelativeLayout mBoxName;
    @BindView(R.id.boxStatus)
    RelativeLayout mBoxMood;
    @BindView(R.id.boxGender)
    RelativeLayout mBoxGender;
    @BindView(R.id.boxBirthday)
    RelativeLayout mBoxBirthday;
    @BindView(R.id.boxCountry)
    RelativeLayout mBoxCountry;
    @BindView(R.id.boxCity)
    RelativeLayout mBoxCity;
    @BindView(R.id.imageProfilePic)
    ImageView mProfilePic;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    private String mFieldName;
    private int mFieldGender;
    private String mFieldStatus;
    private String mFieldBirthday;
    private String mFieldCountry;
    private String mFieldCity;
    private String mFieldProfilePic;

    public void saveData(View view) {
        mProgressBar.setVisibility(View.VISIBLE);
        ProfileDataManager.setProfilePicture(mFieldProfilePic);
        ProfileDataManager.setUserName(mFieldName);
        ProfileDataManager.setUserStatus(mFieldStatus);
        ProfileDataManager.setUserGender(mFieldGender);
        ProfileDataManager.setUserBirthday(mFieldBirthday);
        ProfileDataManager.setUserCountry(mFieldCountry);
        ProfileDataManager.setUserCity(mFieldCity);
    }

    public void close(View view) {
        finish();
    }

    public void onPickImage(View view) {
        //todo implement
    }

    private void loadSavedData() {
        mFieldName = ProfileDataManager.getUserName();
        mFieldStatus = ProfileDataManager.getUserStatus();
        mFieldGender = ProfileDataManager.getUserGender();
        mFieldBirthday = ProfileDataManager.getUserBirthday();
        mFieldCountry = ProfileDataManager.getUserCountry();
        mFieldCity = ProfileDataManager.getUserCity();
        mFieldProfilePic = ProfileDataManager.getProfilePicture();

        mTextName.setText(mFieldName);
        mTextStatus.setText(mFieldStatus);
        mTextGender.setText(Util.intToGender(this, mFieldGender));
        mTextBirthday.setText(mFieldBirthday);
        mTextCountry.setText(mFieldCountry);
        mTextCity.setText(mFieldCity);

        GlideApp
                .with(this)
                .load(mFieldProfilePic)
                .centerInside()
                .into(mProfilePic);
    }
}
