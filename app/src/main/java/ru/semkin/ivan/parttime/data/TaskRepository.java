package ru.semkin.ivan.parttime.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import ru.semkin.ivan.parttime.db.PartTimeDatabase;
import ru.semkin.ivan.parttime.model.Task;
import ru.semkin.ivan.parttime.db.TaskDao;

/**
 * Created by Ivan Semkin on 5/11/18
 */
public class TaskRepository {

    private final TaskDao mTaskDao;
    private final LiveData<List<Task>> mAllTasks;

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
}
