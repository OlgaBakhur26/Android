package com.example.contentprovider.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.contentprovider.database.AppDatabase;
import com.example.contentprovider.database.Contact;
import com.example.contentprovider.database.ContactDao;

public class ContentProviderContacts extends ContentProvider {

    private static final UriMatcher uriMatcher;
    private static final String AUTHORITY = "com.example.contentprovider.contentprovider";
    private static final String URI_PATH_CONTACT_TABLE = "data/contactList";
    private static final String URI_PATH_CONTACT_ID = "data/contactList/*";
    private static final int URI_CONTACT_TABLE_CODE = 1;
    private static final int URI_CONTACT_ID_CODE = 2;
    private static final String MIME_TYPE_TABLE = "vnd.android.cursor.dir/" + AUTHORITY + "." + URI_PATH_CONTACT_TABLE;
    private static final String MIME_TYPE_ID = "vnd.android.cursor.item/" + AUTHORITY + "." + URI_PATH_CONTACT_ID;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, URI_PATH_CONTACT_TABLE, URI_CONTACT_TABLE_CODE);
        uriMatcher.addURI(AUTHORITY, URI_PATH_CONTACT_ID, URI_CONTACT_ID_CODE);
    }

    private ContactDao contactDao;

    @Override
    public boolean onCreate() {
        if(getContext() != null){
            contactDao = AppDatabase.getAppDatabaseInstance(getContext()).getContactDao();
            return true;
        }
        throw new IllegalArgumentException("Failed to create a Content Provider");
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        if(getContext() != null){
            switch (uriMatcher.match(uri)){
                case URI_CONTACT_TABLE_CODE:
                    return MIME_TYPE_TABLE;
                case URI_CONTACT_ID_CODE:
                    return MIME_TYPE_ID;
                default:
                    throw new IllegalArgumentException("No matches found with " + uri);
            }
        }
        throw new RuntimeException("Failed to invoke the method \"getType\"");
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        if(getContext() != null){
            Cursor cursor = null;
            switch (uriMatcher.match(uri)){
                case URI_CONTACT_TABLE_CODE:
                    cursor = contactDao.getAllCursor();
                    cursor.setNotificationUri(getContext().getContentResolver(), uri);
                    break;
                case URI_CONTACT_ID_CODE:
                    cursor = contactDao.getByIdCursor(String.valueOf(ContentUris.parseId(uri)));
                    cursor.setNotificationUri(getContext().getContentResolver(), uri);
                    break;
            }
            return cursor;
        }
        throw new IllegalArgumentException("Failed to query row for uri " + uri);
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        if(getContext() != null){
            if(uriMatcher.match(uri) == URI_CONTACT_TABLE_CODE){
                int id = contactDao.insert(Contact.fromContentValues(values));
                notifyDataChanged(uri);
                return ContentUris.withAppendedId(uri, id);
            }
            return null;
        }
        throw new IllegalArgumentException("Failed to insert with the uri " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        if(getContext() != null){
            switch (uriMatcher.match(uri)){
                case URI_CONTACT_TABLE_CODE:
                    throw new IllegalArgumentException("Failed to delete with the uri " + uri);
                case URI_CONTACT_ID_CODE:
                    int count = contactDao.delete(ContentUris.parseId(uri));
                    notifyDataChanged(uri);
                    return count;
            }
        }
        throw new IllegalArgumentException("Failed to delete with the uri " + uri);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        if(getContext() != null){
            switch (uriMatcher.match(uri)){
                case URI_CONTACT_TABLE_CODE:
                    throw new IllegalArgumentException("Failed to update with the uri " + uri);
                case URI_CONTACT_ID_CODE:
                    Contact contact = Contact.fromContentValues(values);
                    int count = contactDao.update(contact);
                    notifyDataChanged(uri);
                    return count;
            }
        }
        throw new IllegalArgumentException("Failed to delete with the uri " + uri);

    }

    private void notifyDataChanged(Uri uri){
        if(getContext() != null){
            getContext().getContentResolver().notifyChange(uri, null);
        }
    }
}
