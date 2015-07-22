
package com.fakeMessager.fragment;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.fakeMessager.R;
import com.fakeMessager.controller.OnNumberSelectedListener;
import com.fakeMessager.service.ContactNMessageDao;

/**
 * The type Number fragment.
 */
public class NumberFragment extends ListFragment {
    /**
     * The M callback.
     */
    OnNumberSelectedListener mCallback;

    /** {@inheritDoc} */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;
        setListAdapter(new ArrayAdapter<String>(getActivity(), layout, ContactNMessageDao.numbers));
    }

    /** {@inheritDoc} */
    @Override
    public void onStart() {
        super.onStart();
        if (getFragmentManager().findFragmentById(R.id.message) != null) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (OnNumberSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnNumberSelectedListener");
        }
    }

    /** {@inheritDoc} */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        mCallback.onNumberSelected(position);
        getListView().setItemChecked(position, true);
    }
}