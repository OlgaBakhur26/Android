package com.example.contentprovider

import com.example.contentprovider.database.Contact

interface OnContactClickListener {
    fun onContactClick(contact: Contact)
}