package ru.semkin.ivan.parttime.data;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import ru.semkin.ivan.parttime.api.request.Users;
import ru.semkin.ivan.parttime.db.PartTimeDatabase;
import ru.semkin.ivan.parttime.db.dao.UserDao;
import ru.semkin.ivan.parttime.model.User;

/**
 * Created by Ivan Semkin on 5/11/18
 */
public class UserRepository {

    private final UserDao mUserDao;
    private final LiveData<List<User>> mAllUsers;
    private final Context mContext;

    public UserRepository(Context context) {
        PartTimeDatabase db = PartTimeDatabase.getDatabase(context);
        mContext = context;
        mUserDao = db.userDao();
        mAllUsers = mUserDao.getAll();
    }

    public LiveData<List<User>> getAllUsers() {
        return mAllUsers;
    }

    public LiveData<User> loadById(int id) {
        LiveData<User> db = mUserDao.loadById(id);
        if(db == null) {
            Users.getUsersBrief(null, mContext, id);
        }
        return db;
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
