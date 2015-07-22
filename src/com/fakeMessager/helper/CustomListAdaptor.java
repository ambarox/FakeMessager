package com.fakeMessager.helper;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.fakeMessager.R;

import java.util.ArrayList;

/**
 * Created by lasitha on 7/20/15.
 */
public class CustomListAdaptor extends ArrayAdapter<String> {

    /**
     * Instantiates a new Custom list adaptor.
     *
     * @param context the context
     * @param textViewResourceId the text view resource id
     * @param objects the objects
     */
    public CustomListAdaptor(Context context, int textViewResourceId, ArrayList objects) {
        super(context, textViewResourceId, objects);
    }

    /**
     * The type View holder.
     */
    static class ViewHolder {
        /**
         * The Text.
         */
        TextView text;
        /**
         * The Btn.
         */
        Button btn;
    }

    /** {@inheritDoc} */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        String number = getItem(position);

        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            rowView = inflater.inflate(R.layout.customlist, parent, false);
            ViewHolder h = new ViewHolder();
            h.text = (TextView) rowView.findViewById(R.id.list_item_string);
            h.btn = (Button) rowView.findViewById(R.id.delete_btn);
            rowView.setTag(h);
        }

        ViewHolder h = (ViewHolder) rowView.getTag();

        h.text.setText(number);
        h.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // DO what you want to recieve on btn click there.
            }
        });

        return rowView;
    }
}
