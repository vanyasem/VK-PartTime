package ru.semkin.ivan.parttime.ui.activity;

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
import ru.semkin.ivan.parttime.prefs.ProfileDataManager;
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

    @BindView(R.id.content)
    LinearLayout content;
    @BindView(R.id.textName)
    TextView textName;
    @BindView(R.id.textStatus)
    TextView textStatus;
    @BindView(R.id.textGender)
    TextView textGender;
    @BindView(R.id.textBirthday)
    TextView textBirthday;
    @BindView(R.id.textCountry)
    TextView textCountry;
    @BindView(R.id.textCity)
    TextView textCity;
    @BindView(R.id.imageProfilePic)
    ImageView profilePic;

    private String mFieldName;
    private int mFieldGender;
    private String mFieldStatus;
    private String mFieldBirthday;
    private String mFieldCountry;
    private String mFieldCity;
    private String mFieldProfilePic;

    public void saveData(View view) { //todo implement someday
        ProfileDataManager.setProfilePicture(mFieldProfilePic);
        ProfileDataManager.setUserName(mFieldName);
        ProfileDataManager.setUserStatus(mFieldStatus);
        ProfileDataManager.setUserGender(mFieldGender);
        ProfileDataManager.setUserBirthday(mFieldBirthday);
        ProfileDataManager.setUserCountry(mFieldCountry);
        ProfileDataManager.setUserCity(mFieldCity);
    }

    private void loadSavedData() {
        mFieldName = ProfileDataManager.getUserName();
        mFieldStatus = ProfileDataManager.getUserStatus();
        mFieldGender = ProfileDataManager.getUserGender();
        mFieldBirthday = ProfileDataManager.getUserBirthday();
        mFieldCountry = ProfileDataManager.getUserCountry();
        mFieldCity = ProfileDataManager.getUserCity();
        mFieldProfilePic = ProfileDataManager.getProfilePicture();

        textName.setText(mFieldName);
        textStatus.setText(mFieldStatus);
        textGender.setText(Util.intToGender(this, mFieldGender));
        textBirthday.setText(mFieldBirthday);
        textCountry.setText(mFieldCountry);
        textCity.setText(mFieldCity);

        GlideApp
                .with(this)
                .load(mFieldProfilePic)
                .centerInside()
                .into(profilePic);
    }
}
