package com.fakeMessager.helper;

import android.provider.BaseColumns;

/**
 * Created by lasitha on 7/20/15.
 */
public final class FeedReaderContract {
    /**
     * Instantiates a new Feed reader contract.
     */
// To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public FeedReaderContract() {}

    /**
     * The type Feed entry.
     */
/* Inner class that defines the table contents */
    public static abstract class FeedEntry implements BaseColumns {
        /**
         * The constant TABLE_NAME.
         */
        public static final String TABLE_NAME = "contacts_messages";
        /**
         * The constant COLUMN_NAME_PHONE.
         */
        public static final String COLUMN_NAME_PHONE = "phone_number";
        /**
         * The constant COLUMN_NAME_MESSAGE.
         */
        public static final String COLUMN_NAME_MESSAGE = "message";
    }
}
