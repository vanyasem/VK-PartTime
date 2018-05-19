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
import ru.semkin.ivan.parttime.data.TaskRepository;
import ru.semkin.ivan.parttime.model.Message;
import ru.semkin.ivan.parttime.model.Task;
import ru.semkin.ivan.parttime.prefs.LoginDataManager;
import timber.log.Timber;

/**
 * Created by Ivan Semkin on 5/15/18
 */
public class Messages {

    private Messages() { }

    public static void getHistory(final VKListCallback<VKApiMessage> callback,
                                  final Context context) {
        long userId = LoginDataManager.getChatId();
        if(LoginDataManager.isGroupChat())
            userId += 2000000000L;
        VKRequest request = new VKRequest("messages.getHistory",
                VKParameters.from(VKApiConst.COUNT, "5",
                        VKApiConst.USER_ID, userId));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                //VKList<VKApiMessage> messages = (VKList<VKApiMessage>) response.parsedModel;
                // Somehow broken upstream ^

                //noinspection unchecked
                VKList<VKApiMessage> messages = new VKList(response.json, VKApiMessage.class);

                MessageRepository messageRepository = new MessageRepository(context);
                TaskRepository taskRepository = new TaskRepository(context);
                for (VKApiMessage message: messages) {
                    messageRepository.insert(new Message(message));
                    taskRepository.insert(new Task(message));
                }

                if(callback != null)
                    callback.onFinished(messages);
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

    public static void send(final SimpleCallback callback, String message) {
        send(callback, message, -1);
    }

    public static void send(final SimpleCallback callback, String message, long replyTo) {
        VKRequest request;
        String field = VKApiConst.USER_ID;
        if(LoginDataManager.isGroupChat()) {
            field = "chat_id";
        }
        request = new VKRequest("messages.send",
                VKParameters.from(VKApiConst.MESSAGE, message,
                        field, LoginDataManager.getChatId(),
                        "forward_messages", replyTo));
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

    public static void delete(final SimpleCallback callback, long messageId) {
        VKRequest request = new VKRequest("messages.delete",
                VKParameters.from("message_ids", messageId,
                        "delete_for_all", "1"));
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
