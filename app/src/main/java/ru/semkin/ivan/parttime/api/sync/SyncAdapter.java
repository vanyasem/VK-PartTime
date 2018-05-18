package ru.semkin.ivan.parttime.api.sync;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

import ru.semkin.ivan.parttime.api.request.Groups;
import ru.semkin.ivan.parttime.api.request.Messages;
import ru.semkin.ivan.parttime.api.request.Users;
import ru.semkin.ivan.parttime.api.request.Wall;
import timber.log.Timber;

/**
 * Handle the transfer of data between a server and an
 * app, using the Android sync adapter framework.
 */
@SuppressWarnings("WeakerAccess")
public class SyncAdapter extends AbstractThreadedSyncAdapter {
    //...
    // Global variables
    // Define a variable to contain a content resolver instance
    private final ContentResolver mContentResolver;
    private final Context mContext;
    /**
     * Set up the sync adapter
     */
    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        mContext = context;
        mContentResolver = context.getContentResolver();
    }
    //...

    /**
     * Set up the sync adapter. This form of the
     * constructor maintains compatibility with Android 3.0
     * and later platform versions
     */
    public SyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        mContext = context;
        mContentResolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority,
                              ContentProviderClient provider, SyncResult syncResult) {
        Timber.i("Starting sync");

        Users.getUserProfile(null);
        Messages.getHistory(null, mContext);
        Groups.getGroups(null);
        Wall.get(null, mContext);
    }
}