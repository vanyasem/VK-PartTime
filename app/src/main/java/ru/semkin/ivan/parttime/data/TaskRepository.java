package ru.semkin.ivan.parttime.data;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import ru.semkin.ivan.parttime.db.PartTimeDatabase;
import ru.semkin.ivan.parttime.db.dao.TaskDao;
import ru.semkin.ivan.parttime.model.Task;

/**
 * Created by Ivan Semkin on 5/11/18
 */
public class TaskRepository {

    private final TaskDao mTaskDao;
    private final LiveData<List<Task>> mAllTasks;

    public TaskRepository(Context context) {
        PartTimeDatabase db = PartTimeDatabase.getDatabase(context);
        mTaskDao = db.taskDao();
        mAllTasks = mTaskDao.getAll();
    }

    public LiveData<List<Task>> getAllTasks() {
        return mAllTasks;
    }

    public LiveData<List<Task>> getAllActive() {
        return mTaskDao.getAllActive();
    }

    public void markDone(Task task) {
        new markDoneAsyncTask(mTaskDao).execute(task);
    }

    public void insert(Task task) {
        new insertAsyncTask(mTaskDao).execute(task);
    }

    private static class insertAsyncTask extends AsyncTask<Task, Void, Void> {

        private final TaskDao mAsyncTaskDao;

        insertAsyncTask(TaskDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Task... params) {
            mAsyncTaskDao.insertAll(params[0]);
            return null;
        }
    }

    private static class markDoneAsyncTask extends AsyncTask<Task, Void, Void> {

        private final TaskDao mAsyncTaskDao;

        markDoneAsyncTask(TaskDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Task... task) {
            mAsyncTaskDao.markDone(task[0]);
            return null;
        }
    }
}
