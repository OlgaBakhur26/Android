package com.example.multithreading;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.multithreading.Threads.DataRepository;
import com.example.multithreading.Threads.FutureRepository;
import com.example.multithreading.Threads.HandlerRepository;
import com.example.multithreading.Threads.MultithreadingRegimeManager;
import com.example.multithreading.Threads.RXJavaRepository;
import com.example.multithreading.database.AppDatabase;
import com.example.multithreading.database.Contact;
import com.example.multithreading.database.ContactDao;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static android.text.InputType.TYPE_CLASS_PHONE;
import static android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
import static com.example.multithreading.database.CONTACT_TYPE.EMAIL;
import static com.example.multithreading.database.CONTACT_TYPE.PHONE;

public class AddContactActivity extends AppCompatActivity {

    private RadioButton radioPhone;
    private RadioButton radioEmail;
    private EditText nameField;
    private EditText contactField;

    private Contact contact;
    private ContactDao contactDao;
    private DataRepository dataRepository;
    private MultithreadingRegimeManager regimeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        radioPhone = findViewById(R.id.radioPhone);
        radioEmail = findViewById(R.id.radioEmail);
        nameField = findViewById(R.id.personName_field);
        contactField = findViewById(R.id.personContact_field);
        ImageView save = findViewById(R.id.saveButton);
        Toolbar toolbar = findViewById(R.id.toolbarAddActivity);
        setSupportActionBar(toolbar);

        radioPhone.setOnClickListener(radioButtonListener);
        radioEmail.setOnClickListener(radioButtonListener);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewContact();
                finish();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        AppDatabase appDatabase = AppDatabase.getAppDatabaseInstance(this);
        contactDao = appDatabase.getContactDao();
        regimeManager = MultithreadingRegimeManager.getRegimeManager(AddContactActivity.this);
        getMultithreadingRegime();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    View.OnClickListener radioButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.radioPhone:
                    adjustPhoneField();
                    break;
                case R.id.radioEmail:
                    adjustEmailField();
                    break;
                default:
                    break;
            }
        }
    };

    private void adjustPhoneField() {
        contactField.setHint("Phone number");
        contactField.setInputType(TYPE_CLASS_PHONE);
    }

    private void adjustEmailField() {
        contactField.setHint("Email");
        contactField.setInputType(TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
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

    private void addNewContact() {
        if (radioPhone.isChecked()) {
            contact = new Contact(nameField.getText().toString(), PHONE, contactField.getText().toString());
        } else if (radioEmail.isChecked()) {
            contact = new Contact(nameField.getText().toString(), EMAIL, contactField.getText().toString());
        }

        dataRepository.addContact(contact);
    }
}

