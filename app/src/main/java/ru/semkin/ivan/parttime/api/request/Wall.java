package ru.semkin.ivan.parttime.api.request;

import android.content.Context;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiPost;
import com.vk.sdk.api.model.VKList;

import ru.semkin.ivan.parttime.data.PostRepository;
import ru.semkin.ivan.parttime.model.Post;
import ru.semkin.ivan.parttime.prefs.LoginDataManager;
import timber.log.Timber;

/**
 * Created by Ivan Semkin on 5/15/18
 */
public class Wall {

    private Wall() { }

    public static void get(final VKListCallback<VKApiPost> callback,
                           final Context context) {
        VKRequest request = VKApi.wall().get(
                VKParameters.from(VKApiConst.COUNT, "100",
                        VKApiConst.OWNER_ID, LoginDataManager.getGroupId()));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                //VKList<VKApiPost> posts = (VKList<VKApiPost>) response.parsedModel;
                // Somehow broken upstream ^

                //noinspection unchecked
                VKList<VKApiPost> posts = new VKList(response.json, VKApiPost.class);

                PostRepository postRepository = new PostRepository(context);
                for (VKApiPost post: posts) {
                    postRepository.insert(new Post(post));
                }

                if(callback != null)
                    callback.onFinished(posts);
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
