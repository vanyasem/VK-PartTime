package ru.semkin.ivan.parttime.data;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import ru.semkin.ivan.parttime.db.PartTimeDatabase;
import ru.semkin.ivan.parttime.db.dao.MessageDao;
import ru.semkin.ivan.parttime.model.Message;

/**
 * Created by Ivan Semkin on 5/11/18
 */
public class MessageRepository {

    private final MessageDao mMessageDao;
    private final LiveData<List<Message>> mAllMessages;

    public MessageRepository(Context context) {
        PartTimeDatabase db = PartTimeDatabase.getDatabase(context);
        mMessageDao = db.messageDao();
        mAllMessages = mMessageDao.getAll();
    }

    public LiveData<List<Message>> getAllMessages() {
        return mAllMessages;
    }

    public void insert(Message... message) {
        new insertAsyncTask(mMessageDao).execute(message);
    }

    private static class insertAsyncTask extends AsyncTask<Message, Void, Void> {

        private final MessageDao mAsyncTaskDao;

        insertAsyncTask(MessageDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Message... params) {
            mAsyncTaskDao.insertAll(params[0]);
            return null;
        }
    }
}
