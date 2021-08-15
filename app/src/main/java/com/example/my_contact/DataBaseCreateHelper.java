package com.example.my_contact;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseCreateHelper extends SQLiteOpenHelper{
    public static final  String DATABASE_NAME = "contacts.db";
    public  static final  int DATABASE_VERSION = 1;
    public static final String TABLE_CONTACT_INFO = "contact_info";

    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_NUMBER = "number";
    public static final String COL_IMAGE = "image";

    public static final String CREATE_TABLE_CONTACT_INFO = "create table "+ TABLE_CONTACT_INFO+ " ( "
            + COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +COL_NAME + " TEXT, "
            +COL_NUMBER + " TEXT, "
            +COL_IMAGE + "BLOB "
            +" ) " ;



    public DataBaseCreateHelper(@Nullable Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CONTACT_INFO);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" drop table if exists "+TABLE_CONTACT_INFO);
        this.onCreate(db);
    }
}
