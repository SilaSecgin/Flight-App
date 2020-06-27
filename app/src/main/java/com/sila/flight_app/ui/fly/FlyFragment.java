package com.sila.flight_app.ui.fly;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sila.flight_app.Login;
import com.sila.flight_app.Model.Locations;
import com.sila.flight_app.Model.UserFlights;
import com.sila.flight_app.R;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import static android.content.Context.LOCATION_SERVICE;

public class FlyFragment extends Fragment {

    public Timer timer;
    public TimerTask timerTask;
    final Handler handler = new Handler();

    public static String USERNAME = Login.USERNAME;
    public String id, ffrom = "Istanbul", tto = "Ankara", fflyName = "Alfa", firstPilott, secondPilott, flyTime = "0";
    public long calculate = 0;
    private String[] fromCity = {"Ankara", "Balıkesir", "Bursa", "Eskişehir", "Kocaeli", "Istanbul", "Izmir"};
    private String[] toCity = {"Ankara", "Balıkesir", "Bursa", "Eskişehir", "Kocaeli", "Istanbul", "Izmir"};
    private String[] flyName = {"Alfa", "Charlie", "Tango", "Romeo", "India"};

    public Spinner spinner, spinner2, spinner3;
    public Button ready, stop;
    public EditText firstPilot, secondPilot;
    public CheckBox checkbox1, checkbox2, checkbox3, checkbox4, checkbox5, checkbox6, checkbox7;

    private ArrayAdapter<String> dataAdapterForPlanors, dataAdapterForPlanors2, dataAdapterForPlanors3;
    public ArrayList<Locations> myList_1 = new ArrayList<Locations>();

    public DatabaseReference databaseReferenceCustomers;
    public UserFlights userFlights;

