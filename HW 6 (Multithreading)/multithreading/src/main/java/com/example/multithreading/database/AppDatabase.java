package com.example.multithreading.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Contact.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ContactDao getContactDao();
    private static volatile AppDatabase appDatabase;

    public static AppDatabase getAppDatabaseInstance(Context context){
        if(appDatabase == null){
            appDatabase = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "CONTACT_TABLE")
                    .build();
        }
        return appDatabase;
    }
}
