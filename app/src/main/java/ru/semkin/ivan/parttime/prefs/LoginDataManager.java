package ru.semkin.ivan.parttime.prefs;

/**
 * Created by Ivan Semkin on 5/7/18
 */
public class LoginDataManager extends DataManager {

    static public long getUserId() {
        return preferences.getLong("userId", -1);
    }

    static public void setUserId(long userId) {
        preferencesEditor.putLong("userId", userId);
        preferencesEditor.commit();
    }


    static public long getChatId() {
        return preferences.getLong("chatId", -1);
    }

    static public void setChatId(long chatId) {
        preferencesEditor.putLong("chatId", chatId);
        preferencesEditor.commit();
    }


    static public long getGroupId() {
        return preferences.getLong("groupId", -1);
    }

    static public void setGroupId(long groupId) {
        preferencesEditor.putLong("groupId", groupId);
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
