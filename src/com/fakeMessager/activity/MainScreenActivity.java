package com.fakeMessager.activity;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.fakeMessager.R;
import com.fakeMessager.controller.OnNumberSelectedListener;
import com.fakeMessager.fragment.MessageFragment;
import com.fakeMessager.fragment.NumberFragment;
import com.fakeMessager.service.ContactNMessageDao;

import java.util.ArrayList;
import java.util.List;

/**
 * The type My activity.
 */

public class MainScreenActivity extends FragmentActivity
        implements OnNumberSelectedListener, View.OnClickListener {

    private static final int REFRESH = 1;
    private ContactNMessageDao contactNMessageDao;
    Animation animFadein;
    /**
     * The Block list.
     */
    public static List<String> blockList;

    /** {@inheritDointc} */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainview);
        contactNMessageDao = new ContactNMessageDao(this);

        Handler hRefresh = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what){
                    case REFRESH:
                /*Refresh UI*/
                        break;
                }
            }
        };
        ProgressDialog mProgressDlg = ProgressDialog.show(this, "Fake Messager", "Loading data...",
                true, false);
        new Thread(new Runnable(){
            public void run() {
			/*Load Data*/
                mProgressDlg.dismiss();
                hRefresh.sendEmptyMessage(REFRESH);
            }
        }).start();

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            ArrayList<String> allNumbers = contactNMessageDao.getAllNumbers();
            if (allNumbers != null && !allNumbers.isEmpty()) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("numbers", allNumbers);
                NumberFragment firstFragment = new NumberFragment();
                firstFragment.setArguments(getIntent().getExtras());
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, firstFragment).commit();
            }
        }

        Button addNumber = (Button) findViewById(R.id.addNumber);
        addNumber.setOnClickListener(this);
    }

    /** {@inheritDoc} */
    public void onNumberSelected(int position) {
        MessageFragment messageFragment = (MessageFragment)
                getSupportFragmentManager().findFragmentById(R.id.message);

        if (messageFragment != null) {
            messageFragment.updateMessageView(position);
        } else {
            MessageFragment newFragment = new MessageFragment();
            Bundle args = new Bundle();
            args.putInt(MessageFragment.ARG_POSITION, position);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addNumber:
                Intent intent = new Intent(this, AddNumberActivity.class);
                finish();
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(view, 0,
                        0, view.getWidth(), view.getHeight());
                startActivity(intent, options.toBundle());
        }
    }

    /** {@inheritDoc} */
    @Override
    protected void onResume() {
        super.onResume();
        contactNMessageDao = new ContactNMessageDao(this);
        blockList = contactNMessageDao.getAllNumbers();
        populateNoRecordMsg();
    }

    private void populateNoRecordMsg() {
        if (blockList.size() == 0) {
            final TextView tv = new TextView(this);
            tv.setPadding(5, 5, 5, 5);
            tv.setTextSize(15);
            tv.setText("No Record Found !!");
            FrameLayout layout = (FrameLayout) findViewById(R.id.fragment_container);
            layout.addView(tv);
        }
    }
}
