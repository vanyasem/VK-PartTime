package ru.semkin.ivan.parttime.api.request;

import android.content.Context;
import android.content.Intent;

import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

/**
 * Created by Ivan Semkin on 5/7/18
 */
public class AccountSaveProfileInfo {

    private final Context mContext;
    public AccountSaveProfileInfo(Context context) {
        this.mContext = context;
    }

    public static final String USER_SET_SYNC_FINISHED = "UserSetSyncFinishedCast";
    public void getUserProfile() {
        VKRequest request = new VKRequest("account.saveProfileInfo",
                VKParameters.from("status", "test"));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                Intent intent = new Intent(USER_SET_SYNC_FINISHED);
                mContext.sendBroadcast(intent);
            }
            @Override
            public void onError(VKError error) {
                //Do error stuff
            }
            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                //I don't really believe in progress
            }
        });
    }
}
