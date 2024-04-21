package com.example.lab16;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
public class DBHelper extends SQLiteOpenHelper implements BaseColumns {
    public static final String DB_CONTACTS = "contacts1.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_CONTACTS="contacts";
    public static final String KEY_ID="_id";
    public static final String KEY_NAME="name";
    public static final String KEY_PHONE="phone";
    public static final String KEY_AGE="age";
    public static final String KEY_EMAIL="email";
    public static final String KEY_GENDER="gender";
    public DBHelper(Context context) {
        super(context, DB_CONTACTS, null, DATABASE_VERSION);
    }
    //создание таблицы в бд с помощью SQL запроса
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_CONTACTS +"("+KEY_ID+" integer primary key, "+KEY_NAME+" text, "+ KEY_EMAIL+" text, " +KEY_AGE +" text, "+KEY_PHONE+" text, "+KEY_GENDER+" text"+")");
    }
    //обновление БД если разные версии
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }
}

