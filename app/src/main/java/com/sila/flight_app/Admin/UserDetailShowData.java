package com.sila.flight_app.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.sila.flight_app.Login;
import com.sila.flight_app.Model.UserProfile;
import com.sila.flight_app.R;
import com.sila.flight_app.RecyclerViewItemClickListener;

import java.util.ArrayList;

public class UserDetailShowData extends AppCompatActivity {


    public static ArrayList<UserProfile> userAllLists;
    private RecyclerView.LayoutManager mmLayoutManager;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    UserDetailAdapterClass adapterClass;
    public static String key = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_detail_show);
        init();
        Bundle extras = getIntent().getExtras();
        String id = extras.getString("username_key");
        key = id;
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(id);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mmLayoutManager = new LinearLayoutManager(getBaseContext());
        recyclerView.setLayoutManager(mmLayoutManager);


        if (databaseReference != null) {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        userAllLists = new ArrayList<>();
                        for (DataSnapshot da : dataSnapshot.getChildren()) {

                            UserProfile userprofile = new UserProfile(
                                    dataSnapshot.child("userId").getValue().toString(),
                                    dataSnapshot.child("username").getValue().toString(),
                                    dataSnapshot.child("pname").getValue().toString(),
                                    dataSnapshot.child("surname").getValue().toString(),
                                    dataSnapshot.child("mail").getValue().toString(),
                                    dataSnapshot.child("phone").getValue().toString(),
                                    dataSnapshot.child("bloodGroup").getValue().toString()

                            );

                            userAllLists.add(userprofile);
                            break;
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
    }

    private void setRecylerViewAdapter() {

        adapterClass = new UserDetailAdapterClass(userAllLists);


        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getBaseContext());

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterClass);


        adapterClass.setOnItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Login.USERNAME = key;
                Intent intent = new Intent(getBaseContext(), UserLogbook.class);
                intent.putExtra("username_key", key);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    private void init() {
        recyclerView = findViewById(R.id.rvUser);
    }

}