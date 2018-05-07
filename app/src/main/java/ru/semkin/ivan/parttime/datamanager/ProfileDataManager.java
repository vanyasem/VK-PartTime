package ru.semkin.ivan.parttime.datamanager;

/**
 * Created by Ivan Semkin on 5/7/18
 */
public class ProfileDataManager extends DataManager {

    static public String getUserName() {
        return preferences.getString("userName", null);
    }

    static public void setUserName(String name) {
        preferencesEditor.putString("userName", name);
        preferencesEditor.commit();
    }


    static public int getUserGender() {
        return preferences.getInt("userGender", -1);
    }

    static public void setUserGender(int gender) {
        preferencesEditor.putInt("userGender", gender);
        preferencesEditor.commit();
    }


    static public String getUserBirthday() {
        return preferences.getString("userBirthday", null);
    }

    static public void setUserBirthday(String ts) {
        preferencesEditor.putString("userBirthday", ts);
        preferencesEditor.commit();
    }


    static public String getUserCity() {
        return preferences.getString("userCity", null);
    }

    static public void setUserCity(String city) {
        preferencesEditor.putString("userCity", city);
        preferencesEditor.commit();
    }


    static public String getUserCountry() {
        return preferences.getString("userCountry", null);
    }

    static public void setUserCountry(String country) {
        preferencesEditor.putString("userCountry", country);
        preferencesEditor.commit();
    }


    static public String getProfilePicture() {
        return preferences.getString("userProfilePicture", null);
    }

    static public void setProfilePicture(String profilePicture) {
        preferencesEditor.putString("userProfilePicture", profilePicture);
        preferencesEditor.commit();
    }


    static public String getUserStatus() {
        return preferences.getString("userStatus", null);
    }

    static public void setUserStatus(String status) {
        preferencesEditor.putString("userStatus", status);
        preferencesEditor.commit();
    }
}
