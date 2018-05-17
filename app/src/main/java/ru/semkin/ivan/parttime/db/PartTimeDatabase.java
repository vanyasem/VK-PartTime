package ru.semkin.ivan.parttime.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

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
                    .addCallback(sRoomDatabaseCallback)
                    .fallbackToDestructiveMigration() //TODO remove upon release and implement proper migrations
                    .build();
        }
        return instance;
    }

    private static final RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    //new PopulateDbAsync(instance).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final TaskDao mTaskDao;
        private final MessageDao mMessageDao;
        private final PostDao mPostDao;

        PopulateDbAsync(PartTimeDatabase db) {
            mTaskDao = db.taskDao();
            mMessageDao = db.messageDao();
            mPostDao = db.postDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mTaskDao.deleteAll();
            Task task = new Task("Hello", "World");
            mTaskDao.insertAll(task);
            task = new Task("Люблю", "Свету");
            mTaskDao.insertAll(task);

            mMessageDao.deleteAll();
            Message message = new Message("Hello World", 1, 1, true, false);
            mMessageDao.insertAll(message);
            message = new Message("Люблю Свету", 2, 2, true, true);
            mMessageDao.insertAll(message);

            mPostDao.deleteAll();
            Post post = new Post("Hello World", 1);
            mPostDao.insertAll(post);
            post = new Post("Люблю Свету", 2);
            mPostDao.insertAll(post);
            return null;
        }
    }
}