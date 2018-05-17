package ru.semkin.ivan.parttime.api.request;

import android.content.Context;
import android.content.Intent;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiGetDialogResponse;

/**
 * Created by Ivan Semkin on 5/15/18
 */
public class GetDialogs {

    private final Context mContext;
    public GetDialogs(Context context) {
        this.mContext = context;
    }

    public static final String DIALOGS_GET_SYNC_FINISHED = "DialogsGetSyncFinishedCast";
    public static final String EXTRA_DIALOGS = "dialogs";

    public void getDialogs() {
        VKRequest request = VKApi.messages().getDialogs(
                VKParameters.from(VKApiConst.COUNT, "20", VKApiConst.PREVIEW_LENGTH, "50"));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                //noinspection unchecked
                VKApiGetDialogResponse dialog = (VKApiGetDialogResponse) response.parsedModel;

                Intent intent = new Intent(DIALOGS_GET_SYNC_FINISHED);
                intent.putExtra(EXTRA_DIALOGS, dialog.items);
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
