package ru.semkin.ivan.parttime.prefs;

/**
 * Created by Ivan Semkin on 5/7/18
 */
public class LoginDataManager extends DataManager {

    static public long getChatId() {
        return sPreferences.getLong("chatId", -1);
    }

    static public void setChatId(long chatId) {
        sPreferencesEditor.putLong("chatId", chatId);
        sPreferencesEditor.commit();
    }


    static public boolean isGroupChat() {
        return sPreferences.getBoolean("isGroupChat", false);
    }

    static public void setGroupChat(boolean value) {
        sPreferencesEditor.putBoolean("isGroupChat", value);
        sPreferencesEditor.commit();
    }


    static public long getGroupId() {
        return sPreferences.getLong("groupId", -1);
    }

    static public void setGroupId(long groupId) {
        sPreferencesEditor.putLong("groupId", groupId);
        sPreferencesEditor.commit();
    }


    static public boolean getLoggedIn() {
        return sPreferences.getBoolean("loggedIn", false);
    }

    static public void setLoggedIn(boolean isProfileFilled) {
        sPreferencesEditor.putBoolean("loggedIn", isProfileFilled);
        sPreferencesEditor.commit();
    }
}
