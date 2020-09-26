package com.example.kotlin_sql

import android.os.Bundle
import android.text.InputType.TYPE_CLASS_PHONE
import android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_sql.database.CONTACT_TYPE.EMAIL
import com.example.kotlin_sql.database.CONTACT_TYPE.PHONE
import com.example.kotlin_sql.database.AppDatabase
import com.example.kotlin_sql.database.Contact
import com.example.kotlin_sql.database.ContactDao
import kotlinx.android.synthetic.main.add_contact.*

class AddContactActivity : AppCompatActivity() {

    private lateinit var contactDao: ContactDao
    private lateinit var contact: Contact

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_contact)

        setSupportActionBar(toolbarAddActivity)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val appDatabase = AppDatabase.getAppDatabaseInstance(this)
        contactDao = appDatabase.getContactDao()

        radioPhone.setOnClickListener(radioButtonListener)
        radioEmail.setOnClickListener(radioButtonListener)
        saveButton.setOnClickListener(View.OnClickListener {
            thread.start()
            finish()
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private val radioButtonListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.radioPhone -> adjustPhoneField()
            R.id.radioEmail -> adjustEmailField()
        }
    }

    private fun adjustPhoneField() {
        personContactField.apply {
            hint = "Phone number"
            inputType = TYPE_CLASS_PHONE
        }
    }

    private fun adjustEmailField() {
        personContactField.apply {
            hint = "Email"
            inputType = TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        }
    }

    private val thread = Thread(Runnable {
        // contact = when {...} - when с возвращаемым типом сделать не получится, т.к. тогда нужна
        // еще и ветка else, которой ничего кроме null не присвоишь. Null я присвоить контакту не могу,
        // поскольку в свойствах класса он определен с lateinit.
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
        contactDao.insert(contact)
    })
}

