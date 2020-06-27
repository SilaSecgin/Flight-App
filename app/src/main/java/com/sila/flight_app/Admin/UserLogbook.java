package com.sila.flight_app.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.sila.flight_app.MapsActivity;
import com.sila.flight_app.Model.Locations;
import com.sila.flight_app.Model.UserFlights;
import com.sila.flight_app.R;
import com.sila.flight_app.RecyclerViewItemClickListener;
import com.sila.flight_app.ui.logbook.AdapterClass;

import java.util.ArrayList;

public class UserLogbook extends AppCompatActivity {


    String TAG = "Firelog";
    DatabaseReference ref;
    public static ArrayList<UserFlights> userFlightsArrayList;
    RecyclerView recyclerView;
    SearchView searchVie;
    private RecyclerView.LayoutManager mLayoutManager;
    public ArrayList<Locations> myList_1 = new ArrayList<Locations>();
    AdapterClass adapterClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_logbook);

        Bundle extras = getIntent().getExtras();
        String id = extras.getString("username_key");


        ref = FirebaseDatabase.getInstance().getReference("Flights").child(id);
        recyclerView = findViewById(R.id.rv);
        searchVie = findViewById(R.id.searhView);


        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getBaseContext());
        recyclerView.setLayoutManager(mLayoutManager);

        if (ref != null) {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        userFlightsArrayList = new ArrayList<>();

                        for (DataSnapshot da : dataSnapshot.getChildren()) {
                            /// https://subscription.packtpub.com/book/web_development/9781788624718/1/ch01lvl1sec12/reading-and-writing-to-realtime-database
                            //Log.i(TAG, String.valueOf(da.getValue()));
                            //Log.i(TAG, String.valueOf(da.getValue(UserFlights.class)));
                            //list2.add (da.getValue (UserFlights.class));
                            myList_1 = (ArrayList<Locations>) da.child("userFlightsList").getValue();

                            Log.d(TAG, "User name: " + myList_1);
                            UserFlights userFlights = new UserFlights(
                                    da.child("flihtsId").getValue().toString(),
                                    da.child("ffrom").getValue().toString(),
                                    da.child("tto").getValue().toString(),
                                    da.child("startDate").getValue().toString(),
                                    da.child("endDate").getValue().toString(),
                                    da.child("flyName").getValue().toString(),
                                    da.child("firstPilot").getValue().toString(),
                                    da.child("secondPilot").getValue().toString(),
                                    da.child("flyTime").getValue().toString(),
                                    myList_1
                            );

                            userFlightsArrayList.add(userFlights);
                        }

                        setRecylerViewAdapter();


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    Toast.makeText(getBaseContext(), "DatabaseError", Toast.LENGTH_SHORT).show();
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

    private void setRecylerViewAdapter() {
        adapterClass = new AdapterClass(userFlightsArrayList);


        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getBaseContext());

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterClass);


        adapterClass.setOnItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


                String s = userFlightsArrayList.get(position).getFlihtsId();
                Intent intent = new Intent(getBaseContext(), MapsActivity.class);
                intent.putExtra("send_string", s);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }


    private void search(String str) {
        ArrayList<UserFlights> myList = new ArrayList<>();

        for (UserFlights object : userFlightsArrayList) {

            if (object.getFfrom().toLowerCase().contains(str.toLowerCase()) || object.getTto().toLowerCase().contains(str.toLowerCase())) {
                myList.add(object);
            }

        }

        adapterClass = new AdapterClass(myList);
        recyclerView.setAdapter(adapterClass);

        adapterClass.setOnItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                String s = null;

                for (UserFlights object : userFlightsArrayList) {

                    for (int j = 0; j < myList.size(); j++) {
                        if (object.getFfrom().toLowerCase().contains(myList.get(j).getFfrom().toLowerCase()) &&
                                object.getTto().toLowerCase().contains(myList.get(j).getTto().toLowerCase())) {
                            s = object.getFlihtsId();
                        }
                    }
                }

                Intent intent = new Intent(getBaseContext(), MapsActivity.class);
                intent.putExtra("send_string", s);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

    }
}
