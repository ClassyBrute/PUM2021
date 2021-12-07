package com.example.studentcrimeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "crimesDB_JAVA.db";

    public static final String TABLE_CRIME = "crimes";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_INDEX = "indexNumber";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_SOLVED = "solved";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CRIMES_TABLE = "CREATE TABLE " + TABLE_CRIME +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_TITLE + " TEXT," +
                COLUMN_INDEX + " INTEGER," +
                COLUMN_SOLVED + " INTEGER," +
                COLUMN_DATE + " INTEGER" + ")";

        db.execSQL(CREATE_CRIMES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CRIME);
        onCreate(db);
    }

    public Cursor getCrimes(){
        String query = "SELECT * FROM " + TABLE_CRIME;
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(query, null);
    }

    public void addCrimes(Crime crime){
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, crime.getTitle());
        values.put(COLUMN_INDEX, crime.getIndex());
        values.put(COLUMN_SOLVED, crime.isSolved());
        values.put(COLUMN_DATE, crime.getIntDate());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_CRIME, null, values);
        db.close();
    }
}
