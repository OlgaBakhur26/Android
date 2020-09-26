package com.example.kotlin_sql

import com.example.kotlin_sql.database.Contact

interface OnContactClickListener {
    fun onContactClick(contact: Contact)
}