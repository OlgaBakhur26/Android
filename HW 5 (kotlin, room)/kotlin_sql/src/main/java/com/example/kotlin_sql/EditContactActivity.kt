package com.example.kotlin_sql

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_sql.database.AppDatabase
import com.example.kotlin_sql.database.Contact
import com.example.kotlin_sql.database.ContactDao
import kotlinx.android.synthetic.main.edit_contact.*


class EditContactActivity : AppCompatActivity() {

    private var appDatabase: AppDatabase? = null
    private var contactDao: ContactDao? = null

    private var actionBar: ActionBar? = null

    private var contact: Contact? = null
    private var contactId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_contact)

        setSupportActionBar(toolbarEditActivity)
        actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        removeButton.setOnClickListener(removeButtonListener)

        appDatabase = AppDatabase.getAppDatabaseInstance(this)
        contactDao = appDatabase?.getContactDao()

        receiveContactId()
        contact = contactDao?.getById(contactId!!)
    }

    private fun receiveContactId() {
        val intent = intent
        contactId = intent.getStringExtra(CONTACT_FOR_EDITING)
    }

    private fun editContact(contact: Contact) {
        contact.personName = personNameField.text.toString()
        contact.contactDetails = personContactField.text.toString()
        contactDao?.update(contact)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            editContact(contact!!)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private val removeButtonListener = View.OnClickListener {
        contactDao?.delete(contact!!)
        finish()
    }
}