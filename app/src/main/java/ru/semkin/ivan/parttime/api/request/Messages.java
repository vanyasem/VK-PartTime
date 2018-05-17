package ru.semkin.ivan.parttime.api.request;

import android.content.Context;

import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiMessage;
import com.vk.sdk.api.model.VKList;

import ru.semkin.ivan.parttime.data.MessageRepository;
import ru.semkin.ivan.parttime.model.Message;
import ru.semkin.ivan.parttime.prefs.LoginDataManager;
import timber.log.Timber;

/**
 * Created by Ivan Semkin on 5/15/18
 */
public class Messages {

    private Messages() { }

    public static void getDialogs(final VKListCallback<VKApiMessage> callback,
                                  final Context context) {
        VKRequest request = new VKRequest("messages.getHistory",
                VKParameters.from(VKApiConst.COUNT, "200",
                        VKApiConst.PEER_ID, LoginDataManager.getChatId()));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                //VKList<VKApiMessage> messages = (VKList<VKApiMessage>) response.parsedModel;
                // Somehow broken upstream ^

                //noinspection unchecked
                VKList<VKApiMessage> messages = new VKList(response.json, VKApiMessage.class);

                MessageRepository messageRepository = new MessageRepository(context);
                for (VKApiMessage message: messages) {
                    messageRepository.insert(new Message(message));
                }

                if(callback != null)
                    callback.onFinished(messages);
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

    public static void send(final SimpleCallback callback, String message) {
        VKRequest request = new VKRequest("messages.send",
                VKParameters.from(VKApiConst.MESSAGE, message,
                        VKApiConst.PEER_ID, LoginDataManager.getChatId()));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                if(callback != null)
                    callback.onFinished();
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
