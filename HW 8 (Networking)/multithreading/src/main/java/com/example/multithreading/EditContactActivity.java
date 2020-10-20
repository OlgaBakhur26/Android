package com.example.multithreading;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.multithreading.Threads.DataRepository;
import com.example.multithreading.Threads.FutureRepository;
import com.example.multithreading.Threads.HandlerRepository;
import com.example.multithreading.Threads.MultithreadingRegimeManager;
import com.example.multithreading.Threads.RXJavaRepository;
import com.example.multithreading.Threads.RepositoryListener;
import com.example.multithreading.database.AppDatabase;
import com.example.multithreading.database.Contact;
import com.example.multithreading.database.ContactDao;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static com.example.multithreading.ContactListActivity.CONTACT_FOR_EDITING;


public class EditContactActivity extends AppCompatActivity {

    private EditText nameField;
    private EditText contactField;

    private String contactID;
    private ContactDao contactDao;
    private Contact contactToEdit;

    private DataRepository dataRepository;
    private MultithreadingRegimeManager regimeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        nameField = findViewById(R.id.personName_field);
        contactField = findViewById(R.id.personContact_field);
        Button removeButton = findViewById(R.id.button_remove);
        Toolbar toolbar = findViewById(R.id.toolbarEditActivity);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        AppDatabase appDatabase = AppDatabase.getAppDatabaseInstance(this);
        contactDao = appDatabase.getContactDao();
        regimeManager = MultithreadingRegimeManager.getRegimeManager(EditContactActivity.this);
        getMultithreadingRegime();

        receiveContactId();
        setFields();
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataRepository.removeContact(contactToEdit);
                finish();
            }
        });
    }

    private void receiveContactId() {
        Intent intent = getIntent();
        contactID = intent.getStringExtra(CONTACT_FOR_EDITING);
    }

    private void setFields() {
        dataRepository.getById(contactID, new RepositoryListener<Contact>() {
            @Override
            public void onDataReceived(Contact contact) {
                contactToEdit = contact;
                nameField.setText(contactToEdit.getPersonName());
                contactField.setText(contactToEdit.getContactDetails());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            editCurrentContact(contactToEdit);
            finish();
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

    private void editCurrentContact(Contact contact) {
        contact.setPersonName(nameField.getText().toString());
        contact.setContactDetails(contactField.getText().toString());
        dataRepository.editContact(contact);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataRepository.close();
    }
}