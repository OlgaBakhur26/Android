package com.example.contentprovider


import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.contentprovider.database.AppDatabase
import com.example.contentprovider.database.Contact
import com.example.contentprovider.database.ContactDao
import kotlinx.android.synthetic.main.activity_edit_contact.*

class EditContactActivity : AppCompatActivity() {

    private lateinit var contactDao: ContactDao
    private var contactId: String? = null
    private lateinit var contactToEdit: Contact

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_contact)

        setSupportActionBar(toolbarEditActivity)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val appDatabase = AppDatabase.getAppDatabaseInstance(this)
        contactDao = appDatabase.getContactDao()

        receiveContactId()
        contactId?.let { setFields(it) }
        removeButton.setOnClickListener(View.OnClickListener {
            contactDao.delete(contactToEdit)
            finish()
        })
    }

    private fun receiveContactId() {
        val intent = intent
        contactId = intent.getStringExtra(CONTACT_FOR_EDITING)
    }

    private fun setFields(contactID: String) {
        contactToEdit = contactDao.getById(contactID)
        personNameField.setText(contactToEdit.personName)
        personContactField.setText(contactToEdit.contactDetails)
    }

    private fun editContact() {
        contactToEdit.personName = personNameField.text.toString()
        contactToEdit.contactDetails = personContactField.text.toString()
        contactDao.update(contactToEdit)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            editContact()
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}