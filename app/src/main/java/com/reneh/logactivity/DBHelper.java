package com.reneh.logactivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "actividades.db";

    public static final String TABLE_ACTIVIDADES = "actividades";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ACTIVIDAD = "actividad";
    public static final String COLUMN_FECHA = "fecha";

    public String DATABASE_CREATE = "create table " + TABLE_ACTIVIDADES + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_ACTIVIDAD + " text not null, "
            + COLUMN_FECHA + " DATETIME DEFAULT (datetime('now','localtime')));";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_ACTIVIDADES);
        onCreate(sqLiteDatabase);
    }

}
