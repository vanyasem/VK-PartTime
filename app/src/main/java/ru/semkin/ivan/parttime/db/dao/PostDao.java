package ru.semkin.ivan.parttime.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.semkin.ivan.parttime.model.Post;

/**
 * Created by Ivan Semkin on 5/10/18
 */
@Dao
public interface PostDao {
    @Query("SELECT * FROM post")
    LiveData<List<Post>> getAll();

    @Query("SELECT * FROM post WHERE uid IN (:postIds)")
    LiveData<List<Post>> loadAllByIds(int[] postIds);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Post... posts);

    @Delete
    void delete(Post post);

    @Query("DELETE FROM post")
    void deleteAll();
}
