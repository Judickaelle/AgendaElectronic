package com.judickaelle.pelletier.agendaelectronique;

import android.content.ContentValues;
import android.content.Intent;
import android.util.Log;

public class Conflit {

    private static boolean pb;
    private static ContentValues contentValues;

    public static boolean isPb() {
        return pb;
    }

    public static void setPb(boolean pb) {
        Conflit.pb = pb;
    }



    public Conflit(boolean p, ContentValues content) {
        pb = p;
        contentValues = content;
    }

    public static void doPositiveClick() {
        pb = false;
        MainActivity.getInstance().InsertWithoutConflit(contentValues);
    }

    public void doNegativeClick() {
        pb = true;
    }

    public void setContent(ContentValues content) {
        contentValues = content;
    }
}
