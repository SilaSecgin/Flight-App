package com.sila.flight_app.Admin;

import android.content.Intent;
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
import com.sila.flight_app.Model.Usernames;
import com.sila.flight_app.R;
import com.sila.flight_app.RecyclerViewItemClickListener;

import java.util.ArrayList;

public class UserShowData extends AppCompatActivity {


    public static ArrayList<Usernames> userAllLists;
    private RecyclerView.LayoutManager mmLayoutManager;
    RecyclerView recyclerView;
    SearchView searchVie;
    DatabaseReference databaseReference;
    UserAdapterClass adapterClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_show_data);
        init();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
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

                            Usernames usernames = new Usernames(
                                    da.getKey()
                            );

                            userAllLists.add(usernames);
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

    private void setRecylerViewAdapter() {

        adapterClass = new UserAdapterClass(userAllLists);


        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getBaseContext());

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterClass);


        adapterClass.setOnItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                //Toast.makeText(UserShowData.this, position + "", Toast.LENGTH_SHORT).show();
                String key = userAllLists.get(position).getUsername_key();
                Intent intent = new Intent(getBaseContext(), UserDetailShowData.class);
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
        searchVie = findViewById(R.id.searhViewUser);
    }

    private void search(String str) {
        ArrayList<Usernames> myList = new ArrayList<>();

        for (Usernames object : userAllLists) {

            if (object.getUsername_key().toLowerCase().contains(str.toLowerCase())) {
                myList.add(object);
            }

        }

        adapterClass = new UserAdapterClass(myList);
        recyclerView.setAdapter(adapterClass);

        adapterClass.setOnItemClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String key = null;
                int i = 0;

                for (Usernames object : userAllLists) {

                    for (int j = 0; j < myList.size(); j++) {
                        if (object.getUsername_key().toLowerCase().contains(myList.get(j).getUsername_key().toLowerCase())) {
                            key = object.getUsername_key();
                        }
                    }

                }


                //Toast.makeText(UserShowData.this, position + "", Toast.LENGTH_SHORT).show();
                //String key = userAllLists.get(position).getUsername_key();
                Intent intent = new Intent(getBaseContext(), UserDetailShowData.class);
                intent.putExtra("username_key", key);
                startActivity(intent);

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

    }
}