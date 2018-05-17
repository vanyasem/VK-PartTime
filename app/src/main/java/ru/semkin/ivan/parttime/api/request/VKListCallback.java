package ru.semkin.ivan.parttime.api.request;

import com.vk.sdk.api.model.Identifiable;
import com.vk.sdk.api.model.VKApiModel;
import com.vk.sdk.api.model.VKList;

/**
 * Created by Ivan Semkin on 5/17/18
 */
public interface VKListCallback<E extends VKApiModel & Identifiable> {
    void onFinished(VKList<E> items);
}
