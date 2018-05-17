package ru.semkin.ivan.parttime.api.request;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiDialog;
import com.vk.sdk.api.model.VKApiGetDialogResponse;

import timber.log.Timber;

/**
 * Created by Ivan Semkin on 5/15/18
 */
public class Dialogs {

    private Dialogs() { }

    public static void getDialogs(final VKListCallback<VKApiDialog> callback) {
        VKRequest request = VKApi.messages().getDialogs(
                VKParameters.from(VKApiConst.COUNT, "20", VKApiConst.PREVIEW_LENGTH, "50"));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                //noinspection unchecked
                VKApiGetDialogResponse dialog = (VKApiGetDialogResponse) response.parsedModel;

                if(callback != null)
                    callback.onFinished(dialog.items);
            }
            @Override
            public void onError(VKError error) {
                Timber.e(error.apiError.toString());
                //Do error stuff
            }
            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                //I don't really believe in progress
            }
        });
    }
}
