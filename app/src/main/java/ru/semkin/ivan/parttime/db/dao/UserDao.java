package ru.semkin.ivan.parttime.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.semkin.ivan.parttime.model.User;

/**
 * Created by Ivan Semkin on 5/10/18
 */
@SuppressWarnings("unused")
@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    LiveData<List<User>> getAll();

    @Query("SELECT * FROM user WHERE uid = :userId")
    LiveData<User> loadById(int userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(User... users);

    @Delete
    void delete(User user);

    @Query("DELETE FROM user")
    void deleteAll();
}