    public LocationManager locationManager;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_fly, container, false);
        spinner = root.findViewById(R.id.spinner);
        spinner2 = root.findViewById(R.id.spinner2);
        spinner3 = root.findViewById(R.id.spinner3);
        ready = root.findViewById(R.id.ready);
        stop = root.findViewById(R.id.stop);
        firstPilot = root.findViewById(R.id.firstPilot);
        secondPilot = root.findViewById(R.id.secondPilot);
        checkbox1 = root.findViewById(R.id.checkBox);
        checkbox2 = root.findViewById(R.id.checkBox2);
        checkbox3 = root.findViewById(R.id.checkBox3);
        checkbox4 = root.findViewById(R.id.checkBox4);
        checkbox5 = root.findViewById(R.id.checkBox5);
        checkbox6 = root.findViewById(R.id.checkBox6);
        checkbox7 = root.findViewById(R.id.checkBox7);

        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);


        dataAdapterForPlanors = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, fromCity);
        dataAdapterForPlanors2 = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, toCity);
        dataAdapterForPlanors3 = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, flyName);
        dataAdapterForPlanors.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterForPlanors2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterForPlanors3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapterForPlanors);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // Toast.makeText(getContext(), fromCity[position], Toast.LENGTH_LONG).show();
                ffrom = fromCity[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner2.setAdapter(dataAdapterForPlanors2);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // Toast.makeText(getContext(), toCity[position], Toast.LENGTH_LONG).show();
                tto = toCity[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner3.setAdapter(dataAdapterForPlanors3);


        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //Toast.makeText(getContext(), flyName[position], Toast.LENGTH_LONG).show();

                fflyName = flyName[position];

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        ready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (firstPilot.getText().toString().isEmpty() || secondPilot.getText().toString().isEmpty() || !checkbox1.isChecked() || !checkbox2.isChecked() || !checkbox3.isChecked()
                        || !checkbox4.isChecked() || !checkbox5.isChecked() || !checkbox6.isChecked() || !checkbox7.isChecked()) {
                    Toast.makeText(getContext(), "Lütfen bütün alanları doldurunuz.", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(getContext(), "Konumlarınız kaydediliyor.", Toast.LENGTH_SHORT).show();

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa");
                    Date oldDate = new Date();
                    String format = simpleDateFormat.format(new Date(oldDate.getTime() + TimeUnit.HOURS.toMillis(3)));

                    firstPilott = firstPilot.getText().toString();
                    secondPilott = secondPilot.getText().toString();

                    databaseReferenceCustomers = FirebaseDatabase.getInstance().getReference("Flights").child(USERNAME);
                    id = databaseReferenceCustomers.push().getKey();

                    userFlights = new UserFlights(id, ffrom, tto, format, "", fflyName, firstPilott, secondPilott, flyTime, myList_1);
                    databaseReferenceCustomers.child(id).setValue(userFlights);

                    getDuration(userFlights.getFlyName());

                    ready.setEnabled(false);
                    ready.setClickable(false);
                    ready.setVisibility(View.GONE);
                    stop.setVisibility(View.VISIBLE);
                    stop.setEnabled(true);
                    stop.setClickable(true);
                    startTimer();
                }

            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //myList_1.add(new Locations("32.342", "40.221"));
                //myList_1.add(new Locations("34.342", "40.021"));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa");
                Date oldDate = new Date();
                String format = simpleDateFormat.format(new Date(oldDate.getTime() + TimeUnit.HOURS.toMillis(3)));
                calculate = 0;
                calculate = System.currentTimeMillis() - Long.parseLong(userFlights.getUserFlightsList().get(0).getTime());
                int ii = (int) (calculate / 1000);
                flyTime = calculateTime(ii);
                // databaseReference.child("duration").setValue(duration + (int) calculate);
                setDuration((int) calculate);

                userFlights = new UserFlights(id, userFlights.getFfrom(), userFlights.getTto(), userFlights.getStartDate(), format, userFlights.getFlyName(), firstPilott, secondPilott, flyTime, myList_1);
                databaseReferenceCustomers.child(id).setValue(userFlights);
                stoptimertask();

                secondPilot.setText("");
                firstPilot.setText("");

                ready.setEnabled(true);
                ready.setClickable(true);
                ready.setVisibility(View.VISIBLE);
                stop.setEnabled(false);
                stop.setClickable(false);
                stop.setVisibility(View.GONE);

                checkbox1.setChecked(false);
                checkbox2.setChecked(false);
                checkbox3.setChecked(false);
                checkbox4.setChecked(false);
                checkbox5.setChecked(false);
                checkbox6.setChecked(false);
                checkbox7.setChecked(false);

            }
        });
        return root;
    }

    DatabaseReference databaseReference;
    int duration = 0;

    private void getDuration(String airplanesName) {

        databaseReference = FirebaseDatabase.getInstance().getReference("Airplanes").child(airplanesName);

        if (databaseReference != null) {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot da : dataSnapshot.getChildren()) {
                            duration = Integer.parseInt(dataSnapshot.child("duration").getValue().toString());
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getContext(), "Hata oluştu", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setDuration(int timeSecond) {
        databaseReference.child("duration").setValue(duration + timeSecond);
    }

    public static String calculateTime(int seconds) {
        int sec = seconds % 60;
        int minutes = (seconds / 60) % 60;
        int hours = seconds / 3600;

        if (hours != 0) {
            return hours + " Saat " + minutes + " Dakika " + sec + " Saniye ";
        } else if (minutes != 0) {
            return minutes + " Dakika " + sec + " Saniye ";
        } else {
            return sec + " Saniye ";
        }
    }

    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, after the first 1500ms the TimerTask will run every 10000ms
        timer.schedule(timerTask, 1500, 10000); //
    }

    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    int i = 0;
    private String latitude, longitude;

    public void initializeTimerTask() {

        timerTask = new TimerTask() {
            public void run() {

                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {
                        //get the current timeStamp

                        locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);


                        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                            OnGPS();
                        } else {
                            getLocation();
                        }


                        // Toast.makeText(getContext(), "Çalıştı" + latitude + "dsa" + longitude, Toast.LENGTH_SHORT).show();

                    }
                });
            }


        };
    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            LocationListener loc_listener = new LocationListener() {

                public void onLocationChanged(Location l) {
                }

                public void onProviderEnabled(String p) {
                }

                public void onProviderDisabled(String p) {
                }

                public void onStatusChanged(String p, int status, Bundle extras) {
                }
            };

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, loc_listener);
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                String time = String.valueOf(timestamp.getTime());
                Log.i("KONUM", latitude + longitude);
                myList_1.add(new Locations(latitude, longitude, time));
                //Toast.makeText(getContext(), "latitude :"+latitude+ "longitude :"+longitude, Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getContext(), "Konum bulunamadı.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}