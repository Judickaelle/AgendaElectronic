package com.judickaelle.pelletier.agendaelectronique;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddEventFullDayActivity extends AppCompatActivity {

    private EditText editTextNameEvent;
    private static String NameEvent;
    private static String DateEvent;
    private boolean familyEvent = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_full_day);
        editTextNameEvent = findViewById(R.id.editTextEventName);
    }

    // ***************** GETTER et SETTER ********************
    public static String getNameEvent() {
        return NameEvent;
    }
    public void setNameEvent(String NameEvent) {
        AddEventFullDayActivity.NameEvent = NameEvent;}
    public static String getDateEvent() {
        return DateEvent;
    }
    public void setDateEvent(String DateEvent) {
        AddEventFullDayActivity.DateEvent = DateEvent;}

    public void InsertDatabase(View view){
        setNameEvent(editTextNameEvent.getText().toString());
        setDateEvent(MainActivity.getSelectedDate());
        ContentValues contentValues = new ContentValues();
        contentValues.put("Date", getDateEvent());
        contentValues.put("NameEvent", getNameEvent());
        contentValues.put("FamilyEvent", familyEvent);
        contentValues.put("DescriptionEvent", "all the day");

        MainActivity.getInstance().InsertEventDataBase(contentValues,getDateEvent(),getNameEvent(), "NoTime");

        finish();
    }

    public void onCheckboxClicked(View view){
        familyEvent = true;
    }
}