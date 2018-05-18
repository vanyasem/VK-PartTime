package ru.semkin.ivan.parttime.data;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKList;

import ru.semkin.ivan.parttime.api.request.GenericCallback;
import ru.semkin.ivan.parttime.api.request.Users;
import ru.semkin.ivan.parttime.api.request.VKListCallback;
import ru.semkin.ivan.parttime.db.PartTimeDatabase;
import ru.semkin.ivan.parttime.db.dao.UserDao;
import ru.semkin.ivan.parttime.model.User;

/**
 * Created by Ivan Semkin on 5/11/18
 */
public class UserRepository {

    private final UserDao mUserDao;
    private final Context mContext;

    public UserRepository(Context context) {
        PartTimeDatabase db = PartTimeDatabase.getDatabase(context);
        mContext = context;
        mUserDao = db.userDao();
    }

    public void loadById(int id, final GenericCallback<User> callback) {
        LiveData<User> db = mUserDao.loadById(id);
        if(db.getValue() != null)
            callback.onFinished(db.getValue());
        else {
            Users.getUsersBrief(new VKListCallback<VKApiUser>() {
                @Override
                public void onFinished(VKList<VKApiUser> items) {
                    callback.onFinished(new User(items.get(0)));
                }
            }, mContext, id);
        }
    }

    public void insert(User... user) {
        new insertAsyncTask(mUserDao).execute(user);
    }

    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {

        private final UserDao mAsyncTaskDao;

        insertAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            mAsyncTaskDao.insertAll(params[0]);
            return null;
        }
    }
}
