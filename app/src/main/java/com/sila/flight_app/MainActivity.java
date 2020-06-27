package com.sila.flight_app;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sila.flight_app.Model.Locations;

import java.util.ArrayList;

import static com.sila.flight_app.ui.logbook.LogbookFragment.userFlightsArrayList;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Bundle extras = getIntent().getExtras();
        String link = extras.getString("send_string");
        int position = Integer.parseInt(link);
        textView = findViewById(R.id.test);
        ArrayList<Locations> locationsArrayList = (ArrayList<Locations>) userFlightsArrayList.get(position).getUserFlightsList();








    }
}
