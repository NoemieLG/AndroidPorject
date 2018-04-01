package com.example.noemie.projectapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Time;

/**
 * Created by Noemie on 30/03/2018.
 */

public class DataBase extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "Sport.db";
    private static final int DATABASE_VERSION = 2;
    public static final String TABLE_COURSE = "course";
    public static final String COL_ID = "Id";
    public static final String COL_NOM = "Nom";
    public static final String COL_DATE = "Date";
    public static final String COL_LATITUDE = "Latitude";
    public static final String COL_LONGITUDE = "Longitude";
    public static final String COL_TEMPS = "Temps";
    public static final String COL_COM = "Commentaire";
    private static final String DATABASE_CREATE = "create table "
            + TABLE_COURSE + "(" + COL_ID
            + " integer primary key autoincrement, " + COL_NOM
            + " text not null, " + COL_DATE
            + " text not null, " + COL_LATITUDE
            + " text not null, " + COL_LONGITUDE
            + " text not null, " + COL_TEMPS
            + " text not null, "+ COL_COM
            + " text not null); ";

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(DataBase.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_COURSE + ";");
        onCreate(sqLiteDatabase);
    }
}
