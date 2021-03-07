package com.judickaelle.pelletier.agendaelectronique;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddEventActivity extends Activity {

    private EditText editTextNameEvent;
    private EditText editTextTime;
    private EditText editTextTime_min;
    private EditText editTextPeople;
    private Button buttonSave;
    private boolean familyEvent = false;

    private static String NameEvent;
    private static String People;
    private static String TimeEvent;
    private static String DateEvent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        editTextNameEvent = findViewById(R.id.editTextEventName);
        editTextTime = findViewById(R.id.editTextTime);
        editTextTime_min = findViewById(R.id.editTextTime_min);
        editTextPeople = findViewById(R.id.editTextPeople);

        buttonSave = findViewById(R.id.buttonSave);
    }
    // ***************** GETTER et SETTER ********************
    public static String getNameEvent() {
        return NameEvent;
    }
    public void setNameEvent(String NameEvent) {this.NameEvent = NameEvent;}
    public static String getPeople() {
        return People;
    }
    public void setPeople(String People) {this.People = People;}
    public static String getTimeEvent() {
        return TimeEvent;
    }
    public void setTimeEvent(String TimeEvent) {this.TimeEvent = TimeEvent;}
    public static String getDateEvent() {
        return DateEvent;
    }
    public void setDateEvent(String DateEvent) {this.DateEvent = DateEvent;}

    public void InsertDatabase(View view){

        setNameEvent(editTextNameEvent.getText().toString());
        setPeople(editTextPeople.getText().toString());
        setTimeEvent(editTextTime.getText().toString() + ":" + editTextTime_min.getText().toString() );
        setDateEvent(MainActivity.getSelectedDate());

        ContentValues contentValues = new ContentValues();
        contentValues.put("Date", getDateEvent());
        contentValues.put("NameEvent", getNameEvent());
        contentValues.put("Guest", getPeople());
        contentValues.put("DescriptionEvent", getTimeEvent());

        contentValues.put("FamilyEvent", familyEvent);

        //appeler fonction test
        MainActivity.getInstance().InsertEventDataBase(contentValues,getDateEvent(),getNameEvent(), getTimeEvent());

        finish();
    }

    public void onCheckboxClicked(View view){
        familyEvent = true;
    }
}