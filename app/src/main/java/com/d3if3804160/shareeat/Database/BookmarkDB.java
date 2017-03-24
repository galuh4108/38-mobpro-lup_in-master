package com.d3if3804160.shareeat.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yasmine on 11/12/2016.
 */

public class BookmarkDB extends SQLiteOpenHelper {

    public BookmarkDB (Context context){
        super(context, "bookmark.db", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE = "CREATE TABLE " + BookmarkContract.TABLE_NAME + " (" +
                BookmarkContract._ID + " INTEGER PRIMARY KEY, " +
                BookmarkContract.COLUMN_ID_RESTAURANT + " TEXT, " +
                BookmarkContract.COLUMN_NAME + " TEXT " +" )";
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int newVersion) {

    }

    public Cursor getData() { //melakukan query
        String[] Projection = {
                BookmarkContract._ID,
                BookmarkContract.COLUMN_NAME
        };
        String sortOrder =
                BookmarkContract._ID + " DESC";
        SQLiteDatabase db = getReadableDatabase();
        return db.query(BookmarkContract.TABLE_NAME, Projection, null, null, null, null, sortOrder);
    }

    public Boolean cekBookmark() { //melakukan query

        SQLiteDatabase db = getReadableDatabase();
        String[] Projection = {
                BookmarkContract._ID,
                BookmarkContract.COLUMN_NAME
        };
        Cursor c = db.query(BookmarkContract.TABLE_NAME, Projection, null, null, null, null, null);
        if (c != null){
            return true;
        }
        else{
            return false;
        }
    }

    public void insertData(String id, String nama) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BookmarkContract.COLUMN_ID_RESTAURANT, id);
        values.put(BookmarkContract.COLUMN_NAME, nama);
        db.insert(BookmarkContract.TABLE_NAME, null, values);
    }


    public void deleteData(String id){
        SQLiteDatabase db = getWritableDatabase();
        // Define 'where' part of query.
        String selection = BookmarkContract.COLUMN_ID_RESTAURANT+ " LIKE ?";
// Specify arguments in placeholder order.
        String[] selectionArgs = { id };
// Issue SQL statement.
        db.delete(BookmarkContract.TABLE_NAME, selection, selectionArgs);
    }
}
