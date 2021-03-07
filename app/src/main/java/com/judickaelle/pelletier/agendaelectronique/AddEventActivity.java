package com.judickaelle.pelletier.agendaelectronique;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

public class AddEventActivity extends Activity {

    private EditText editTextNameEvent;
    private EditText editTextPeople;
    private int valuePicker1;
    private NumberPicker HourPicker;
    private String[] HourVals;
    private int valuePicker2;
    private NumberPicker MinutePicker;
    private String[] MinuteVals;
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
        editTextPeople = findViewById(R.id.editTextPeople);
        HourPicker = findViewById(R.id.NumberPickerHour);
        MinutePicker = findViewById(R.id.NumberPickerMinute);

        HourPicker.setMaxValue(23);
        HourPicker.setMinValue(0);
        HourVals = new String[]{"00","01","02", "03","04","05","06","07","08", "09", "10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
        HourPicker.setDisplayedValues(HourVals);
        HourPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                valuePicker1 = HourPicker.getValue();
            }
        });


        MinutePicker.setMaxValue(59);
        MinutePicker.setMinValue(0);
        MinuteVals = new String[]{"00","01","02", "03","04","05","06","07","08", "09", "10","11","12","13","14","15","16","17","18","19","20","21","22","23",
                "24","25","26","27","28", "29","30","31","32","33","34","35","36","37","33","39","40","41","42","43",
                "44","45","46","47","48", "49", "50","51","52","53","54","55","56","57","58","59"};
        MinutePicker.setDisplayedValues(MinuteVals);
        MinutePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                valuePicker2 = MinutePicker.getValue();
            }
        });
    }

    // ***************** GETTER et SETTER ********************
    public static String getNameEvent() {return NameEvent; }
    public void setNameEvent(String NameEvent) {AddEventActivity.NameEvent = NameEvent;}
    public static String getPeople() {return People; }
    public void setPeople(String People) {AddEventActivity.People = People;}
    public static String getTimeEvent() {return TimeEvent; }
    public void setTimeEvent(String TimeEvent) {AddEventActivity.TimeEvent = TimeEvent;}
    public static String getDateEvent() {return DateEvent; }
    public void setDateEvent(String DateEvent) {AddEventActivity.DateEvent = DateEvent;}

    public void InsertDatabase(View view){setNameEvent(editTextNameEvent.getText().toString());
        setPeople(editTextPeople.getText().toString());
        setTimeEvent( HourVals[valuePicker1] + ":" + MinuteVals[valuePicker2]);
        setDateEvent(MainActivity.getSelectedDate());

        ContentValues contentValues = new ContentValues();
        contentValues.put("Date", getDateEvent());
        contentValues.put("NameEvent", getNameEvent());
        contentValues.put("Guest", getPeople());
        contentValues.put("DescriptionEvent", getTimeEvent());

        contentValues.put("FamilyEvent", familyEvent);

        MainActivity.getInstance().InsertEventDataBase(contentValues,getDateEvent(),getNameEvent(), getTimeEvent());

        finish();
    }

    public void onCheckboxClicked(View view){
        familyEvent = true;
    }
}