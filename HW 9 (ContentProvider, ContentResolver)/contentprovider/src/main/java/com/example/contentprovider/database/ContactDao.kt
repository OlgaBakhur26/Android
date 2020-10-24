package com.example.contentprovider.database

import android.database.Cursor
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface ContactDao{

    @Query("SELECT * FROM CONTACT_TABLE")
    fun getAll(): List<Contact>

    @Query("SELECT * FROM CONTACT_TABLE")
    fun getAllCursor(): Cursor

    @Query("SELECT * FROM CONTACT_TABLE WHERE id LIKE :id")
    fun getById(id: String): Contact

    @Query("SELECT * FROM CONTACT_TABLE WHERE id LIKE :id")
    fun getByIdCursor(id: String): Cursor

    @Insert(onConflict = REPLACE)
    fun insert(contact: Contact): Int

    @Update
    fun update(contact: Contact): Int

    @Delete
    fun delete(contact: Contact): Int

    @Delete
    fun delete(uriId: Long): Int
}