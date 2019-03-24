package com.example.bucketlist.database;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.bucketlist.model.BucketlistItem;


@Database(entities ={BucketlistItem.class}, version = 1, exportSchema = false)
public abstract class BucketlistDatabase extends RoomDatabase {
    private final static String NAME_DATABASE = "bucketlist_table";

    public abstract BucketlistDao bucketlistDao();

    public static volatile BucketlistDatabase INSTANCE;

    public static BucketlistDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (BucketlistDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        BucketlistDatabase.class, NAME_DATABASE)
                        .build();
                }
            }
        }
        return INSTANCE;
    }
}
