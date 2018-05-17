package ru.semkin.ivan.parttime.data;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import ru.semkin.ivan.parttime.db.PartTimeDatabase;
import ru.semkin.ivan.parttime.db.dao.CommentDao;
import ru.semkin.ivan.parttime.model.Comment;

/**
 * Created by Ivan Semkin on 5/11/18
 */
public class CommentRepository {

    private final CommentDao mCommentDao;
    private final LiveData<List<Comment>> mAllComments;

    public CommentRepository(Context context) {
        PartTimeDatabase db = PartTimeDatabase.getDatabase(context);
        mCommentDao = db.commentDao();
        mAllComments = mCommentDao.getAll();
    }

    public LiveData<List<Comment>> getAllComments() {
        return mAllComments;
    }

    public void insert(Comment comment) {
        new insertAsyncTask(mCommentDao).execute(comment);
    }

    private static class insertAsyncTask extends AsyncTask<Comment, Void, Void> {

        private final CommentDao mAsyncTaskDao;

        insertAsyncTask(CommentDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Comment... params) {
            mAsyncTaskDao.insertAll(params[0]);
            return null;
        }
    }
}
