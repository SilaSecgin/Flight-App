package com.sila.flight_app.Admin;

import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sila.flight_app.Model.Airplanes;
import com.sila.flight_app.R;
import com.sila.flight_app.RecyclerViewItemClickListener;

import java.util.ArrayList;

public class AirplanesShowData extends AppCompatActivity {

    public static ArrayList<Airplanes> airplanesList;
    private RecyclerView.LayoutManager mLayoutManager;
    RecyclerView recyclerView;
    SearchView searchVie;
    DatabaseReference databaseReference;
    AirplanesAdapterClass adapterClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.airplanes_show_data);
        init();
        databaseReference = FirebaseDatabase.getInstance().getReference("Airplanes");
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        if (databaseReference != null) {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        airplanesList = new ArrayList<>();
                        for (DataSnapshot da : dataSnapshot.getChildren()) {

                            Airplanes airplanes = new Airplanes(
                                    da.getKey(),
                                    calculateTime(Integer.parseInt(da.child("duration").getValue().toString()) / 1000)

                            );

                            airplanesList.add(airplanes);
                        }
                    }
                    setRecylerViewAdapter();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getBaseContext(), "Hata oluştu", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (searchVie != null) {
            searchVie.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    search(s);
                    return false;
                }

            });
        }

    }

    public static String calculateTime(int seconds) {
        int sec = seconds % 60;
        int minutes = (seconds / 60) % 60;
        int hours = seconds / 3600;
        int day = 0;

        if (hours >= 24) {
            day = hours / 24;
            hours = hours - 24;
        }

        if (hours != 0) {
            if (day != 0) {
                return day + " Gün " + hours + " Saat " + minutes + " Dakika " + sec + " Saniye ";
            } else {
                return hours + " Saat " + minutes + " Dakika " + sec + " Saniye ";
            }
        } else if (minutes != 0) {
            return minutes + " Dakika " + sec + " Saniye ";
        } else {
            return sec + " Saniye ";
        }
    }

    private void setRecylerViewAdapter() {

        adapterClass = new AirplanesAdapterClass(airplanesList);

        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getBaseContext());

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterClass);

        adapterClass.setOnItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Toast.makeText(AirplanesShowData.this, position + "", Toast.LENGTH_SHORT).show();
                //String s = userFlightsArrayList.get(position).getFlihtsId();
                //Intent intent = new Intent(getContext(), MapsActivity.class);
                //intent.putExtra("send_string", s);
                //startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    private void init() {
        recyclerView = findViewById(R.id.rvAirplanes);
        searchVie = findViewById(R.id.searhViewAirplanes);
    }

    private void search(String str) {
        ArrayList<Airplanes> myList = new ArrayList<>();

        for (Airplanes object : airplanesList) {

            if (object.getName().toLowerCase().contains(str.toLowerCase()) || object.getDuration().toLowerCase().contains(str.toLowerCase())) {
                myList.add(object);
            }

        }

        adapterClass = new AirplanesAdapterClass(myList);
        recyclerView.setAdapter(adapterClass);

    }
}