package com.example.kotlin_sql

import java.io.Serializable
import java.util.*

class Contact(
        internal var personName: String = "not specified",
        internal var contactType: CONTACT_TYPE = CONTACT_TYPE.PHONE,
        internal var contactDetails: String = "not specified",
        internal val id: String = UUID.randomUUID().toString()
) : Serializable{
    // Пробовала инициализировать id в свойствах класса и в блоке init - закрвть это свойство
    // для ввода при создании объекта не получилось (задавала этот вопрос в чате).

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Contact

        if (personName != other.personName) return false
        if (contactType != other.contactType) return false
        if (contactDetails != other.contactDetails) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = personName.hashCode()
        result = 31 * result + contactType.hashCode()
        result = 31 * result + contactDetails.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }
}