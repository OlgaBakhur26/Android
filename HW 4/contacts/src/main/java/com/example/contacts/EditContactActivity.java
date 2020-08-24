package com.example.contacts;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class EditContactActivity extends AppCompatActivity {

    private EditText nameField;
    private EditText contactField;
    private Button removeButton;
    private Toolbar toolbar;
    private ActionBar actionBar;

    private Contact receivedContact;
    private boolean removeContact = false;

    public static final String KEY_CHANGED_CONTACT = "Changed Contact";
    public static final String REMOVE_CONTACT = "Remove Contact";
    public static final String KEY_CONTACT_FOR_EDITING = "CONTACT FOR EDITING";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_contact);

        nameField = findViewById(R.id.personName_field);
        contactField = findViewById(R.id.personContact_field);
        removeButton = findViewById(R.id.button_remove);
        toolbar = findViewById(R.id.toolbarEditActivity);
        setSupportActionBar(toolbar);

        removeButton.setOnClickListener(removeButtonListener);

        actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        receiveContact();
        setFields(receivedContact);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            sendChangesBack();
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent newIntent(Context context, Contact contact){
        Intent intent = new Intent(context, EditContactActivity.class);
        intent.putExtra(KEY_CONTACT_FOR_EDITING, contact);
        return intent;
    }

    View.OnClickListener removeButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            removeContact = true;
            sendChangesBack();
        }
    };

    private void receiveContact(){
        Intent intent = getIntent();
        receivedContact = (Contact) intent.getExtras().getSerializable(KEY_CONTACT_FOR_EDITING);
    }

    private void setFields(Contact contact){
        if(contact.getPersonName() != null){
            nameField.setText(contact.getPersonName());
        }

        if(contact.getPhone() != null){
            contactField.setText(contact.getPhone());
        }

        if(contact.getEmail() != null){
            contactField.setText(contact.getEmail());
        }
    }

    private void sendChangesBack(){
        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_CHANGED_CONTACT, receivedContact);
        resultIntent.putExtra(REMOVE_CONTACT, removeContact);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}