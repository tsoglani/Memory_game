package com.tsoglakos.mem_game;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FindImageGamefDB";
    public static final String TOP_SCORE_TABLE = "top_score_table";

    public static final String ID = "_id";
    public static final String TOP_SCORE_VALUE = "top_score_value";
    private Context context;


    public DB(Context context, String name, CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TOP_SCORE_TABLE + "("
                    + ID + " INTEGER PRIMARY KEY," + TOP_SCORE_VALUE + " TEXT)";
            db.execSQL(CREATE_PRODUCTS_TABLE);
        } catch (Exception e) {

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TOP_SCORE_TABLE);
        onCreate(db);
    }

    public boolean addProduct(String curentValue) {
        if (getValue() == null || getValue().equals("") || Integer.parseInt(getValue()) < Integer.parseInt(curentValue)) {
            Toast.makeText(context,"Congratulations you made a new Hight Score : "+ curentValue,Toast.LENGTH_LONG).show();

            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("DROP TABLE IF EXISTS " + TOP_SCORE_TABLE);
            onCreate(db);
            ContentValues values = new ContentValues();
            values.put(TOP_SCORE_VALUE, curentValue);////////////////////////
            db.insert(TOP_SCORE_TABLE, null, values);
            db.close();
            return true;
        }
        return false;
    }

    public String getValue() {
        String query = "Select * FROM " + TOP_SCORE_TABLE;
        String value = null;
        SQLiteDatabase db = this.getWritableDatabase();
        String str = null;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            //Integer.parseInt(cursor.getString(0));
            value = cursor.getString(1);

            cursor.close();
        }
        db.close();
        return value;
    }

}
