package com.d3if3804160.shareeat.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.d3if3804160.shareeat.Database.HistoryContract;
import com.d3if3804160.shareeat.R;

/**
 * Created by yasmine on 11/12/2016.
 */

public class HistoryAdapter extends ResourceCursorAdapter {


    public HistoryAdapter (Context context, int layout, Cursor c, int flags){
        super(context, layout, c, flags);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textView = (TextView)view.findViewById(R.id.name);
        textView.setText(cursor.getString(cursor.getColumnIndex(HistoryContract.COLUMN_NAME)));
    }


}