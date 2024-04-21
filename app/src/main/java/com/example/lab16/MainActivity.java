package com.example.lab16;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity implements
        View.OnClickListener{
    Button buttonDel, buttonClear, buttonShow, buttonAdd;
    EditText ET_name, ET_age,ET_gender,ET_email,ET_phone;
    LinearLayout container;
    DBHelper DBHelper;
    //инициализация всех кнопок и полей ввода
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(this);
        buttonShow = (Button) findViewById(R.id.buttonShow);
        buttonShow.setOnClickListener(this);
        buttonDel = (Button) findViewById(R.id.buttonDel);
        buttonDel.setOnClickListener(this);
        buttonClear = (Button) findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(this);
        ET_name = (EditText) findViewById(R.id.ET_name);
        ET_age = (EditText) findViewById(R.id.ET_age);
        ET_gender = (EditText) findViewById(R.id.ET_gender);
        ET_email = (EditText) findViewById(R.id.ET_email);
        ET_phone = (EditText) findViewById(R.id.ET_phone);
        container = findViewById(R.id.container);
        DBHelper = new DBHelper(this);
    }
    @Override
    public void onClick(View v) {
        //получение данных из всех полей
        String input_name = ET_name.getText().toString();
        String input_phone = ET_phone.getText().toString();
        String input_age = ET_age.getText().toString();
        String input_gender = ET_gender.getText().toString();
        String input_email = ET_email.getText().toString();
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        if (v.getId()==R.id.buttonShow) {
            container.removeAllViews();
            //объявление какие поля и бд булут использоваться
            String[] projection = {
                    DBHelper.KEY_NAME,
                    DBHelper.KEY_AGE,
                    DBHelper.KEY_GENDER,
                    DBHelper.KEY_EMAIL,
                    DBHelper.KEY_PHONE
            };
            //передача в курсор названия таблицы и полей
            Cursor cursor = db.query(
                    DBHelper.TABLE_CONTACTS,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    null
            );
            //через курсор получаем интовые значения для каждого поля
            int index_name = cursor.getColumnIndex(DBHelper.KEY_NAME);
            int index_age = cursor.getColumnIndex(DBHelper.KEY_AGE);
            int index_gender =
                    cursor.getColumnIndex(DBHelper.KEY_GENDER);
            int index_email = cursor.getColumnIndex(DBHelper.KEY_EMAIL);
            int index_phone = cursor.getColumnIndex(DBHelper.KEY_PHONE);
            //переводим интовые значения курсора в строки и помещаем в текст
            while (cursor.moveToNext()) {

                String params[] = {
                        cursor.getString(index_name),
                        cursor.getString(index_age),
                        cursor.getString(index_gender),
                        cursor.getString(index_email),
                        cursor.getString(index_phone)
                };
                Entry entry = new Entry(this, params);
                container.addView(entry);
                Space space = new Space(this);
                space.setMinimumWidth(8);
                container.addView(space);
            }
            cursor.close();
        }
        //добавление в бд записи(каждого поля)
        if (v.getId()==R.id.buttonAdd) {
            values.put(DBHelper.KEY_NAME, input_name);
            values.put(DBHelper.KEY_AGE, input_age);
            values.put(DBHelper.KEY_GENDER, input_gender);
            values.put(DBHelper.KEY_EMAIL, input_email);
            values.put(DBHelper.KEY_PHONE, input_phone);
            db.insert(DBHelper.TABLE_CONTACTS, null, values);
            buttonShow.callOnClick();
        }//удаление из бд записи по имени
        if (v.getId()==R.id.buttonDel) {
            String selection = DBHelper.KEY_NAME + "=?";
            String[] selectionArgs = {input_name};
            db.delete(DBHelper.TABLE_CONTACTS, selection,
                    selectionArgs);
            buttonShow.callOnClick();
        }//очисть бд
        if (v.getId()==R.id.buttonClear) {
            db.delete(DBHelper.TABLE_CONTACTS, null, null);
            buttonShow.callOnClick();
        }
        DBHelper.close();
    }
}
