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
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {

    private static MainActivity instance;
    private static String eventSelectedDesc;
    private static String selectedDate;

    private MyDB dbCalendar;
    private CalendarView calendarView;
    private ListView eventListView;
    private SQLiteDatabase sqLiteDatabase;
    private Cursor cursor;

    ArrayList<String> eventList=new ArrayList<String>();
    ArrayAdapter<String> adapter;

    //****************** GETTER et SETTER ********************
    public static String getSelectedDate() {
        return selectedDate;
    }
    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
    }
    public String getEventSelectedDesc() {
        return eventSelectedDesc;
    }
    public static MainActivity getInstance() {
        return instance;
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

        adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                eventList);
        eventListView.setAdapter(adapter);
        instance = this;

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                eventList.clear();
                selectedDate = Integer.toString(year) + Integer.toString(month) + Integer.toString(dayOfMonth);
                ReadDatabase(view);
            }
        });

        try{
            dbCalendar = new MyDB(this, "CalendarDatabase", null, 1); //initialisation de la BDD
            sqLiteDatabase = dbCalendar.getWritableDatabase();
        }catch(Exception e){
            e.printStackTrace();
        }

        //button to join the AddEventActivity
        ButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddEventActivity.class));
            }
        });

        //button to join AddEventFullDayActivity
        ButtonAddfullday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddEventFullDayActivity.class));
            }
        });

        //onclick on an item
        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent pop = new Intent(MainActivity.this, Pop.class);
                        eventSelectedDesc = eventList.get(position);
                        startActivity(new Intent(MainActivity.this,Pop.class));
                    }
                }
        );
    }

    //***********************ALERT POPUP DUPLICATE***************************
    void showDialog() {
        DialogFragment newFragment = AlertDialogFragment.newInstance(R.string.warning_dialog_title);
        newFragment.show(getSupportFragmentManager(), "Alert");
    }

    //************************INSERTION IN THE DB*******************************
    public void InsertEventDataBase(ContentValues contentValues, String dateToCompare, String nameEventToCompare){
        cursor = sqLiteDatabase.rawQuery("Select Date, NameEvent from EventCalendar", null);
        cursor.moveToFirst();

        Conflit conflit = new Conflit(false);

        while(!cursor.isAfterLast()){
            //String eventNOM = cursor.getString((cursor.getColumnIndex("NameEvent")));
            if(cursor.getString((cursor.getColumnIndex("Date"))).equals(dateToCompare)&& cursor.getString(1).equals(nameEventToCompare)) {
                //if (cursor.getString(1).equals(nameEventToCompare)) {
                    showDialog();
                  //  break;
           }
            cursor.moveToNext();
        }

        if(conflit.isPb() == false){
            sqLiteDatabase.insert("EventCalendar", null, contentValues);
        }
    }

    //************************READ THE DB*****************************
    public void ReadDatabase(View view){
        String query = "Select NameEvent, Guest, DescriptionEvent, FamilyEvent Desc from EventCalendar where Date =" + selectedDate;
        try{
            cursor = sqLiteDatabase.rawQuery(query, null);
            cursor.moveToFirst(); // faire une boucle pour afficher tout les événements de la journée

            String name ="ah";
            String time ="hmm";
            while(!cursor.isAfterLast()){
                if(cursor.getInt(3)==1){
                    eventList.add(cursor.getString(cursor.getColumnIndex("NameEvent"))
                            + " at "
                            + cursor.getString(2)
                            + " : "
                            //+ cursor.getString(cursor.getColumnIndex("Guest"))
                            + "Family event"
                    );  //a changer en list view
                }else{
                    eventList.add(cursor.getString(cursor.getColumnIndex("NameEvent"))
                            + " at "
                            + cursor.getString(2)
                            + " : "
                            + cursor.getString(cursor.getColumnIndex("Guest"))
                            //+ ", personal event"
                    );  //a changer en list view
                }

                cursor.moveToNext();
            }
            eventListView.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
            eventList.add("nothing for the moment"); //si pas d'événement dans la journée alors l'affichage est nothing
            eventListView.setAdapter(adapter);
        }
    }
}