package com.example.kotlin_sql

import android.os.Bundle
import android.text.InputType
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_sql.CONTACT_TYPE.EMAIL
import com.example.kotlin_sql.CONTACT_TYPE.PHONE
import com.example.kotlin_sql.database.AppDatabase
import com.example.kotlin_sql.database.Contact
import com.example.kotlin_sql.database.ContactDao
import kotlinx.android.synthetic.main.add_contact.*


class AddContactActivity : AppCompatActivity() {

    private var appDatabase: AppDatabase? = null
    private var contactDao: ContactDao? = null

    private var actionBar: ActionBar? = null
    private var contact: Contact? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_contact)

        setSupportActionBar(toolbarAddActivity)
        actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        appDatabase = AppDatabase.getAppDatabaseInstance(this)
        contactDao = appDatabase?.getContactDao()

        radioPhone.setOnClickListener(radioButtonListener)
        radioEmail.setOnClickListener(radioButtonListener)
        saveButton.setOnClickListener(View.OnClickListener {
            thread.start()
            if (!thread.isAlive){
                finish()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private var radioButtonListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.radioPhone -> adjustPhoneField()
            R.id.radioEmail -> adjustEmailField()
        }
    }

    private fun adjustPhoneField() {
        personContactField.hint = "Phone number"
        personContactField.inputType = InputType.TYPE_CLASS_PHONE
    }

    private fun adjustEmailField() {
        personContactField.hint = "Email"
        personContactField.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
    }

    private val thread = Thread(Runnable {
        when {
            radioPhone.isChecked -> contact = Contact(
                    personName = personNameField.text.toString(),
                    contactType = PHONE,
                    contactDetails = personContactField.text.toString())
            radioEmail.isChecked -> contact = Contact(
                    personName = personNameField.text.toString(),
                    contactType = EMAIL,
                    contactDetails = personContactField.text.toString())
        }
        contactDao?.insert(contact!!)
    })
}

