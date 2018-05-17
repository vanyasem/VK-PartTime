package ru.semkin.ivan.parttime.api.request;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiCommunity;
import com.vk.sdk.api.model.VKList;

/**
 * Created by Ivan Semkin on 5/15/18
 */
public class Groups {

    private Groups() { }

    public static void getGroups(final VKListCallback<VKApiCommunity> callback) {
        VKRequest request = VKApi.groups().get(
                VKParameters.from(VKApiConst.COUNT, "20", VKApiConst.EXTENDED, 1));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                //noinspection unchecked
                VKList<VKApiCommunity> groups = (VKList<VKApiCommunity>) response.parsedModel;

                if(callback != null)
                    callback.onFinished(groups);
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
