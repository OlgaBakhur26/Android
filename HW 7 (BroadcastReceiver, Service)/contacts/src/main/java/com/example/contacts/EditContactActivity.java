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
    enum CONTACT_MODIFICATION{
        REMOVE,
        EDIT
    }

    private EditText nameField;
    private EditText contactField;
    private Button removeButton;
    private Toolbar toolbar;
    private ActionBar actionBar;

    private Contact receivedContact;
    private CONTACT_MODIFICATION modification;

    public static final String KEY_CHANGED_CONTACT = "KEY CHANGED CONTACT";
    public static final String REMOVE_OR_EDIT_CONTACT = "REMOVE OR EDIT CONTACT";
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

    public static Intent newIntent(Context context, Contact contact){
        Intent intent = new Intent(context, EditContactActivity.class);
        intent.putExtra(KEY_CONTACT_FOR_EDITING, contact);
        return intent;
    }

    private void receiveContact(){
        Intent intent = getIntent();
        receivedContact = (Contact) intent.getExtras().getSerializable(KEY_CONTACT_FOR_EDITING);
    }

    private void setFields(Contact contact){
        nameField.setText(contact.getPersonName());
        contactField.setText(contact.getContactDetails());
    }

    private Contact modifyContact(Contact contact){
        contact.setPersonName(nameField.getText().toString());
        contact.setContactDetails(contactField.getText().toString());
        return contact;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            modification = CONTACT_MODIFICATION.EDIT;
            modifyContact(receivedContact);
            sendChangesBack();
        }
        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener removeButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            modification = CONTACT_MODIFICATION.REMOVE;
            modifyContact(receivedContact);
            sendChangesBack();
        }
    };

    private void sendChangesBack(){
        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_CHANGED_CONTACT, receivedContact);
        resultIntent.putExtra(REMOVE_OR_EDIT_CONTACT, modification);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
}