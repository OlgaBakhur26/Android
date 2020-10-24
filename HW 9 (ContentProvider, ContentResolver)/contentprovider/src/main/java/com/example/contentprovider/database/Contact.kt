package com.example.contentprovider.database

import android.content.ContentValues
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
) : Serializable{

        companion object{
                val contact = Contact()

                @JvmStatic
                fun fromContentValues(contentValues: ContentValues): Contact{
                        if (contentValues.containsKey("personName")){
                                contact.personName = contentValues.getAsString("personName")
                        }
                        if (contentValues.containsKey("contactType")){
                                contact.contactType = CONTACT_TYPE.valueOf(contentValues.getAsString("contactType"))
                        }
                        if (contentValues.containsKey("contactDetails")){
                                contact.contactDetails = contentValues.getAsString("contactDetails")
                        }
                        return contact
                }
        }
}