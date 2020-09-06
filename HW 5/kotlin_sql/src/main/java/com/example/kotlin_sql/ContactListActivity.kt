package com.example.kotlin_sql

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.contact_list.*

private const val REQUEST_CODE_ADD_CONTACT: Int = 111
private const val REQUEST_CODE_EDIT_CONTACT: Int = 222

// Это все очень ужасно. Извиняюсь :(
// Базу данных доделаю позже
class ContactListActivity : AppCompatActivity() {

    private var contact: Contact? = null
    private var newContactID: String? = null
    private var oldContactIndex: Int? = null
    private var modification: CONTACT_MODIFICATION? = null
    private val contactsList = arrayListOf<Contact>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contact_list)

        setSupportActionBar(toolbarMain)

        recycleView.adapter = ContactsAdapter(contactsList, object : ContactsAdapter.OnContactClickListener {
            override fun onContactClick(contact: Contact) {
                oldContactIndex = contactsList.indexOf(contact)
                startEditContactActivity(contact)
            }
        })

        recycleView?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        checkEmpty(contactsList)

        floatingButton.setOnClickListener { startAddContactActivity() }
    }

    private fun startAddContactActivity() {
        startActivityForResult(AddContactActivity.newIntent(this@ContactListActivity), REQUEST_CODE_ADD_CONTACT)
    }

    private fun startEditContactActivity(contact: Contact) {
        startActivityForResult(EditContactActivity.newIntent(this@ContactListActivity, contact), REQUEST_CODE_EDIT_CONTACT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (requestCode == REQUEST_CODE_ADD_CONTACT && resultCode == RESULT_OK && intent != null) {
            contact = intent.extras?.getSerializable(KEY_NEW_CONTACT) as Contact
            addContact(contact!!)
        }
        if (requestCode == REQUEST_CODE_EDIT_CONTACT && resultCode == RESULT_OK && intent != null) {
            contact = intent.extras?.getSerializable(KEY_CHANGED_CONTACT) as Contact
            modification = intent.extras?.getSerializable(REMOVE_OR_EDIT_CONTACT) as CONTACT_MODIFICATION

            newContactID = contact!!.id
            val adapter: ContactsAdapter? = recycleView.adapter as ContactsAdapter

            var iterator: Iterator<Contact> = adapter!!.items!!.iterator()
            while (iterator.hasNext()) {
                val item: Contact = iterator.next()
                val itemId: String = item.id
                if (newContactID == itemId) {
                    if (modification!!.equals(CONTACT_MODIFICATION.EDIT)) {
                        adapter.items?.set(oldContactIndex!!, item)
                        adapter.notifyItemChanged(oldContactIndex!!)
                        checkEmpty(adapter.items!!)
                    } else if (modification!!.equals(CONTACT_MODIFICATION.REMOVE)) {
                        adapter.items?.remove(contact!!)
                        adapter.notifyItemChanged(oldContactIndex!!)
                        checkEmpty(adapter.items!!)
                    }
                }
            }
        }
    }

    private fun addContact(contact: Contact) {
        var adapter: ContactsAdapter = recycleView.adapter as ContactsAdapter
        adapter.items?.add(contact)
        adapter.items?.indexOf(contact)?.let { adapter.notifyItemChanged(it) }
        adapter.items?.let { checkEmpty(it) }
    }

    private fun checkEmpty(items: ArrayList<Contact>) {
        if (items.isEmpty()) {
            recycleView?.visibility = View.GONE
            noContacts?.visibility = View.VISIBLE
        } else {
            recycleView?.visibility = View.VISIBLE
            noContacts?.visibility = View.GONE
        }
    }


    // ADAPTER
    class ContactsAdapter(contactsList: ArrayList<Contact>, param: OnContactClickListener) : RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {
        interface OnContactClickListener {
            fun onContactClick(contact: Contact)
        }

        var items: ArrayList<Contact>? = null
        private val contactListener: OnContactClickListener? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
            val view = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.item_contact, parent, false)
            return ContactsViewHolder(view)
        }


        override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
            if (holder != null && contactListener != null) {
                holder.bind(items!![position], contactListener)
            }
        }

        override fun getItemCount(): Int {
            return items?.size ?: 0
        }

        // VIEWHOLDER
        class ContactsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private var contactType: CONTACT_TYPE? = null

            fun bind(contact: Contact, contactListener: OnContactClickListener) {
                itemPersonName.text = contact.personName
                itemPersonContact.text = contact.contactDetails
                when (contact.contactType) {
                    CONTACT_TYPE.PHONE -> itemIcon.imageResource(R.drawable.contact_phone)
                    CONTACT_TYPE.EMAIL -> itemIcon.imageResource(R.drawable.contact_email)
                }
                itemView.setOnClickListener { contactListener.onContactClick(contact) }
            }
        }
    }
}
