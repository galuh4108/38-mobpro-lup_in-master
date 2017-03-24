package com.d3if3804160.shareeat.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yasmine on 11/12/2016.
 */

public class HistoryDB extends SQLiteOpenHelper {

    public HistoryDB(Context context) {
        super(context, "history.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE = "CREATE TABLE " + HistoryContract.TABLE_NAME + " (" +
                HistoryContract._ID + " INTEGER PRIMARY KEY, " +
                HistoryContract.COLUMN_ID_RESTAURANT + " TEXT, " +
                HistoryContract.COLUMN_NAME + " TEXT, " +
                HistoryContract.COLUMN_ALAMAT + " TEXT, " +
                HistoryContract.COLUMN_URL + " TEXT, " +
                HistoryContract.COLUMN_TELEPON + " TEXT, " +
                HistoryContract.COLUMN_DESKRIPSI + " TEXT " +" )";
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int newVersion) {

    }

    public Cursor getData() { //melakukan query
        String[] Projection = {
                HistoryContract._ID,
                HistoryContract.COLUMN_NAME
        };
        String sortOrder =
                HistoryContract._ID + " DESC";
        SQLiteDatabase db = getReadableDatabase();
        return db.query(HistoryContract.TABLE_NAME, Projection, null, null, null, null, sortOrder);
    }

    public Cursor getDataDetail(String id) { //melakukan query
        SQLiteDatabase db = getReadableDatabase();
        String[] Projection = {
                HistoryContract.COLUMN_ID_RESTAURANT,
                HistoryContract.COLUMN_NAME,
                HistoryContract.COLUMN_ALAMAT,
                HistoryContract.COLUMN_URL,
                HistoryContract.COLUMN_TELEPON,
                HistoryContract.COLUMN_DESKRIPSI
        };
        String selection = HistoryContract.COLUMN_ID_RESTAURANT + " = ?";
        String[] selectionArgs = { id };
        Cursor c = db.query(
                HistoryContract.TABLE_NAME,                     // The table to query
                Projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        return c;
    }

    public void insertData(String id, String nama, String alamat, String url, String telpon, String deskripsi) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HistoryContract.COLUMN_ID_RESTAURANT, id);
        values.put(HistoryContract.COLUMN_NAME, nama);
        values.put(HistoryContract.COLUMN_ALAMAT, alamat);
        values.put(HistoryContract.COLUMN_URL, url);
        values.put(HistoryContract.COLUMN_TELEPON, telpon);
        values.put(HistoryContract.COLUMN_DESKRIPSI, deskripsi);
        db.insert(HistoryContract.TABLE_NAME, null, values);
    }

    public void deleteData(String id){
        SQLiteDatabase db = getWritableDatabase();
        // Define 'where' part of query.
        String selection = HistoryContract.COLUMN_ID_RESTAURANT+ " LIKE ?";
// Specify arguments in placeholder order.
        String[] selectionArgs = { id };
// Issue SQL statement.
        db.delete(HistoryContract.TABLE_NAME, selection, selectionArgs);
    }
}
