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

    public Conflit(boolean pb) {
        pb = false;
    }

    public static void doPositiveClick() {
        pb = false;
    }

    public void doNegativeClick() {
        pb = true;
    }

}
