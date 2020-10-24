package com.example.contentprovider.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = arrayOf(Contact::class), version = 1, exportSchema = false)
@TypeConverters(EnumTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getContactDao(): ContactDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        @JvmStatic
        fun getAppDatabaseInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "CONTACT_TABLE"
                )
                        .allowMainThreadQueries()
                        .build()
            }
            return INSTANCE as AppDatabase
        }
    }
}