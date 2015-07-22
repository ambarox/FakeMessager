
package com.fakeMessager.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.fakeMessager.R;
import com.fakeMessager.activity.MainScreenActivity;
import com.fakeMessager.service.ContactNMessageDao;

/**
 * The type Message fragment.
 */
public class MessageFragment extends Fragment {
    /**
     * The constant ARG_POSITION.
     */
    public final static String ARG_POSITION = "position";
    /**
     * The M current position.
     */
    int mCurrentPosition = -1;
    /**
     * The Contact n message dao.
     */
    ContactNMessageDao contactNMessageDao;

    /**
     * Instantiates a new Message fragment.
     */
    public MessageFragment(){

    }

    /** {@inheritDoc} */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, 
        Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }
        return inflater.inflate(R.layout.detailedview, container, false);
    }

    /** {@inheritDoc} */
    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if (args != null) {
            updateMessageView(args.getInt(ARG_POSITION));
        } else if (mCurrentPosition != -1) {
            updateMessageView(mCurrentPosition);
        }
    }

    /**
     * Update message view.
     *
     * @param position the position
     */
    public void updateMessageView(int position) {
        TextView article = (TextView) getActivity().findViewById(R.id.message);
        article.setText(ContactNMessageDao.numbersNContacts.get(position));
        Button delete = (Button) getActivity().findViewById(R.id.delete_btn);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = ContactNMessageDao.numbers.get(position);
                ContactNMessageDao.delete(phoneNumber);
                Intent intent = new Intent(getActivity(), MainScreenActivity.class);
                getActivity().finish();
                startActivity(intent);
            }
        });
        mCurrentPosition = position;
    }

    /** {@inheritDoc} */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_POSITION, mCurrentPosition);
    }
}