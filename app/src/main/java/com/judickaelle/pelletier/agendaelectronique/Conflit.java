package com.judickaelle.pelletier.agendaelectronique;

import android.content.Intent;
import android.util.Log;

public class Conflit {

    private static boolean pb;

    public static boolean isPb() {
        return pb;
    }
    public static void setPb(boolean pb) {
        Conflit.pb = pb;
    }

    public Conflit(boolean pb) {this.setPb(pb);}

    public static void doPositiveClick() {setPb(false);}

    public static void doNegativeClick() {setPb(true);}


}
