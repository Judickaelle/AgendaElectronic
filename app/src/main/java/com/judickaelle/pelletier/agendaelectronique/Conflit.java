package com.judickaelle.pelletier.agendaelectronique;

import android.content.ContentValues;
import android.content.Intent;
import android.util.Log;

public class Conflit {

    private static boolean pb;
    private static ContentValues contentValues;

    public Conflit(boolean p, ContentValues content) {
        pb = p;
        contentValues = content;
    }

    public static void doPositiveClick() {
        pb = false;
        MainActivity.getInstance().InsertWithoutConflit(contentValues);
    }

    public void setContent(ContentValues content) {
        contentValues = content;
    }
}
