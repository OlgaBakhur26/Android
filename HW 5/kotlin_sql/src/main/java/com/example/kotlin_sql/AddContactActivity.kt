package com.example.kotlin_sql

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_sql.CONTACT_TYPE.EMAIL
import com.example.kotlin_sql.CONTACT_TYPE.PHONE
import kotlinx.android.synthetic.main.add_contact.*

const val KEY_NEW_CONTACT = "KEY_NEW_CONTACT"

class AddContactActivity : AppCompatActivity() {

    private var actionBar: ActionBar? = null
    private var contact: Contact? = null

    fun newIntent(context: Context?): Intent? {
        return Intent(context, AddContactActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_contact)

        setSupportActionBar(toolbarAddActivity)

        radioPhone?.setOnClickListener(radioButtonListener)
        radioEmail?.setOnClickListener(radioButtonListener)
        saveButton?.setOnClickListener(saveButtonListener)

        actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
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
        personContactField?.hint = "Phone number"
        personContactField?.inputType = InputType.TYPE_CLASS_PHONE
    }

    private fun adjustEmailField() {
        personContactField?.hint = "Email"
        personContactField?.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
    }

    private val saveButtonListener = View.OnClickListener {
        contact = createContact(personNameField?.text.toString(), personContactField?.text.toString())
        val resultIntent = Intent()
        resultIntent.putExtra(KEY_NEW_CONTACT, contact)
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    private fun createContact(nameField: String, contactField: String): Contact{
        if (radioPhone.isChecked) {
            contact = Contact(nameField, PHONE, contactField)
        } else if (radioEmail.isChecked) {
            contact = Contact(nameField, EMAIL, contactField)
        }
        return contact!!
    }
}