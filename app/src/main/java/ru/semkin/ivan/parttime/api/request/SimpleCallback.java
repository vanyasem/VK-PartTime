package ru.semkin.ivan.parttime.api.request;

import com.vk.sdk.api.model.Identifiable;
import com.vk.sdk.api.model.VKApiModel;

/**
 * Created by Ivan Semkin on 5/17/18
 */
public interface SimpleCallback<E extends VKApiModel & Identifiable> {
    void onFinished();
}
