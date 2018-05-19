package ru.semkin.ivan.parttime.ui.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ru.semkin.ivan.parttime.data.MessageRepository;
import ru.semkin.ivan.parttime.model.Message;

/**
 * Created by Ivan Semkin on 5/11/18
 */
public class MessageViewModel extends AndroidViewModel {

    private final MessageRepository mRepository;
    private final LiveData<List<Message>> mAllMessages;

    public MessageViewModel(Application application) {
        super(application);
        mRepository = new MessageRepository(application);
        mAllMessages = mRepository.getAllMessages();
    }

    public LiveData<List<Message>> getAllMessages() { return mAllMessages; }
}
