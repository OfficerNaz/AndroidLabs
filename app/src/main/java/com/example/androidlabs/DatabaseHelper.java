package com.example.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "TO-DO.DB";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "TO_DO_LIST_ITEMS";
    public static final String COL_ID = "_ID";
    public static final String COL_ITEMS = "ITEMS";
    public static final String COL_URG = "Is_URGENT";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_ITEMS + " text, " + COL_URG + " integer);");  // add or remove columns
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        {
            db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
