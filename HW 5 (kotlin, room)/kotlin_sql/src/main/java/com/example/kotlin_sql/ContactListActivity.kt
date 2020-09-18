package com.example.kotlin_sql

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_sql.CONTACT_TYPE.EMAIL
import com.example.kotlin_sql.CONTACT_TYPE.PHONE
import com.example.kotlin_sql.database.AppDatabase
import com.example.kotlin_sql.database.Contact
import com.example.kotlin_sql.database.ContactDao
import kotlinx.android.synthetic.main.contact_list.*
import kotlinx.android.synthetic.main.item_contact.*

private const val CONTACT_FOR_EDITING: String = "CONTACT_FOR_EDITING"

class ContactListActivity : AppCompatActivity() {

    private var appDatabase: AppDatabase? = null
    private var contactDao: ContactDao? = null

    private var contactsList: List<Contact>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contact_list)

        setSupportActionBar(toolbarMain)

        appDatabase = AppDatabase.getAppDatabaseInstance(this)
        contactDao = appDatabase?.getContactDao()
        contactsList = contactDao?.getAll()

        recycleView.adapter = ContactsAdapter(contactsList, object : OnContactClickListener {
            override fun onContactClick(contact: Contact) {
                startEditContactActivity(contact)
            }
        })

        recycleView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        checkEmpty(contactsList)

        floatingButton.setOnClickListener { startAddContactActivity() }
    }

    private fun startAddContactActivity() {
        val intent = Intent(this, AddContactActivity::class.java)
        startActivity(intent)
    }

    private fun startEditContactActivity(contact: Contact) {
        val intent = Intent(this, EditContactActivity::class.java)
        intent.putExtra(CONTACT_FOR_EDITING, contact.id)
        startActivity(intent)
    }

    private fun checkEmpty(items: List<Contact>?) {
        if (items!!.isEmpty()) {
            recycleView.visibility = View.GONE
            noContacts.visibility = View.VISIBLE
        } else {
            recycleView?.visibility = View.VISIBLE
            noContacts?.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        contactsList = contactDao?.getAll()
        val adapter: ContactsAdapter = recycleView.adapter as ContactsAdapter
        adapter.notifyDataSetChanged()
        checkEmpty(adapter.items)
    }

    // ADAPTER
    inner class ContactsAdapter(contactsList: List<Contact>?, param: OnContactClickListener)
        : RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {

        internal var items = ArrayList<Contact>()
        private val contactListener: OnContactClickListener? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
            val view = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.item_contact, parent, false)
            return ContactsViewHolder(view)
        }

        override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
            if (contactListener != null) {
                holder.bind(items[position], contactListener)
            }
        }

        override fun getItemCount(): Int {
            return items.size
        }

        // VIEWHOLDER
        inner class ContactsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(contact: Contact, contactListener: OnContactClickListener) {
                itemPersonName.text = contact.personName
                itemPersonContact.text = contact.contactDetails
                when (contact.contactType) {
                    PHONE -> itemIcon.setImageResource(R.drawable.contact_phone)
                    EMAIL -> itemIcon.setImageResource(R.drawable.contact_email)
                }
                itemView.setOnClickListener { contactListener.onContactClick(contact) }
            }
        }
    }
}
