package ru.semkin.ivan.parttime.db.entity;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Ivan Semkin on 5/10/18
 */
@Dao
public interface TaskDao {
    @Query("SELECT * FROM task")
    List<Task> getAll();

    @Query("SELECT * FROM task WHERE uid IN (:userIds)")
    List<Task> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM task WHERE first_name LIKE :first AND "
            + "last_name LIKE :last LIMIT 1")
    Task findByName(String first, String last);

    @Insert
    void insertAll(Task... users);

    @Delete
    void delete(Task user);
}
