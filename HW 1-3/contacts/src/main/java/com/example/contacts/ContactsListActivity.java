package com.example.contacts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

import static com.example.contacts.AddContactActivity.REQUEST_CODE_ADD_CONTACT;
import static com.example.contacts.Contact.contactsList;
import static com.example.contacts.EditContactActivity.KEY_CHANGED_CONTACT;
import static com.example.contacts.EditContactActivity.REMOVE_CONTACT;
import static com.example.contacts.EditContactActivity.REQUEST_CODE_EDIT_CONTACT;


public class ContactsListActivity extends AppCompatActivity{

    private RecyclerView contactsListRecycleView;
    private TextView noContacts;
    private FloatingActionButton floatingButton;
    private Toolbar toolbar;
    private SearchView search;

    private Contact contact;
    private volatile int index;
    private boolean removeContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_list);

        contactsListRecycleView = findViewById(R.id.recycleView);
        noContacts = findViewById(R.id.noContacts);
        floatingButton = findViewById(R.id.floatingButton_addContact);
        toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        search = findViewById(R.id.search);

        contactsListRecycleView.setAdapter(new ContactsAdapter(contactsList, new ContactsAdapter.OnContactClickListener() {
            @Override
            public void onContactClick(Contact contact) {
                index = contactsList.indexOf(contact);
                startEditContactActivity(contact);
            }
        }));

        contactsListRecycleView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        checkEmpty(contactsList);

        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAddContactActivity();
            }
        });
    }

    private void startAddContactActivity(){
        startActivityForResult(AddContactActivity.newIntent(ContactsListActivity.this), REQUEST_CODE_ADD_CONTACT);
    }

    private void startEditContactActivity(Contact contact){
        startActivityForResult(EditContactActivity.newIntent(ContactsListActivity.this, contact), REQUEST_CODE_EDIT_CONTACT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if(requestCode == REQUEST_CODE_ADD_CONTACT && resultCode == Activity.RESULT_OK && intent != null){
            contact = (Contact) intent.getExtras().getSerializable(AddContactActivity.KEY_NEW_CONTACT);
            addContact(contact);
        }

        if(requestCode == REQUEST_CODE_EDIT_CONTACT && resultCode == Activity.RESULT_OK && intent != null){
            contact = (Contact) intent.getExtras().getSerializable(KEY_CHANGED_CONTACT);
            removeContact = intent.getBooleanExtra(REMOVE_CONTACT, false);
            if(removeContact == false){
                changeContact(index, contact); // изменения не вносятся ни по одному индексу
            }else if(removeContact == true){
                removeContact(index); // приходит только последний элемент в листе. Только последний удаляется, в остальных случаях IndexOutOfBoundsException
            }
        }
    }

    private void addContact(Contact contact){
        ContactsAdapter adapter = (ContactsAdapter) contactsListRecycleView.getAdapter();
        if (adapter != null){
            adapter.items.add(contact);
            adapter.notifyItemChanged(adapter.items.indexOf(contact));
            checkEmpty(adapter.items);
        }
    }

    private void changeContact(int index, Contact contact){
        ContactsAdapter adapter = (ContactsAdapter) contactsListRecycleView.getAdapter();
        if (adapter != null){
            adapter.items.set(index, contact);
            adapter.notifyItemChanged(adapter.items.indexOf(contact));
            checkEmpty(adapter.items);
        }
    }

    private void removeContact(int index){
        ContactsAdapter adapter = (ContactsAdapter) contactsListRecycleView.getAdapter();
        if (adapter != null){
            adapter.items.remove(index);
            adapter.notifyItemChanged(adapter.items.indexOf(contact));
            checkEmpty(adapter.items);
        }
    }

    private void checkEmpty(List<Contact> items){
        if (items.isEmpty()) {
            contactsListRecycleView.setVisibility(View.GONE);
            noContacts.setVisibility(View.VISIBLE);
        }
        else {
            contactsListRecycleView.setVisibility(View.VISIBLE);
            noContacts.setVisibility(View.GONE);
        }
    }


    // ADAPTER
    private static class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>{
        public interface OnContactClickListener {
            void onContactClick(Contact contact);
        }

        private final List<Contact> items;
        private final OnContactClickListener contactListener;

        public ContactsAdapter(List<Contact> items, OnContactClickListener contactListener) {
            this.items = items;
            this.contactListener = contactListener;
        }

        @NonNull
        @Override
        public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.item_contact, parent, false);
            return new ContactsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ContactsViewHolder holder, int position) {
            holder.bind(items.get(position), contactListener);
        }

        @Override
        public int getItemCount() {
            return items != null ? items.size() : 0;
        }

        // VIEWHOLDER
        private static class ContactsViewHolder extends RecyclerView.ViewHolder{
            private ImageView itemIcon;
            private TextView itemPersonName;
            private TextView itemPersonContact;

            public ContactsViewHolder(@NonNull View itemView) {
                super(itemView);

                itemIcon = itemView.findViewById(R.id.item_icon);
                itemPersonName = itemView.findViewById(R.id.item_personName);
                itemPersonContact = itemView.findViewById(R.id.item_personContact);
            }

            public void bind(final Contact contact, final OnContactClickListener contactListener){

                if(contact.getPersonName() != null){
                    itemPersonName.setText(contact.getPersonName());
                }

                if(contact.getPhone() != null){
                    itemPersonContact.setText(contact.getPhone());
                    itemIcon.setImageResource(R.drawable.contact_phone);
                }

                if(contact.getEmail() != null){
                    itemPersonContact.setText(contact.getEmail());
                    itemIcon.setImageResource(R.drawable.contact_email);
                }

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        contactListener.onContactClick(contact);
                    }
                });
            }
        }
    }
}
