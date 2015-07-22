package com.fakeMessager.model;

/**
 * Created by lasitha on 7/20/15.
 */
public class ContactNMessage {

    /**
     * The Id.
     */
// Two mapping fields for the database table blacklist
    public long id;
    /**
     * The Phone number.
     */
    public String phoneNumber;
    /**
     * The Message.
     */
    public String message;

    /**
     * Instantiates a new Contact n message.
     */
// Default constructor
    public ContactNMessage() {

    }

    /**
     * Instantiates a new Contact n message.
     *
     * @param phoneMumber the phone mumber
     */
    public ContactNMessage(final String phoneMumber) {
        this.phoneNumber = phoneMumber;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(final Object obj) {

        if (obj.getClass().isInstance(new ContactNMessage())) {
            final ContactNMessage bl = (ContactNMessage) obj;
            if (bl.phoneNumber.equalsIgnoreCase(this.phoneNumber))
                return true;
        }
        return false;
    }
}