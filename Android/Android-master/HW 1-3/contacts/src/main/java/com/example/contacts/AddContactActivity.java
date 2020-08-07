package com.example.contacts;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

public class AddContactActivity extends AppCompatActivity {

    private RadioButton radioPhone;
    private RadioButton radioEmail;
    private EditText nameField;
    private EditText contactField;
    private ImageView save;
    private Toolbar toolbar;
    private ActionBar actionBar;
    private Contact contact;
    public static final String KEY_NEW_CONTACT = "KEY_NEW_CONTACT";
    public static final int REQUEST_CODE_ADD_CONTACT = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact);

        radioPhone = findViewById(R.id.radio_phoneNumber);
        radioEmail = findViewById(R.id.radio_email);
        nameField = findViewById(R.id.personName_field);
        contactField = findViewById(R.id.personContact_field);
        save = findViewById(R.id.saveButton);
        toolbar = findViewById(R.id.toolbarAddActivity);
        setSupportActionBar(toolbar);

        radioPhone.setOnClickListener(radioButtonListener);
        radioEmail.setOnClickListener(radioButtonListener);
        save.setOnClickListener(saveButtonListener);

        actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, AddContactActivity.class);
        return intent;
    }

    View.OnClickListener radioButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.radio_phoneNumber:
                    contactField.setHint("Phone number");
                    contactField.setInputType(InputType.TYPE_CLASS_PHONE);
                    break;
                case R.id.radio_email:
                    contactField.setHint("Email");
                    contactField.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                    break;
                default:
                    break;
            }
        }
    };

    View.OnClickListener saveButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            contact = createContact(nameField.getText().toString(), contactField.getText().toString());
            Intent resultIntent = new Intent();
            resultIntent.putExtra(KEY_NEW_CONTACT, contact);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }
    };

    private Contact createContact(String nameField, String contactField){
        if(radioPhone.isChecked()){
            contact = new Contact.Builder()
                    .setPersonName(nameField)
                    .setPhone(contactField)
                    .build();
        } else if(radioEmail.isChecked()){
            contact = new Contact.Builder()
                    .setPersonName(nameField)
                    .setEmail(contactField)
                    .build();
        }return contact;
    }
}