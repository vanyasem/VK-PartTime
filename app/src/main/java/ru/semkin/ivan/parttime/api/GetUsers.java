package ru.semkin.ivan.parttime.api;

import android.content.Context;
import android.content.Intent;

import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKList;

import ru.semkin.ivan.parttime.datamanager.ProfileDataManager;

/**
 * Created by Ivan Semkin on 5/7/18
 */
public class GetUsers {

    private final Context mContext;
    public GetUsers(Context context) {
        this.mContext = context;
    }

    public static final String USER_GET_SYNC_FINISHED = "UserGetSyncFinishedCast";
    public void getUserProfile() {
        VKRequest request = VKApi.users().get(
                VKParameters.from(VKApiConst.FIELDS, "sex,bdate,photo_100,city,country,activity"));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                VKList<VKApiUserFull> user = (VKList<VKApiUserFull>) response.parsedModel;
                ProfileDataManager.setUserName(
                        user.get(0).first_name + " " + user.get(0).last_name);
                ProfileDataManager.setUserGender(user.get(0).sex);
                ProfileDataManager.setUserBirthday(user.get(0).bdate);
                ProfileDataManager.setUserCity(user.get(0).city.title);
                ProfileDataManager.setUserCountry(user.get(0).country.title);
                ProfileDataManager.setProfilePicture(user.get(0).photo_100);
                ProfileDataManager.setUserStatus(user.get(0).activity);

                Intent intent = new Intent(USER_GET_SYNC_FINISHED);
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
