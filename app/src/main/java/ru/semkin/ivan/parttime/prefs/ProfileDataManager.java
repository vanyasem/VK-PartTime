package ru.semkin.ivan.parttime.prefs;

/**
 * Created by Ivan Semkin on 5/7/18
 */
public class ProfileDataManager extends DataManager {

    static public String getUserName() {
        return sPreferences.getString("userName", null);
    }

    static public void setUserName(String name) {
        sPreferencesEditor.putString("userName", name);
        sPreferencesEditor.commit();
    }


    static public int getUserGender() {
        return sPreferences.getInt("userGender", -1);
    }

    static public void setUserGender(int gender) {
        sPreferencesEditor.putInt("userGender", gender);
        sPreferencesEditor.commit();
    }


    static public String getUserBirthday() {
        return sPreferences.getString("userBirthday", null);
    }

    static public void setUserBirthday(String ts) {
        sPreferencesEditor.putString("userBirthday", ts);
        sPreferencesEditor.commit();
    }


    static public String getUserCity() {
        return sPreferences.getString("userCity", null);
    }

    static public void setUserCity(String city) {
        sPreferencesEditor.putString("userCity", city);
        sPreferencesEditor.commit();
    }


    static public String getUserCountry() {
        return sPreferences.getString("userCountry", null);
    }

    static public void setUserCountry(String country) {
        sPreferencesEditor.putString("userCountry", country);
        sPreferencesEditor.commit();
    }


    static public String getProfilePicture() {
        return sPreferences.getString("userProfilePicture", null);
    }

    static public void setProfilePicture(String profilePicture) {
        sPreferencesEditor.putString("userProfilePicture", profilePicture);
        sPreferencesEditor.commit();
    }


    static public String getUserStatus() {
        return sPreferences.getString("userStatus", null);
    }

    static public void setUserStatus(String status) {
        sPreferencesEditor.putString("userStatus", status);
        sPreferencesEditor.commit();
    }
}
