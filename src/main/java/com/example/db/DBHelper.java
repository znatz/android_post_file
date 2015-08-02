package com.example.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.model.Person;
import com.example.model.ReceiptLine;

public class DBHelper extends SQLiteOpenHelper{
    private static String DATABASE_NAME = "person.db";
    private static int DATABASE_VERSION = 1;

    /* Table */
    private static final String TABLE_PERSON        = "person";
    private static final String TABLE_RECEIPTLINE   = "transfer";

    /* Fields */
    private static final String PERSON_NAME         = "name";
    private static final String PERSON_AGE          = "age";

    private static final String RECEIPTLINE_TANTO         = "tantoID";
    private static final String RECEIPTLINE_TITLE         = "goodsTitle";
    private static final String RECEIPTLINE_KOSU          = "kosu";
    private static final String RECEIPTLINE_TIME          = "time";
    private static final String RECEIPTLINE_RECEIPTNO     = "receiptNo";
    private static final String RECEIPTLINE_TABLENO       = "tableNO";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PERSON_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PERSON + "(" +
                                        PERSON_NAME + " STRING, " +
                                        PERSON_AGE  + " INTEGER)";

        String CREATE_RECEIPTLINE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_RECEIPTLINE + "(" +
                                            RECEIPTLINE_TANTO + " TEXT, " +
                                            RECEIPTLINE_TITLE + " TEXT, " +
                                            RECEIPTLINE_KOSU  + " TEXT, " +
                                            RECEIPTLINE_TIME  + " TEXT, " +
                                            RECEIPTLINE_RECEIPTNO + " TEXT, " +
                                            RECEIPTLINE_TABLENO   + " TEXT)";
        db.execSQL(CREATE_RECEIPTLINE_TABLE);
        db.execSQL(CREATE_PERSON_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_PERSON_TABLE  = "DROP TABLE IF EXISTS " + TABLE_PERSON;
        db.execSQL(DROP_PERSON_TABLE);

        String DROP_RECEIPTLINE_TABLE  = "DROP TABLE IF EXISTS " + TABLE_RECEIPTLINE;
        db.execSQL(DROP_RECEIPTLINE_TABLE);
    }
    public void clearup() {
        SQLiteDatabase db = this.getWritableDatabase();
        String DROP_PERSON_TABLE  = "DROP TABLE IF EXISTS " + TABLE_PERSON;
        db.execSQL(DROP_PERSON_TABLE);

        String DROP_RECEIPTLINE_TABLE  = "DROP TABLE IF EXISTS " + TABLE_RECEIPTLINE;
        db.execSQL(DROP_RECEIPTLINE_TABLE);
    }

    /* Person */
    public void addPerson (Person p) {
        ContentValues cv = new ContentValues();
        cv.put(PERSON_AGE, p.getAge());
        cv.put(PERSON_NAME, p.getName());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_PERSON,null,cv);
        db.close();
    }

    public Person findPerson (String personName) {
        String QUERY = "SELECT * FROM " + TABLE_PERSON + " WHERE " + PERSON_NAME + "='" + personName + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(QUERY, null);
        Person result = new Person();
        if(cursor.moveToFirst()) {
            cursor.moveToFirst();
            result.setName(cursor.getString(0));
            result.setAge(cursor.getInt(1));
            cursor.close();
        } else {
            result = null;
        }
        db.close();
        return result;
    }
    public boolean deletePerson (String personName) {
        boolean flag = false;
        String QUERY = "SELECT * FROM " + TABLE_PERSON + " WHERE " + PERSON_NAME + "='" + personName + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(QUERY, null);
        Person target = new Person();
        if(cursor.moveToFirst()) {
            target.setName(cursor.getString(0));
            db.delete(TABLE_PERSON, PERSON_NAME + "=?", new String[]{target.getName()});
            cursor.close();
            flag = true;
        }
        db.close();
        return flag;
    }

    /* ReceiptLine */
    public void addReceiptLine (ReceiptLine p) {
        ContentValues cv = new ContentValues();
        cv.put(RECEIPTLINE_TANTO,   p.getTantoID());
        cv.put(RECEIPTLINE_TITLE,   p.getGoodsTitle());
        cv.put(RECEIPTLINE_KOSU,    p.getKosu());
        cv.put(RECEIPTLINE_TIME,    p.getTime());
        cv.put(RECEIPTLINE_RECEIPTNO, p.getReceiptNo());
        cv.put(RECEIPTLINE_TABLENO,   p.getTableNO());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_RECEIPTLINE,null,cv);
        db.close();
    }
}
