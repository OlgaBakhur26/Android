package com.example.contentprovider

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contentprovider.database.AppDatabase
import com.example.contentprovider.database.CONTACT_TYPE.EMAIL
import com.example.contentprovider.database.CONTACT_TYPE.PHONE
import com.example.contentprovider.database.Contact
import com.example.contentprovider.database.ContactDao
import kotlinx.android.synthetic.main.activity_contact_list.*
import kotlinx.android.synthetic.main.item_contact.view.*

internal const val CONTACT_FOR_EDITING: String = "CONTACT_FOR_EDITING"

class ContactListActivity : AppCompatActivity() {

    private lateinit var contactDao: ContactDao
    private lateinit var contactsList: List<Contact>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_list)

        setSupportActionBar(toolbarMain)

        val appDatabase = AppDatabase.getAppDatabaseInstance(this)
        contactDao = appDatabase.getContactDao()
        contactsList = contactDao.getAll()

        recycleView.adapter = ContactsAdapter(contactsList, object : OnContactClickListener {
            override fun onContactClick(contact: Contact) {
                startEditContactActivity(contact)
            }
        })
        recycleView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

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

    private fun checkEmpty(list: List<Contact>) {
        if (list.isEmpty()) {
            recycleView.visibility = View.GONE
            noContacts.visibility = View.VISIBLE
        } else {
            recycleView.visibility = View.VISIBLE
            noContacts.visibility = View.GONE
        }
    }

    private fun loadContactList() {
        val adapter = recycleView.adapter as? ContactsAdapter
        adapter?.items = contactDao.getAll()
        adapter?.notifyDataSetChanged()
        adapter?.items?.let { checkEmpty(it) }
    }

    override fun onStart() {
        super.onStart()
        loadContactList()
    }

    // ADAPTER
    class ContactsAdapter(
            internal var items: List<Contact>,
            private val contactListener: OnContactClickListener
    )
        : RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
            val view = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.item_contact, parent, false)
            return ContactsViewHolder(view)
        }

        override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
            holder.bind(items[position], contactListener)
        }

        override fun getItemCount(): Int {
            return items.size
        }

        // VIEWHOLDER
        class ContactsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            internal fun bind(contact: Contact, contactListener: OnContactClickListener) {
                itemView.itemPersonName.text = contact.personName
                itemView.itemPersonContact.text = contact.contactDetails
                when (contact.contactType) {
                    PHONE -> itemView.itemIcon.setImageResource(R.drawable.contact_phone)
                    EMAIL -> itemView.itemIcon.setImageResource(R.drawable.contact_email)
                }
                itemView.setOnClickListener { contactListener.onContactClick(contact) }
            }
        }
    }
}