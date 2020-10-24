package com.example.contentresolver;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContentResolverContactsActivity extends AppCompatActivity {

    private static final String URI_PATH = "content://com.example.contentprovider.contentprovider/data/contactList";
    private List<Contact> contactList;
    private RecyclerView contactsListRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactsListRecycleView = findViewById(R.id.recycleView);
        contactList = new ArrayList<>();

        contactsListRecycleView.setAdapter(new ContactsAdapter(contactList));

        contactsListRecycleView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        Cursor cursor = getContentResolver().query(Uri.parse(URI_PATH), null,
                null, null);

        if (cursor != null) {
            int personNameInd = cursor.getColumnIndex("personName");
            int contactTypeInd = cursor.getColumnIndex("contactType");
            int contactDetailsInd = cursor.getColumnIndex("contactDetails");
            while (cursor.moveToNext()) {
                Contact contact = new Contact(
                        cursor.getString(personNameInd),
                        CONTACT_TYPE.valueOf(cursor.getString(contactTypeInd)),
                        cursor.getString(contactDetailsInd)
                );

                contactList.add(contact);
            }
            cursor.close();
        }
    }

    // ADAPTER
    private static class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {

        private final List<Contact> items;

        public ContactsAdapter(List<Contact> items) {
            this.items = items;
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
            holder.bind(items.get(position));
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

                itemIcon = itemView.findViewById(R.id.itemIcon);
                itemPersonName = itemView.findViewById(R.id.itemPersonName);
                itemPersonContact = itemView.findViewById(R.id.itemPersonContact);
            }

            public void bind(final Contact contact) {

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
            }
        }
    }
}
