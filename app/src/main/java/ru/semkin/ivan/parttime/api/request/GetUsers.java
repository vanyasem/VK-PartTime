package ru.semkin.ivan.parttime.api.request;

import android.content.Context;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKList;

import ru.semkin.ivan.parttime.prefs.ProfileDataManager;

/**
 * Created by Ivan Semkin on 5/7/18
 */
public class GetUsers {

    public GetUsers(Context context) { }

    public void getUserProfile(final SimpleCallback callback) {
        VKRequest request = VKApi.users().get(
                VKParameters.from(VKApiConst.FIELDS, "sex,bdate,photo_200,city,country,activity"));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                //noinspection unchecked
                VKList<VKApiUserFull> user = (VKList<VKApiUserFull>) response.parsedModel;
                ProfileDataManager.setUserName(
                        user.get(0).first_name + " " + user.get(0).last_name);
                ProfileDataManager.setUserGender(user.get(0).sex);
                ProfileDataManager.setUserBirthday(user.get(0).bdate);
                ProfileDataManager.setUserCity(user.get(0).city.title);
                ProfileDataManager.setUserCountry(user.get(0).country.title);
                ProfileDataManager.setProfilePicture(user.get(0).photo_200);
                ProfileDataManager.setUserStatus(user.get(0).activity);

                if(callback != null)
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

    public void getUsersBrief(final VKListCallback<VKApiUserFull> callback, long... userId) {
        VKRequest request = VKApi.users().get(
                VKParameters.from(VKApiConst.USER_IDS, idsToString(userId), VKApiConst.FIELDS, "photo_100"));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                //noinspection unchecked
                VKList<VKApiUserFull> users = (VKList<VKApiUserFull>) response.parsedModel;

                if(callback != null)
                    callback.onFinished(users);
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

    private static String idsToString(long... userId) {
        StringBuilder result = new StringBuilder();

        for (long anUserId : userId) {
            result.append(String.valueOf(anUserId)).append(",");
        }
        return result.toString();
    }
}
