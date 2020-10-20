package com.example.hello;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class RateManager {
    private DBHelper dbHelper;
    private String TBNAME;

    public RateManager(Context context){
        dbHelper = new DBHelper(context);
        TBNAME = DBHelper.TB_NAME;
    }

    public void addAll(List<Currency> list){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        for(Currency currency:list) {
            ContentValues values = new ContentValues();
            values.put("curname", currency.getName());
            values.put("currate", currency.getRate());
            db.insert(TBNAME, null, values);
        }
        db.close();
    }

    public Currency findById(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME,null,
                "id=?",new String[]{String.valueOf(id)},
                null,null,null);

        Currency currency = null;
        if(cursor != null && cursor.moveToFirst()){
           currency = new Currency();
           currency.setId(cursor.getInt(cursor.getColumnIndex("id")));
           currency.setName(cursor.getString(cursor.getColumnIndex("curname")));
           currency.setRate(cursor.getString(cursor.getColumnIndex("currate")));
           cursor.close();
        }

        db.close();
        return currency;
    }

    public List<Currency> findAll(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME,null,
                null,null,
                null,null,null);

        List<Currency> list = new ArrayList<>();

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {

                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("curname"));
                String rate = cursor.getString(cursor.getColumnIndex("currate"));

                Currency currency = new Currency();
                currency.setId(id);
                currency.setName(name);
                currency.setRate(rate);

                list.add(currency);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();

        return list;
    }

    public void deleteAll(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME,null,null);
    }
}
