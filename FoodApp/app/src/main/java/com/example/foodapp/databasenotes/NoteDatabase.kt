package com.example.foodapp.databasenotes

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Note::class), version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getNoteDao(): NoteDao

    companion object {
        private var INSTANCE: NoteDatabase? = null

        fun getDatabaseInstance(context: Context): NoteDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "NOTES"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE as NoteDatabase
        }
    }
}