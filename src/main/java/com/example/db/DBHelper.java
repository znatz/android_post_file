package com.example.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.model.Person;

public class DBHelper extends SQLiteOpenHelper{
    private static String DATABASE_NAME = "person.db";
    private static int DATABASE_VERSION = 1;
    private static final String TABLE_PERSON   = "person";
    private static final String PERSON_NAME     = "name";
    private static final String PERSON_AGE         = "age";
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PERSON_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PERSON + "(" +
                                        PERSON_NAME + " STRING, " +
                                        PERSON_AGE  + " INTEGER)";
        db.execSQL(CREATE_PERSON_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_PERSON_TABLE  = "DROP TABLE IF EXISTS " + TABLE_PERSON;
        db.execSQL(DROP_PERSON_TABLE);
    }

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
}
