package com.bronet.blockchain.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public MyDatabaseHelper(Context context){
        super(context,"person.db",null,1);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table person(" +
                "id integer primary key autoincrement," +
                "name varchar(20)," +
                "number varchar(20))");

    }

    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        db.execSQL("alter table person add account varchar(29)");
    }

}
