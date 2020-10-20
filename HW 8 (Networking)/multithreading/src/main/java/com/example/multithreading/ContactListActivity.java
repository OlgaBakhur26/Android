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

import com.example.multithreading.Threads.DataRepository;
import com.example.multithreading.Threads.FutureRepository;
import com.example.multithreading.Threads.HandlerRepository;
import com.example.multithreading.Threads.MultithreadingRegimeActivity;
import com.example.multithreading.Threads.MultithreadingRegimeManager;
import com.example.multithreading.Threads.RXJavaRepository;
import com.example.multithreading.Threads.RepositoryListener;
import com.example.multithreading.database.AppDatabase;
import com.example.multithreading.database.Contact;
import com.example.multithreading.database.ContactDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ContactListActivity extends AppCompatActivity {

    public static final String CONTACT_FOR_EDITING = "CONTACT_FOR_EDITING";
    private RecyclerView contactsListRecycleView;
    private ContactsAdapter adapter;
    private TextView noContacts;
    private List<Contact> contactsList = new ArrayList<>();

    private ContactDao contactDao;
    private DataRepository dataRepository;
    private MultithreadingRegimeManager regimeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);

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

        AppDatabase appDatabase = AppDatabase.getAppDatabaseInstance(this);
        contactDao = appDatabase.getContactDao();
        regimeManager = MultithreadingRegimeManager.getRegimeManager(ContactListActivity.this);
        getMultithreadingRegime();

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
        dataRepository.getAll(new RepositoryListener<List<Contact>>() {
            @Override
            public void onDataReceived(List<Contact> contactList) {
                if (adapter != null) {
                    adapter.items = contactList;
                    adapter.notifyDataSetChanged();
                    checkEmpty(adapter.items);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.contact_list_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {  // я сделела здесь switch, а не if для того, чтобы легче было добавлять другие функции в меню, если они появятся.
            case R.id.multithreadingRegime:
                startMultithreadingRegimeActivity();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getMultithreadingRegime() {
        int regimeId = regimeManager.loadMultithreadingRegime();
        switch (regimeId) {
            case R.id.executorHandler:
                dataRepository = new HandlerRepository(contactDao, (ThreadPoolExecutor) Executors.newFixedThreadPool(2));
                break;
            case R.id.executorFuture:
                dataRepository = new FutureRepository(contactDao, (Executor) getMainExecutor(),
                        (ThreadPoolExecutor) Executors.newFixedThreadPool(2));
                break;
            case R.id.rxJava:
                dataRepository = new RXJavaRepository(contactDao);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataRepository.close();
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






















