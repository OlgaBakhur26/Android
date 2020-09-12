package com.example.kotlin_sql.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kotlin_sql.CONTACT_TYPE
import java.io.Serializable
import java.util.*

const val CONTACT_TABLE: String = "CONTACT_TABLE"

@Entity(tableName = CONTACT_TABLE)
data class Contact(
        internal var personName: String = "Not specified",
        internal var contactType: CONTACT_TYPE = CONTACT_TYPE.PHONE,
        internal var contactDetails: String = "Not specified",
        @PrimaryKey @ColumnInfo(index = true) internal val id: String = UUID.randomUUID().toString()
) : Serializable