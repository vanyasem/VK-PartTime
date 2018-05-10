package ru.semkin.ivan.parttime.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
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

    @Query("SELECT * FROM task WHERE uid IN (:userIds)")
    LiveData<List<Task>> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM task WHERE first_name LIKE :first AND "
            + "last_name LIKE :last LIMIT 1")
    LiveData<Task> findByName(String first, String last);

    @Insert
    void insertAll(Task... users);

    @Delete
    void delete(Task user);

    @Query("DELETE FROM task")
    void deleteAll();
}
