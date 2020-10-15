package com.example.foodapp.databasenotes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "NOTES")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val noteText: String
)