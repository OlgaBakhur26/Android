package com.example.multithreading;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.multithreading.Threads.DatabaseMethods;
import com.example.multithreading.Threads.Executor_CompletableFuture;
import com.example.multithreading.Threads.Executor_Handler;
import com.example.multithreading.Threads.MultithreadingRegimeManager;
import com.example.multithreading.Threads.RXJava;
import com.example.multithreading.database.AppDatabase;
import com.example.multithreading.database.Contact;
import com.example.multithreading.database.ContactDao;

import static android.text.InputType.TYPE_CLASS_PHONE;
import static android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
import static com.example.multithreading.database.CONTACT_TYPE.EMAIL;
import static com.example.multithreading.database.CONTACT_TYPE.PHONE;

public class AddContactActivity<runnable> extends AppCompatActivity {

    private RadioButton radioPhone;
    private RadioButton radioEmail;
    private EditText nameField;
    private EditText contactField;

    private ContactDao contactDao;
    private Contact contact;
    private DatabaseMethods databaseMethods;
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
                databaseMethods.addContact(runnableAdd);
                finish();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
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
            switch (view.getId()){
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

    private void adjustPhoneField(){
        contactField.setHint("Phone number");
        contactField.setInputType(TYPE_CLASS_PHONE);
    }

    private void adjustEmailField(){
        contactField.setHint("Email");
        contactField.setInputType(TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
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

    private Runnable runnableAdd = new Runnable() {
        @Override
        public void run() {
            Log.d("Executor", "runnableAdd: " + Thread.currentThread().getName());
            if(radioPhone.isChecked()){
                contact = new Contact(nameField.getText().toString(), PHONE, contactField.getText().toString());
                contactDao.insert(contact);
            }else if(radioEmail.isChecked()){
                contact = new Contact(nameField.getText().toString(), EMAIL, contactField.getText().toString());
            }
            contactDao.insert(contact);
            // тут может try/ catch на случай ошибки записи в базу?
        }
    };
}

