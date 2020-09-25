package com.example.multithreading.database;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ContactDao_Impl implements ContactDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Contact> __insertionAdapterOfContact;

  private final EnumTypeConverter __enumTypeConverter = new EnumTypeConverter();

  private final EntityDeletionOrUpdateAdapter<Contact> __deletionAdapterOfContact;

  private final EntityDeletionOrUpdateAdapter<Contact> __updateAdapterOfContact;

  public ContactDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfContact = new EntityInsertionAdapter<Contact>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `CONTACT_TABLE` (`personName`,`contactType`,`contactDetails`,`id`) VALUES (?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Contact value) {
        if (value.getPersonName() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getPersonName());
        }
        final String _tmp;
        _tmp = __enumTypeConverter.enumToString(value.getContactType());
        if (_tmp == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, _tmp);
        }
        if (value.getContactDetails() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getContactDetails());
        }
        if (value.getId() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getId());
        }
      }
    };
    this.__deletionAdapterOfContact = new EntityDeletionOrUpdateAdapter<Contact>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `CONTACT_TABLE` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Contact value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
      }
    };
    this.__updateAdapterOfContact = new EntityDeletionOrUpdateAdapter<Contact>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `CONTACT_TABLE` SET `personName` = ?,`contactType` = ?,`contactDetails` = ?,`id` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Contact value) {
        if (value.getPersonName() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getPersonName());
        }
        final String _tmp;
        _tmp = __enumTypeConverter.enumToString(value.getContactType());
        if (_tmp == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, _tmp);
        }
        if (value.getContactDetails() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getContactDetails());
        }
        if (value.getId() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getId());
        }
        if (value.getId() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getId());
        }
      }
    };
  }

  @Override
  public void insert(final Contact contact) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfContact.insert(contact);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Contact contact) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfContact.handle(contact);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Contact contact) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfContact.handle(contact);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Contact> getAll() {
    final String _sql = "SELECT * FROM CONTACT_TABLE";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfPersonName = CursorUtil.getColumnIndexOrThrow(_cursor, "personName");
      final int _cursorIndexOfContactType = CursorUtil.getColumnIndexOrThrow(_cursor, "contactType");
      final int _cursorIndexOfContactDetails = CursorUtil.getColumnIndexOrThrow(_cursor, "contactDetails");
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final List<Contact> _result = new ArrayList<Contact>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Contact _item;
        final String _tmpPersonName;
        _tmpPersonName = _cursor.getString(_cursorIndexOfPersonName);
        final CONTACT_TYPE _tmpContactType;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfContactType);
        _tmpContactType = __enumTypeConverter.stringToEnum(_tmp);
        final String _tmpContactDetails;
        _tmpContactDetails = _cursor.getString(_cursorIndexOfContactDetails);
        _item = new Contact(_tmpPersonName,_tmpContactType,_tmpContactDetails);
        final String _tmpId;
        _tmpId = _cursor.getString(_cursorIndexOfId);
        _item.setId(_tmpId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Contact getById(final String id) {
    final String _sql = "SELECT * FROM CONTACT_TABLE WHERE id LIKE ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, id);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfPersonName = CursorUtil.getColumnIndexOrThrow(_cursor, "personName");
      final int _cursorIndexOfContactType = CursorUtil.getColumnIndexOrThrow(_cursor, "contactType");
      final int _cursorIndexOfContactDetails = CursorUtil.getColumnIndexOrThrow(_cursor, "contactDetails");
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final Contact _result;
      if(_cursor.moveToFirst()) {
        final String _tmpPersonName;
        _tmpPersonName = _cursor.getString(_cursorIndexOfPersonName);
        final CONTACT_TYPE _tmpContactType;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfContactType);
        _tmpContactType = __enumTypeConverter.stringToEnum(_tmp);
        final String _tmpContactDetails;
        _tmpContactDetails = _cursor.getString(_cursorIndexOfContactDetails);
        _result = new Contact(_tmpPersonName,_tmpContactType,_tmpContactDetails);
        final String _tmpId;
        _tmpId = _cursor.getString(_cursorIndexOfId);
        _result.setId(_tmpId);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
