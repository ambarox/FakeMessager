package com.fakeMessager.activity;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.fakeMessager.R;
import com.fakeMessager.model.ContactNMessage;
import com.fakeMessager.service.ContactNMessageDao;

/**
 * Created by lasitha on 7/17/15.
 */
public class AddNumberActivity extends FragmentActivity implements View.OnClickListener {

    private EditText phoneNumberET, messageET;
    private Button addContact, backButton;
    private ContactNMessageDao contactNMessageDao;

    /** {@inheritDoc} */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnumber);

        contactNMessageDao = new ContactNMessageDao(this);

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(this);

        addContact = (Button) findViewById(R.id.addEntry);
        addContact.setOnClickListener(this);

        phoneNumberET = (EditText) findViewById(R.id.mobile);
        messageET = (EditText) findViewById(R.id.customMessage);
    }

    /** {@inheritDoc} */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backButton:
                Intent intent = new Intent(this, MainScreenActivity.class);
                finish();
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(view, 0,
                        0, view.getWidth(), view.getHeight());
                startActivity(intent, options.toBundle());
            case R.id.addEntry:
                // All input fields are mandatory, so made a check
                if (phoneNumberET.getText().toString().trim().length() > 0 &&
                        phoneNumberET.getText().toString().trim().length() > 0 && messageET.getText().toString().trim().length() > 0 &&
                        messageET.getText().toString().trim().length() > 0) {
                    final ContactNMessage contactNMessage = new ContactNMessage();
                    contactNMessage.phoneNumber = phoneNumberET.getText().toString();
                    contactNMessage.message = messageET.getText().toString();
                    contactNMessageDao.create(contactNMessage);
                    showDialog();
                } else {
                    showMessageDialog("All fields are mandatory !!");
                }
        }
    }

    private void showDialog() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Phone Number added to List successfully !!");
        alertDialogBuilder.setPositiveButton("Add More",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        reset();
                    }
                });
        alertDialogBuilder.setNegativeButton("Done",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(AddNumberActivity.this, MainScreenActivity.class);
                        finish();
                        startActivity(intent);
                    }
                });
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void showMessageDialog(final String message) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void reset() {
        phoneNumberET.setText("");
        messageET.setText("");
    }
}
