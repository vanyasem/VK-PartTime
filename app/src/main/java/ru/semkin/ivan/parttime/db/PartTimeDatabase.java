package ru.semkin.ivan.parttime.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import ru.semkin.ivan.parttime.db.entity.Task;
import ru.semkin.ivan.parttime.db.entity.TaskDao;

/**
 * Created by Ivan Semkin on 5/10/18
 */
@Database(entities = {Task.class}, version = 1)
public abstract class PartTimeDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();


    private static PartTimeDatabase instance = null;

    public static PartTimeDatabase getDatabase(Context mContext) {
        if (instance == null) {
            instance = Room.databaseBuilder(mContext,
                    PartTimeDatabase.class, "parttime_database")
                    .addCallback(sRoomDatabaseCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(instance).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final TaskDao mDao;

        PopulateDbAsync(PartTimeDatabase db) {
            mDao = db.taskDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            Task task = new Task("Hello", "World");
            mDao.insertAll(task);
            task = new Task("Люблю", "Свету");
            mDao.insertAll(task);
            return null;
        }
    }
}