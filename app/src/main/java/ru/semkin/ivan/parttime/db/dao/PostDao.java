package ru.semkin.ivan.parttime.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.semkin.ivan.parttime.model.Post;

/**
 * Created by Ivan Semkin on 5/10/18
 */
@SuppressWarnings("unused")
@Dao
public interface PostDao {
    @Query("SELECT * FROM post")
    LiveData<List<Post>> getAll();

    @Query("SELECT * FROM post WHERE uid = :postId")
    LiveData<Post> loadById(int postId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Post... posts);

    @Query("DELETE FROM post")
    void deleteAll();
}
