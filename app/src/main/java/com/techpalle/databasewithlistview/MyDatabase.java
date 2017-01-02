package com.techpalle.databasewithlistview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by manasranjan on 1/2/2017.
 */
public class MyDatabase {
    MyHelper myHelper;
    SQLiteDatabase sdb;
    public MyDatabase(Context c){
        myHelper=new MyHelper(c,"techpalle.db",null,1);

    }
    public  void open(){
        sdb=myHelper.getWritableDatabase();
    }

    public void insertStudent(String name,String sub){
        ContentValues cv=new ContentValues();
        cv.put("sname",name);
        cv.put("ssub",sub);
        sdb.insert("student",null,cv);
    }
    public Cursor queryStudent() {
        Cursor c = null;
        //q1-read all student details
        c = sdb.query("student", null, null, null, null, null, null, null);
        return c;
    }
    public void close(){
        sdb.close();//otherwise database will leak memory
    }

    private class MyHelper extends SQLiteOpenHelper{

        public MyHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table student(_id integer primary key,sname text,ssub text);");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
