package com.example.multithreading;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.multithreading.Threads.DatabaseMethods;
import com.example.multithreading.Threads.Executor_CompletableFuture;
import com.example.multithreading.database.AppDatabase;
import com.example.multithreading.database.Contact;
import com.example.multithreading.database.ContactDao;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.example.multithreading.ContactListActivity.CONTACT_FOR_EDITING;


public class EditContactActivity extends AppCompatActivity {

    private EditText nameField;
    private EditText contactField;

    private String contactID;
    private ContactDao contactDao;
    private Contact contactToEdit;

    DatabaseMethods databaseMethods = new Executor_CompletableFuture(); //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        Log.d("Executor", "onCreate: " + Thread.currentThread().getName());

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

        receiveContactId();
        setFields();
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseMethods.removeContact(runnableDelete);
                finish();
            }
        });
    }

    private void receiveContactId() {
        Intent intent = getIntent();
        contactID = intent.getStringExtra(CONTACT_FOR_EDITING);
    }

    private void setFields() {
        databaseMethods.getById(supplierGetById, consumerGetById);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            databaseMethods.editContact(runnableEdit);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private Supplier<Contact> supplierGetById = new Supplier<Contact>() {
        @Override
        public Contact get() {
            return contactDao.getById(contactID);
        }
    };

    private Consumer<Contact> consumerGetById = new Consumer<Contact>() {
        @Override
        public void accept(Contact contact) {
            contactToEdit = contact;

            nameField.setText(contactToEdit.getPersonName());
            contactField.setText(contactToEdit.getContactDetails());
        }
    };

    private Runnable runnableDelete = new Runnable() {
        @Override
        public void run() {
            Log.d("Executor", "runnableDelete: " + Thread.currentThread().getName());
            contactDao.delete(contactToEdit);
        }
    };

    private Runnable runnableEdit = new Runnable() {
        @Override
        public void run() {
            Log.d("Executor", "runnableEdit: " + Thread.currentThread().getName());
            contactToEdit.setPersonName(nameField.getText().toString());
            contactToEdit.setContactDetails(contactField.getText().toString());
            contactDao.update(contactToEdit);
        }
    };
}