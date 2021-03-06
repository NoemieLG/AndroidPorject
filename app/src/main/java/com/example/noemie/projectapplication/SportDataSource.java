package com.example.noemie.projectapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Noemie on 30/03/2018.
 */

public class SportDataSource {

    private DataBase myDB;
    private SQLiteDatabase database;
    private String[] allColumns = { DataBase.COL_ID, DataBase.COL_NOM, DataBase.COL_DATE,
            DataBase.COL_LATITUDE, DataBase.COL_LONGITUDE, DataBase.COL_TEMPS, DataBase.COL_COM};


    public SportDataSource(Context context) {
        myDB = new DataBase(context);
    }

    public void open() throws SQLException {
        database = myDB.getWritableDatabase();
    }

    public void close() {
        myDB.close();
    }

    public CourseTable createCourse(String name, String date, String latitude, String longitude, String tps, String com) {

        ContentValues values = new ContentValues();
        values.put(DataBase.COL_NOM, name);
        values.put(DataBase.COL_DATE, date);
        values.put(DataBase.COL_LATITUDE, latitude);
        values.put(DataBase.COL_LONGITUDE, longitude);
        values.put(DataBase.COL_TEMPS, tps);
        values.put(DataBase.COL_COM, com);
        long insertId = database.insert(DataBase.TABLE_COURSE, null, values);
        Cursor cursor = database.query(DataBase.TABLE_COURSE, allColumns, DataBase.COL_ID + " = " + insertId,
                null, null, null, null);
        cursor.moveToFirst();
        CourseTable newCourse = cursorToCourse(cursor);
        cursor.close();
        return newCourse;
    }


    public void deleteCourse(CourseTable course) {
        long id = course.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(DataBase.TABLE_COURSE, DataBase.COL_ID + " = " + id, null);
    }

    public List<CourseTable> getAllComments() {
        List<CourseTable> courses = new ArrayList<>();

        Cursor cursor = database.query(DataBase.TABLE_COURSE,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            CourseTable course = cursorToCourse(cursor);
            courses.add(course);
            cursor.moveToNext();
        }
        // assurez-vous de la fermeture du curseur
        cursor.close();
        return courses;
    }

    public List<CourseTable> getFiveCourses(){
        List<CourseTable> courses = new ArrayList<>();
        List<CourseTable> fivecourses = new ArrayList<>();

        Cursor cursor = database.query(DataBase.TABLE_COURSE,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            CourseTable course = cursorToCourse(cursor);
            courses.add(course);
            cursor.moveToNext();
        }
        // assurez-vous de la fermeture du curseur
        cursor.close();
        if(courses.size() > 5){
            fivecourses.add(courses.get((courses.size()-1)));
            fivecourses.add(courses.get((courses.size()-2)));
            fivecourses.add(courses.get((courses.size()-3)));
            fivecourses.add(courses.get((courses.size()-4)));
            fivecourses.add(courses.get((courses.size()-5)));
            return fivecourses;
        } else {
            return courses;
        }
    }

    private CourseTable cursorToCourse(Cursor cursor) {
        CourseTable course = new CourseTable();
        course.setId(cursor.getLong(cursor.getColumnIndex(DataBase.COL_ID)));
        course.setNom(cursor.getString(cursor.getColumnIndex(DataBase.COL_NOM)));
        course.setDate(cursor.getString(cursor.getColumnIndex(DataBase.COL_DATE)));
        course.setLat(cursor.getString(cursor.getColumnIndex(DataBase.COL_LATITUDE)));
        course.setLon(cursor.getString(cursor.getColumnIndex(DataBase.COL_LONGITUDE)));
        course.setTemps(cursor.getString(cursor.getColumnIndex(DataBase.COL_TEMPS)));
        course.setCom(cursor.getString(cursor.getColumnIndex(DataBase.COL_COM)));
        return course;
    }

    public void updateCourse(long id, String tps, String com){
        ContentValues values = new ContentValues();
        values.put(DataBase.COL_TEMPS, tps);
        values.put(DataBase.COL_COM, com);

        database.update(DataBase.TABLE_COURSE, values, DataBase.COL_ID + "=" + id, null);
        Cursor cursor = database.query(DataBase.TABLE_COURSE, allColumns, DataBase.COL_ID + "=" + id,
                null, null, null, null);
        cursor.moveToFirst();
        CourseTable newCourse = cursorToCourse(cursor);
        cursor.close();
    }

}
