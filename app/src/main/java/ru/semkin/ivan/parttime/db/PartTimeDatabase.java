package ru.semkin.ivan.parttime.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import ru.semkin.ivan.parttime.db.dao.CommentDao;
import ru.semkin.ivan.parttime.db.dao.MessageDao;
import ru.semkin.ivan.parttime.db.dao.PostDao;
import ru.semkin.ivan.parttime.db.dao.TaskDao;
import ru.semkin.ivan.parttime.db.dao.UserDao;
import ru.semkin.ivan.parttime.model.Comment;
import ru.semkin.ivan.parttime.model.Message;
import ru.semkin.ivan.parttime.model.Post;
import ru.semkin.ivan.parttime.model.Task;
import ru.semkin.ivan.parttime.model.User;

/**
 * Created by Ivan Semkin on 5/10/18
 */
@Database(entities = {Task.class, Message.class, Post.class, Comment.class, User.class}, version = 4)
public abstract class PartTimeDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();
    public abstract MessageDao messageDao();
    public abstract PostDao postDao();
    public abstract CommentDao commentDao();
    public abstract UserDao userDao();


    private static PartTimeDatabase instance = null;

    public static PartTimeDatabase getDatabase(Context mContext) {
        if (instance == null) {
            instance = Room.databaseBuilder(mContext,
                    PartTimeDatabase.class, "parttime_database")
                    .fallbackToDestructiveMigration() //TODO remove upon release and implement proper migrations
                    .build();
        }
        return instance;
    }
}