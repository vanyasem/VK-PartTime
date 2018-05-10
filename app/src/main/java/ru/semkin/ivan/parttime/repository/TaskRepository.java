package ru.semkin.ivan.parttime.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import ru.semkin.ivan.parttime.db.PartTimeDatabase;
import ru.semkin.ivan.parttime.db.entity.Task;
import ru.semkin.ivan.parttime.db.entity.TaskDao;

/**
 * Created by Ivan Semkin on 5/11/18
 */
public class TaskRepository {

    private TaskDao mTaskDao;
    private LiveData<List<Task>> mAllTasks;

    public TaskRepository(Application application) {
        PartTimeDatabase db = PartTimeDatabase.getDatabase(application);
        mTaskDao = db.taskDao();
        mAllTasks = mTaskDao.getAll();
    }

    public LiveData<List<Task>> getAllTasks() {
        return mAllTasks;
    }

    public void insert(Task task) {
        new insertAsyncTask(mTaskDao).execute(task);
    }

    private static class insertAsyncTask extends AsyncTask<Task, Void, Void> {

        private TaskDao mAsyncTaskDao;

        insertAsyncTask(TaskDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Task... params) {
            mAsyncTaskDao.insertAll(params[0]);
            return null;
        }
    }
}
