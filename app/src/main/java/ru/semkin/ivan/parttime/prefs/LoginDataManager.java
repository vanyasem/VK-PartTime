package ru.semkin.ivan.parttime.prefs;

/**
 * Created by Ivan Semkin on 5/7/18
 */
public class LoginDataManager extends DataManager {

    static public String getUserId() {
        return preferences.getString("userId", null);
    }

    static public void setUserId(String Id) {
        preferencesEditor.putString("userId", Id);
        preferencesEditor.commit();
    }


    static public boolean getLoggedIn() {
        return preferences.getBoolean("loggedIn", false);
    }

    static public void setLoggedIn(boolean isProfileFilled) {
        preferencesEditor.putBoolean("loggedIn", isProfileFilled);
        preferencesEditor.commit();
    }
}
