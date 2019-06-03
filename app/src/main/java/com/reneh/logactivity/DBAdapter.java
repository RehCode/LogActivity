package com.reneh.logactivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBAdapter {
    Context context;
    SQLiteDatabase db;
    DBHelper helper;

    public DBAdapter(Context c) {
        this.context = c;
        helper = new DBHelper(context);
    }

    public void openDB() {
        try {
            db = helper.getWritableDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeDB() {
        try {
            helper.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean add(String actividad) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(helper.COLUMN_ACTIVIDAD, actividad);

            long id = db.insert(helper.TABLE_ACTIVIDADES, null, contentValues);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Cursor retrieve() {
        String[] columns = {helper.COLUMN_ID, helper.COLUMN_ACTIVIDAD, helper.COLUMN_FECHA};
        Cursor cursor;
        cursor = db.query("actividades", columns, null, null, null, null, helper.COLUMN_ID + " DESC");
        return cursor;
    }

    public void recreateDatabase() {
        db.execSQL("drop table if exists " + helper.TABLE_ACTIVIDADES);
        db.execSQL(helper.DATABASE_CREATE);
    }
}
