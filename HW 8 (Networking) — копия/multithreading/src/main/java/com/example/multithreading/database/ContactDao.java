package com.example.multithreading.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface ContactDao {

    @Query("SELECT * FROM CONTACT_TABLE")
    List<Contact> getAll();

    @Query("SELECT * FROM CONTACT_TABLE WHERE id LIKE :id")
    Contact getById(String id);

    @Insert(onConflict = REPLACE)
    void insert(Contact contact);

    @Update
    void update(Contact contact);

    @Delete
    void delete(Contact contact);
}
