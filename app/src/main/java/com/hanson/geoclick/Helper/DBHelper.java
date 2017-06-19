package com.hanson.geoclick.Helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.hanson.geoclick.Model.PictureItem;

import java.util.ArrayList;

/**
 * Created by lily on 2017-06-18.
 */

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table
        db.execSQL("CREATE TABLE Picture( _id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "country TEXT, city TEXT, latitude TEXT, longitude TEXT,"
                + "thumbnail BLOB, mainImg BLOB);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void Picture_Insert(String country, String city, String latitude, String longitude,
                               byte[] thumbnail, byte[] mainImg) {

        // open read and write database
        SQLiteDatabase db = getWritableDatabase();
        // execute insert query

        SQLiteStatement p = db.compileStatement("INSERT INTO Picture values(?,?,?,?,?,?,?);");
        p.bindNull(1);
        p.bindString(2, country);
        p.bindString(3, city);
        p.bindString(4, latitude);
        p.bindString(5, longitude);
        p.bindBlob(6, thumbnail);
        p.bindBlob(7, mainImg);
        p.execute();
        db.close();
        Log.d("Database :", "INSERT Complate!");
    }

    public ArrayList<PictureItem> recipes_SelectAll()
    {
        // Open available reading database
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<PictureItem> allPictures = new ArrayList<>();
        // Get all recipes data
        Cursor cursor = db.rawQuery("SELECT * FROM Picture", null);
        if (cursor != null)
        {
            while (cursor.moveToNext()) {
                allPictures.add(new PictureItem(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getBlob(5),
                        cursor.getBlob(6)
                ));
            }
        }
        cursor.close();
        db.close();

        return allPictures;
    }
}
