package com.example.foodapp.databasenotes

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "NOTES")
data class Note(
    @PrimaryKey()
    val id: String = UUID.randomUUID().toString(),
    val dishId: String,
    var noteText: String
)