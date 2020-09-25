package com.example.kotlin_room

import com.example.kotlin_room.database.Contact

interface OnContactClickListener {
    fun onContactClick(contact: Contact)
}