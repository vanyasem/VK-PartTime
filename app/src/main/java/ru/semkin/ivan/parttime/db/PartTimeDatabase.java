package ru.semkin.ivan.parttime.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import ru.semkin.ivan.parttime.db.entity.Task;
import ru.semkin.ivan.parttime.db.entity.TaskDao;

/**
 * Created by Ivan Semkin on 5/10/18
 */
@Database(entities = {Task.class}, version = 1)
public abstract class PartTimeDatabase extends RoomDatabase {
    public abstract TaskDao userDao();
}