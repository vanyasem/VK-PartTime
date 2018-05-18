package ru.semkin.ivan.parttime.api.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;

import ru.semkin.ivan.parttime.R;

/**
 * Created by Ivan Semkin on 5/8/18
 */
public class Sync {

    private static Sync sInstance = null;
    protected Sync() {

    }

    public static Sync getInstance() {
        if(sInstance == null) {
            sInstance = new Sync();
        }
        return sInstance;
    }

    // Constants
    // The account name
    public static final String ACCOUNT = "PartTime";
    // Sync interval constants
    public static Account account;

    public static final long SECONDS_PER_MINUTE = 60L;
    public static long syncIntervalInMinutes = 360L;
    public static long syncInterval;

    public void initSync(Context context) {
        // Create the dummy account
        account = createSyncAccount(context);
        String authority = context.getString(R.string.content_authority);
        // Inform the system that this account supports sync
        ContentResolver.setIsSyncable(account, authority, 1);
        // Inform the system that this account is eligible for auto sync when the network is up
        ContentResolver.setSyncAutomatically(account, authority, true);
        ContentResolver.setMasterSyncAutomatically(true);
        ContentResolver.addPeriodicSync(account, authority, Bundle.EMPTY, syncInterval);
    }

    /**
     * Create a new dummy account for the sync adapter
     *
     * @param context The application context
     */
    private static Account createSyncAccount(Context context) {
        // Create the account type and default account
        Account newAccount = new Account(
                ACCOUNT, context.getString(R.string.account_type));
        // Get an sInstance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(
                        Context.ACCOUNT_SERVICE);
        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
        assert accountManager != null;
        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call context.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */
        } else {
            /*
             * The account exists or some other error occurred. Log this, report it,
             * or handle it internally.
             */
        }
        return newAccount;
    }

    public void startSync(Context context) {
        Bundle settingsBundle = new Bundle();
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_MANUAL, true);
        settingsBundle.putBoolean(
                ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        /*
         * Request the sync for the default account, authority, and
         * manual sync settings
         */
        ContentResolver.requestSync(account,
                context.getString(R.string.content_authority), settingsBundle);
    }
}
