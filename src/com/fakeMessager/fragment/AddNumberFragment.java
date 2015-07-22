package com.fakeMessager.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.fakeMessager.R;
import com.fakeMessager.service.ContactNMessageDao;

/**
 * Created by lasitha on 7/17/15.
 */
public class AddNumberFragment extends Fragment {
    /**
     * The constant ARG_POSITION.
     */
    public final static String ARG_POSITION = "position";
    /**
     * The M current position.
     */
    int mCurrentPosition = -1;
    private ContactNMessageDao contactNMessageDao;

    /** {@inheritDoc} */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }
        return inflater.inflate(R.layout.customlist, container, false);
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
        mCurrentPosition = position;
    }

    /** {@inheritDoc} */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_POSITION, mCurrentPosition);
    }

}
