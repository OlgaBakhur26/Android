package com.example.kotlin_sql

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_sql.CONTACT_MODIFICATION.EDIT
import com.example.kotlin_sql.CONTACT_MODIFICATION.REMOVE
import kotlinx.android.synthetic.main.edit_contact.*

const val KEY_CHANGED_CONTACT: String = "KEY CHANGED CONTACT"
const val REMOVE_OR_EDIT_CONTACT: String = "REMOVE OR EDIT CONTACT"
private const val KEY_CONTACT_FOR_EDITING: String = "CONTACT FOR EDITING"

class EditContactActivity : AppCompatActivity() {

    private var actionBar: ActionBar? = null
    private var receivedContact: Contact? = null
    private var modification: CONTACT_MODIFICATION? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_contact)

        setSupportActionBar(toolbarEditActivity)

        removeButton?.setOnClickListener(removeButtonListener)

        actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)


        receiveContact()
        receivedContact?.let { setFields(it) }
    }

    fun newIntent(context: Context, contact: Contact): Intent {
        val intent = Intent(context, EditContactActivity::class.java)
        intent.putExtra(KEY_CONTACT_FOR_EDITING, contact)
        return intent
    }

    private fun receiveContact() {
        val intent = intent
        receivedContact = intent.extras?.getSerializable(KEY_CONTACT_FOR_EDITING) as Contact
    }

    private fun setFields(contact: Contact) {
        personNameField.text = contact.personName as Editable
        personContactField.text = contact.contactDetails as Editable
    }

    private fun modifyContact(contact: Contact): Contact {
        contact.personName = personNameField?.text.toString()
        contact.contactDetails = personContactField?.text.toString()
        return contact
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            modification = EDIT
            modifyContact(receivedContact!!)
            sendChangesBack()
        }
        return super.onOptionsItemSelected(item)
    }

    private val removeButtonListener = View.OnClickListener {
        modification = REMOVE
        modifyContact(receivedContact!!)
        sendChangesBack()
    }

    private fun sendChangesBack() {
        val resultIntent = Intent()
        resultIntent.putExtra(KEY_CHANGED_CONTACT, receivedContact)
        resultIntent.putExtra(REMOVE_OR_EDIT_CONTACT, modification)
        setResult(RESULT_OK, resultIntent)
        finish()
    }
}