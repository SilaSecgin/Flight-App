package com.sila.flight_app;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sila.flight_app.Model.Locations;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public DatabaseReference databaseReferenceCustomers;
    public ArrayList<Locations> list;
    Locations locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Bundle extras = getIntent().getExtras();
        String id = extras.getString("send_string");

        databaseReferenceCustomers = FirebaseDatabase.getInstance().getReference("Flights").child(Login.USERNAME).child(id).child("userFlightsList");

        if (databaseReferenceCustomers != null) {
            databaseReferenceCustomers.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        list = new ArrayList<>();

                        for (DataSnapshot da : dataSnapshot.getChildren()) {

                            locations = new Locations(

                                    da.child("latitude").getValue().toString(),
                                    da.child("longitude").getValue().toString(),
                                    da.child("time").getValue().toString()

                            );
                            list.add(locations);
                        }

                        mMap = googleMap;

                        for (int i = 0; i < list.size(); i++) {
                            LatLng sydney = new LatLng(Double.parseDouble(list.get(i).getLatitude()), Double.parseDouble(list.get(i).getLongitude()));
                            mMap.addMarker(new MarkerOptions().position(sydney).title(calculate(list.get(i).getTime())));
                        }

                        // Add a marker in Sydney and move the camera
                        LatLng sydneyLast = new LatLng(Double.parseDouble(list.get(list.size() - 1).getLatitude()), Double.parseDouble(list.get(list.size() - 1).getLongitude()));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydneyLast));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    Toast.makeText(getBaseContext(), "Hata oluştu", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private String calculate(String time) {

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm a  dd-MM-yyyy");

        long tsSecond = Long.parseLong(time);
        Timestamp ts = new Timestamp(tsSecond);
        Date sunriseDate = new Date(ts.getTime() + TimeUnit.HOURS.toMillis(3));
        return formatter.format(sunriseDate);
    }
}