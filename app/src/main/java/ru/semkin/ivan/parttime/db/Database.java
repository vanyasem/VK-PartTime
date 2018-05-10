package ru.semkin.ivan.parttime.db;

import android.arch.persistence.room.Room;
import android.content.Context;

/**
 * Created by Ivan Semkin on 5/10/18
 */
public class Database {

    private static PartTimeDatabase instance = null;

    protected Database() {

    }

    public static PartTimeDatabase getInstance(Context mContext) {
        if (instance == null) {
            instance = Room.databaseBuilder(mContext,
                    PartTimeDatabase.class, "database-name").build();
        }
        return instance;
    }
}