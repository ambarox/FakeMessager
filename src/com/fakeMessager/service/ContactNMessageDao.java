package com.fakeMessager.service;

/**
 * Created by lasitha on 7/20/15.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.fakeMessager.helper.DatabaseHelper;
import com.fakeMessager.helper.FeedReaderContract;
import com.fakeMessager.model.ContactNMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The type Contact n message dao.
 */
public class ContactNMessageDao {
    private static SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    /**
     * The Numbers.
     */
    public static ArrayList<String> numbers;
    /**
     * The Numbers n contacts.
     */
    public static ArrayList<String> numbersNContacts;
    /**
     * The Number n message map.
     */
    public static HashMap<String,String> numberNMessageMap;

    /**
     * Instantiates a new Contact n message dao.
     *
     * @param context the context
     */
    public ContactNMessageDao(Context context) {
        dbHelper = new DatabaseHelper(context);
        open();
    }

    private void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    /**
     * Close void.
     */
    public void close() {
        dbHelper.close();
    }

    /**
     * Create contact n message.
     *
     * @param contactNMessage the contact n message
     * @return the contact n message
     */
    public ContactNMessage create(final ContactNMessage contactNMessage) {
        final ContentValues values = new ContentValues();
        values.put("phone_number", contactNMessage.phoneNumber);
        values.put("message", contactNMessage.message);

        final long id = database.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
        contactNMessage.id = id;
        return contactNMessage;
    }

    /**
     * Delete void.
     *
     * @param phoneNumber the phone number
     */
    public static void delete(final String phoneNumber) {
        database.delete(FeedReaderContract.FeedEntry.TABLE_NAME, "phone_number = '" + phoneNumber + "'", null);
    }

    /**
     * Gets all contacts.
     *
     * @return the all contacts
     */
    public List<ContactNMessage> getAllContacts() {
        final List<ContactNMessage> contactNMessages = new ArrayList<ContactNMessage>();
        final Cursor cursor = database.query(FeedReaderContract.FeedEntry.TABLE_NAME, new String[]{"_id", "phone_number", "message"}, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            final ContactNMessage number = new ContactNMessage();
            number.id = cursor.getLong(0);
            number.phoneNumber = cursor.getString(1);
            number.message = cursor.getString(2);
            contactNMessages.add(number);
            cursor.moveToNext();
        }
        return contactNMessages;
    }

    /**
     * Gets all numbers.
     *
     * @return the all numbers
     */
    public ArrayList<String> getAllNumbers() {
        numbers = new ArrayList<>();
        numbersNContacts = new ArrayList<>();
        numberNMessageMap = new HashMap<String, String>();
        final Cursor cursor = database.query(FeedReaderContract.FeedEntry.TABLE_NAME, new String[]{"phone_number", "message"}, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            String number = cursor.getString(0);
            String message = cursor.getString(1);
            String fullMessage = number+"\n\n"+message;
            numbersNContacts.add(fullMessage);
            numbers.add(number);
            numberNMessageMap.put(number,message);
            cursor.moveToNext();
        }
        return numbersNContacts;
    }

}
