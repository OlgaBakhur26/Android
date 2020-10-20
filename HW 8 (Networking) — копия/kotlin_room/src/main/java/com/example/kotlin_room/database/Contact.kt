package com.example.kotlin_room.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "CONTACT_TABLE")
data class Contact(
        var personName: String = "Not specified",
        var contactType: CONTACT_TYPE = CONTACT_TYPE.PHONE,
        var contactDetails: String = "Not specified",
        @PrimaryKey @ColumnInfo(index = true)
        val id: String = UUID.randomUUID().toString()
) : Serializable