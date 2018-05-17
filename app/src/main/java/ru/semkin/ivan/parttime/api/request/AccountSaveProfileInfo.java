package ru.semkin.ivan.parttime.api.request;

import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

/**
 * Created by Ivan Semkin on 5/7/18
 */
public class AccountSaveProfileInfo {

    public AccountSaveProfileInfo() { }

    public void getUserProfile(final SimpleCallback callback) {
        VKRequest request = new VKRequest("account.saveProfileInfo",
                VKParameters.from("status", "test"));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                callback.onFinished();
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
