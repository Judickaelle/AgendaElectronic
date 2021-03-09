package com.judickaelle.pelletier.agendaelectronique;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {

    private static MainActivity instance;

    private static String eventSelectedDesc;
    private static String selectedDate;

    public static Conflit conflit = new Conflit(false, null);

    private MyDB dbCalendar;
    private CalendarView calendarView;
    private ListView eventListView;
    private SQLiteDatabase sqLiteDatabase;
    private Cursor cursor;

    ArrayList<String> eventList=new ArrayList<>();
    ArrayAdapter<String> adapter;

    private int count = 0;

    //****************** GETTER et SETTER ********************
    public static String getSelectedDate() {
        return selectedDate;
    }
    public void setSelectedDate(String selectedDate) {
        MainActivity.selectedDate = selectedDate;}
    public String getEventSelectedDesc() {
        return eventSelectedDesc;
    }

    public static MainActivity getInstance() {
        return instance;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }

    //***********************PRINCIPAL CODE*****************************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //declaration of variable and attribution with the layout
        eventListView = (ListView) findViewById(R.id.eventList);
        calendarView = findViewById(R.id.calendarView);
        Button ButtonAdd = findViewById(R.id.buttonAdd);
        Button ButtonAddfullday = findViewById(R.id.buttonAddfullday);

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                eventList);
        eventListView.setAdapter(adapter);
        instance = this;

        calendarView = findViewById(R.id.calendarView);
        ReadDatabase(calendarView);

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            eventList.clear();
            setSelectedDate(Integer.toString(year) + Integer.toString(month) + Integer.toString(dayOfMonth));
            ReadDatabase(view);
        });

        try{
            dbCalendar = new MyDB(this, "CalendarDatabase", null, 1); //DB initialisation
            sqLiteDatabase = dbCalendar.getWritableDatabase();
        }catch(Exception e){
            e.printStackTrace();
        }

        //button to join the AddEventActivity
        ButtonAdd.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddEventActivity.class)));

        //button to join AddEventFullDayActivity
        ButtonAddfullday.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddEventFullDayActivity.class)));

        //onclick on an item
        eventListView.setOnItemClickListener(
                (parent, view, position, id) -> {
                    Intent pop = new Intent(MainActivity.this, Pop.class);
                    eventSelectedDesc = eventList.get(position);
                    startActivity(new Intent(MainActivity.this,Pop.class));
                }
        );
    }

    //***********************ALERT POPUP DUPLICATE***************************
    void showDialog() {
        count +=1;
        if(count==1){
            DialogFragment newFragment = AlertDialogFragment.newInstance(R.string.warning_dialog_title);
            final FragmentManager fragmentManager = this.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(newFragment, "Alert").commitAllowingStateLoss();
        }

    }

    //************************INSERTION IN THE DB*******************************
    public void InsertEventDataBase(ContentValues contentValues, String dateToCompare, String nameEventToCompare, String timeToCompare){
        cursor = sqLiteDatabase.rawQuery("Select Date, NameEvent, DescriptionEvent from EventCalendar", null);
        cursor.moveToFirst();

        boolean isConflit = false;

        conflit.setPb(true);
        conflit.setContent(contentValues);
        count = 0;
        while(!cursor.isAfterLast()){
            String eventNOM = cursor.getString((cursor.getColumnIndex("NameEvent")));
            if(cursor.getString((cursor.getColumnIndex("Date"))).equals(dateToCompare)) {
                if (cursor.getString(1).equals(nameEventToCompare) || cursor.getString(2).equals(timeToCompare)) {

                    showDialog();
                    isConflit = true;
                    break;
                }else {
                    conflit.setPb(false);
                }
            }
            cursor.moveToNext();
        }
        if(!isConflit){
            sqLiteDatabase.insert("EventCalendar", null, contentValues);
            eventList.clear();
            ReadDatabase(calendarView);
        }
    }

    public void InsertWithoutConflit(ContentValues contentValues) {
        sqLiteDatabase.insert("EventCalendar", null, contentValues);
        eventList.clear();
        ReadDatabase(calendarView);
    }

    //************************READ THE DB*****************************
    public void ReadDatabase(View view){
        String query = "Select NameEvent, Guest, DescriptionEvent, FamilyEvent Desc from EventCalendar where Date =" + getSelectedDate();
        try{
            cursor = sqLiteDatabase.rawQuery(query, null);
            cursor.moveToFirst();
            String time = "All the day";
            String people = "Everybody";

            while(!cursor.isAfterLast()){

                if(cursor.getString(2)!=null && !cursor.getString(2).isEmpty()){
                    time = cursor.getString(2);
                }else{
                    time = "All the day";
                }
                if(cursor.getString(cursor.getColumnIndex("Guest"))!=null && !cursor.getString(cursor.getColumnIndex("Guest")).isEmpty()){
                    people = cursor.getString(cursor.getColumnIndex("Guest"));
                }else{
                    people = "Everybody";
                }

                if(cursor.getInt(3)==1){
                    //Family Event
                    eventList.add(cursor.getString(cursor.getColumnIndex("NameEvent"))
                            + "\n"
                            + "at "
                            + time
                            + " for "
                            + "Family event"
                    );
                }else{
                    //Personal event
                    eventList.add(cursor.getString(cursor.getColumnIndex("NameEvent"))
                                    + "\n"
                                    + "at "
                                    + time
                                    + " for "
                                    + people
                    );
                }
                cursor.moveToNext();
            }
            eventListView.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
            eventListView.setAdapter(adapter);
        }
    }
}