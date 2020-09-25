package com.example.multithreading;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multithreading.Threads.DatabaseMethods;
import com.example.multithreading.Threads.Executor_CompletableFuture;
import com.example.multithreading.Threads.Executor_Handler;
import com.example.multithreading.Threads.MultithreadingRegimeActivity;
import com.example.multithreading.Threads.MultithreadingRegimeManager;
import com.example.multithreading.Threads.RXJava;
import com.example.multithreading.database.AppDatabase;
import com.example.multithreading.database.Contact;
import com.example.multithreading.database.ContactDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ContactListActivity extends AppCompatActivity {

    public static final String CONTACT_FOR_EDITING = "CONTACT_FOR_EDITING";
    private RecyclerView contactsListRecycleView;
    private ContactsAdapter adapter;
    private TextView noContacts;
    private List<Contact> contactsList = new ArrayList<>();

    private ContactDao contactDao;
    private DatabaseMethods databaseMethods;
    private MultithreadingRegimeManager regimeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

        AppDatabase appDatabase = AppDatabase.getAppDatabaseInstance(this);
        contactDao = appDatabase.getContactDao();
        regimeManager = MultithreadingRegimeManager.getRegimeManager(ContactListActivity.this);
        getMultithreadingRegime();

        contactsListRecycleView = findViewById(R.id.recycleView);
        noContacts = findViewById(R.id.noContacts);
        FloatingActionButton floatingButton = findViewById(R.id.floatingButton_addContact);
        Toolbar toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        SearchView search = findViewById(R.id.search);
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAddContactActivity();
            }
        });

        contactsListRecycleView.setAdapter(new ContactsAdapter(contactsList, new ContactsAdapter.OnContactClickListener() {
            @Override
            public void onContactClick(Contact contact) {
                startEditContactActivity(contact);
            }
        }));
        contactsListRecycleView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        adapter = (ContactsAdapter) contactsListRecycleView.getAdapter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadContactList();
    }

    private void startAddContactActivity() {
        Intent intent = new Intent(ContactListActivity.this, AddContactActivity.class);
        startActivity(intent);
    }

    private void startEditContactActivity(Contact contact) {
        Intent intent = new Intent(ContactListActivity.this, EditContactActivity.class);
        intent.putExtra(CONTACT_FOR_EDITING, contact.getId());
        startActivity(intent);
    }

    private void startMultithreadingRegimeActivity() {
        Intent intent = new Intent(ContactListActivity.this, MultithreadingRegimeActivity.class);
        startActivity(intent);
    }

    private void checkEmpty(List<Contact> list) {
        if (list.isEmpty()) {
            contactsListRecycleView.setVisibility(View.GONE);
            noContacts.setVisibility(View.VISIBLE);
        } else {
            contactsListRecycleView.setVisibility(View.VISIBLE);
            noContacts.setVisibility(View.GONE);
        }
    }

    private void loadContactList() {
        if (adapter != null) {
            databaseMethods.getAll(supplierGetAll, consumerGetAll);
            adapter.notifyDataSetChanged();
        }
    }

    private Supplier<List<Contact>> supplierGetAll = new Supplier<List<Contact>>() {
        @Override
        public List<Contact> get() {
            return contactDao.getAll();
        }
    };

    private Consumer<List<Contact>> consumerGetAll = new Consumer<List<Contact>>() {
        @Override
        public void accept(List<Contact> list) {
            if (adapter != null) {
                adapter.items = list;
                checkEmpty(adapter.items);
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // надо поправить стиль меню. Поверх searchView
        getMenuInflater().inflate(R.menu.contact_list_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId){  // я сделела здесь switch, а не if для того, чтобы легче было добавлять другие функции в меню, если они появятся.
            case R.id.multithreadingRegime:
                startMultithreadingRegimeActivity();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getMultithreadingRegime(){
        int regimeId = regimeManager.loadMultithreadingRegime();
        switch (regimeId){
            case R.id.executorHandler:
                databaseMethods = new Executor_Handler();
                break;
            case R.id.executorFuture:
                databaseMethods = new Executor_CompletableFuture();
                break;
            case R.id.rxJava:
                databaseMethods = new RXJava();
                break;
        }
    }

    // ADAPTER
    private static class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {
        public interface OnContactClickListener {
            void onContactClick(Contact contact);
        }

        private List<Contact> items;
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
        private static class ContactsViewHolder extends RecyclerView.ViewHolder {
            private ImageView itemIcon;
            private TextView itemPersonName;
            private TextView itemPersonContact;

            public ContactsViewHolder(@NonNull View itemView) {
                super(itemView);

                itemIcon = itemView.findViewById(R.id.item_icon);
                itemPersonName = itemView.findViewById(R.id.item_personName);
                itemPersonContact = itemView.findViewById(R.id.item_personContact);
            }

            public void bind(final Contact contact, final OnContactClickListener contactListener) {

                itemPersonName.setText(contact.getPersonName());
                itemPersonContact.setText(contact.getContactDetails());

                switch (contact.getContactType()) {
                    case PHONE:
                        itemIcon.setImageResource(R.drawable.contact_phone);
                        break;
                    case EMAIL:
                        itemIcon.setImageResource(R.drawable.contact_email);
                        break;
                    default:
                        break;
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






















