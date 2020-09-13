package com.example.kotlin_sql.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface ContactDao{

    @Query("SELECT * FROM CONTACT_TABLE")
    fun getAll(): List<Contact>

    @Query("SELECT * FROM CONTACT_TABLE WHERE id LIKE :id")
    fun getById(id: String): Contact


    @Insert(onConflict = REPLACE)
    fun insert(contact: Contact)

    @Update
    fun update(contact: Contact)

    @Delete
    fun delete(contact: Contact)

}