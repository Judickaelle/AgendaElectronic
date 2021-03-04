package com.judickaelle.pelletier.agendaelectronique;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private MyDB dbCalendar;

    private CalendarView calendarView;
    private String selectedDate;
    private SQLiteDatabase sqLiteDatabase;
    private TextView textViewEvent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        calendarView = findViewById(R.id.calendarView);
        textViewEvent = findViewById(R.id.textViewEvent);
        Button ButtonAdd = findViewById(R.id.buttonAdd);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
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

        ButtonAdd.setOnClickListener(new View.OnClickListener() { //bouton pour aller vers la page AddEvent
            @Override
            public void onClick(View v) {
                Intent t new Intent(MainActivity.this, AddEvent.class);
                Bundle bundle = new Bundle(); // essayer d'envoer la date selectionnée vers le fragment
                bundle.putString("Date", selectedDate);
                // set Fragmentclass Arguments
                AddEvent fragmentAddEvent = new AddEvent();
                fragmentAddEvent.setArguments(bundle);
                startActivityForResult(t, 0);
            }
        });
    }



    public void ReadDatabase(View view){
        String query = "Select NameEvent from EventCalendar where Date =" + selectedDate;
        try{
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            cursor.moveToFirst(); // faire une boucle pour afficher tout les événements de la journée
            textViewEvent.setText(cursor.getString(0));  //a changer en list view
        }catch (Exception e){
            e.printStackTrace();
            textViewEvent.setText("nothing for the moment"); //si pas d'événement dans la journée alors l'affichage est nothing
        }
    }
}