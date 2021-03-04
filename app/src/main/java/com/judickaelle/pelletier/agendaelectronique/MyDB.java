package com.judickaelle.pelletier.agendaelectronique;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDB extends SQLiteOpenHelper {


    public MyDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String script = "CREATE TABLE EventCalendar ";
        script += "( Date TEXT,";
        script += "NameEvent TEXT,";
        script += "DescriptionEvent TEXT,";
        script += "LocalisationEvent TEXT,";
        script += "Guest TEXT);";
        //Execute script
        db.execSQL(script);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop table
        db.execSQL("DROP TABLE IF EXISTS EventCalendar");
        //Recreate
        this.onCreate(db);
    }
}
