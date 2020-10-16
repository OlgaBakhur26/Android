package com.example.foodapp.databasenotes

import androidx.room.*
import com.example.foodapp.repositorydishbyid.DishByIdDataModel

@Dao
interface NoteDao {

    @Query("SELECT * FROM NOTES  WHERE dishId LIKE :dishId")
    fun getAll(dishId: String): List<Note>

    @Query("SELECT * FROM NOTES WHERE id LIKE :id")
    fun getById(id: String): Note

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)
}