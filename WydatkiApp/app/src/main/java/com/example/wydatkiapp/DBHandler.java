package com.example.wydatkiapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "expensesDB_JAVA.db";

    public static final String TABLE_EXPENSE = "expenses";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_OWNER = "owner";
    public static final String COLUMN_AMOUNT = "amount";
    public static final String COLUMN_DATE = "date";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EXPENSES_TABLE =
                "CREATE TABLE " + TABLE_EXPENSE + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_OWNER + " TEXT," +
                COLUMN_AMOUNT + " INTEGER," +
                COLUMN_DATE + " INTEGER" + ")";

        db.execSQL(CREATE_EXPENSES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE);
        onCreate(db);
    }

    public Cursor getExpenses() {
        String query = "SELECT * FROM " + TABLE_EXPENSE;
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(query, null);
    }

    public void addExpenses(Expense expense) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_OWNER, expense.getOwner());
        values.put(COLUMN_AMOUNT, expense.getAmount());
        values.put(COLUMN_DATE, expense.getDate().toString());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_EXPENSE, null, values);
        db.close();
    }

    public void deleteExpense(String id) {
        String query = "SELECT * FROM " + TABLE_EXPENSE +
                " WHERE " + COLUMN_ID +
                "= \"" + id + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            int currentId = Integer.parseInt(cursor.getString(0));
            db.delete(TABLE_EXPENSE, COLUMN_ID + " = ?",
                    new String[]{String.valueOf(currentId)});

            cursor.close();
        }
        db.close();
    }

    public void updateExpense(int id, int amount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_AMOUNT, amount);

        db.update(TABLE_EXPENSE, contentValues,
                COLUMN_ID + "=" + id, null);

        db.close();
    }
}
