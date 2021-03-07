package com.judickaelle.pelletier.agendaelectronique;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddEventFullDayActivity extends AppCompatActivity {

    private EditText editTextNameEvent;
    private Button buttonSave;


    private static String NameEvent;
    private static String DateEvent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_full_day);

        editTextNameEvent = findViewById(R.id.editTextEventName);

        buttonSave = findViewById(R.id.buttonSave);
    }

    public static String getNameEvent() {
        return NameEvent;
    }

    public void setNameEvent(String NameEvent) {this.NameEvent = NameEvent;}

    public static String getDateEvent() {
        return DateEvent;
    }

    public void setDateEvent(String DateEvent) {this.DateEvent = DateEvent;}

    public void InsertDatabase(View view){

        setNameEvent(editTextNameEvent.getText().toString());

        setDateEvent(MainActivity.getSelectedDate());

        ContentValues contentValues = new ContentValues();
        contentValues.put("Date", getDateEvent());
        contentValues.put("NameEvent", getNameEvent());                 // + " at " + editTextTime.getText() + " : " + editTextPeople.getText());

        //appeler fonction test

        MainActivity.getInstance().InsertEventDataBase(contentValues,getDateEvent(),getNameEvent(), "NoTime");

        finish();
    }

}