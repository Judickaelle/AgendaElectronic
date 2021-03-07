package com.judickaelle.pelletier.agendaelectronique;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class Pop extends Activity {
    TextView tEvent;
    TextView tNameEvent;
    TextView tTimeEvent;
    TextView tPeopleEvent;

    private String event;
    private String nameEventPop;
    private String timeEventPop;
    private String peopleEventPop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_info);

        DisplayMetrics dimension = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dimension);

        int width = dimension.widthPixels;
        int height = dimension.heightPixels;

        getWindow().setLayout((int)(width*0.8), (int)(height*0.6));

        tNameEvent = findViewById(R.id.NameEvent);
        tTimeEvent = findViewById(R.id.TimeEvent);
        tPeopleEvent = findViewById(R.id.PeopleEvent);
        tEvent = findViewById(R.id.Event);

        event = MainActivity.getInstance().getEventSelectedDesc();

        nameEventPop = event.split("at")[0];
        timeEventPop = event.split("at")[1].split(" : ")[0];
        peopleEventPop = event.split("at")[1].split(" : ")[1];

        tEvent.setText(event);

        tTimeEvent.setText(timeEventPop);
        tNameEvent.setText(nameEventPop);
        tPeopleEvent.setText(peopleEventPop);
    }

}
