package ru.semkin.ivan.parttime.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.semkin.ivan.parttime.model.Task;

/**
 * Created by Ivan Semkin on 5/10/18
 */
@Dao
public interface TaskDao {
    @Query("SELECT * FROM task")
    LiveData<List<Task>> getAll();

    @Query("SELECT * FROM task WHERE done = 0")
    LiveData<List<Task>> getAllActive();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Task... tasks);

    @Delete
    void delete(Task task);

    @Query("DELETE FROM task")
    void deleteAll();
}
