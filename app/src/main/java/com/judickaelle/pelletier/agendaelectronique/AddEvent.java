package com.judickaelle.pelletier.agendaelectronique;

import android.content.ContentValues;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddEvent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEvent extends Fragment {

    private EditText editText;

    editText = findViewById(R.id.editTextEventName);

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddEvent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddEvent.
     */
    // TODO: Rename and change types and number of parameters
    public static AddEvent newInstance(String param1, String param2) {
        AddEvent fragment = new AddEvent();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_event, container, false);
    }

    public void InsertDatabase(View view){
        ContentValues contentValues = new ContentValues();
        contentValues.put("Date", Date);
        contentValues.put("NameEvent", editText.getText().toString());
        sqLiteDatabase.insert("EventCalendar", null, contentValues); //trouver un moyen d'écrire sur la bdd d'ici
    }
}