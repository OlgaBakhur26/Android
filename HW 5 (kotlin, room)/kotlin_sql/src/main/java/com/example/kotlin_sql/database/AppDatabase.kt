package com.example.kotlin_sql.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Contact::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getContactDao(): ContactDao
    private var appDatabaseInstance: AppDatabase? = null

    fun getAppDatabaseInstance(context: Context): AppDatabase{
        if (appDatabaseInstance == null){
            appDatabaseInstance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    CONTACT_TABLE)
                    .allowMainThreadQueries()
                    .build()
        }
        return appDatabaseInstance as AppDatabase
    }
}