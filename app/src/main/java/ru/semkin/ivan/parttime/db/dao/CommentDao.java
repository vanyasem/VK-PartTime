package ru.semkin.ivan.parttime.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ru.semkin.ivan.parttime.model.Comment;

/**
 * Created by Ivan Semkin on 5/10/18
 */
@Dao
public interface CommentDao {
    @Query("SELECT * FROM comment")
    LiveData<List<Comment>> getAll();

    @Query("SELECT * FROM comment WHERE uid = :postId")
    LiveData<List<Comment>> loadAllByPostId(int postId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Comment... comments);

    @Delete
    void delete(Comment comment);

    @Query("DELETE FROM comment")
    void deleteAll();
}
