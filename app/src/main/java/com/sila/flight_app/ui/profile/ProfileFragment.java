package com.sila.flight_app.ui.profile;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sila.flight_app.Login;
import com.sila.flight_app.Model.UserProfile;
import com.sila.flight_app.R;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    public String USERNAME = Login.USERNAME;
    public TextView name_surname, pusername;
    public EditText email, phone, bloodGroup;
    public Button update;
    public ArrayList<UserProfile> list;
    DatabaseReference databaseReference;
    UserProfile userProfile;
    ProgressDialog progressDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Yükleniyor...");
        progressDialog.show();

        name_surname = root.findViewById(R.id.name_surname);
        email = root.findViewById(R.id.email);
        phone = root.findViewById(R.id.phone);
        bloodGroup = root.findViewById(R.id.bloodGroup);
        update = root.findViewById(R.id.update);
        pusername = root.findViewById(R.id.pUsername);

        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(USERNAME);

        if (databaseReference != null) {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        list = new ArrayList<>();
                        for (DataSnapshot da : dataSnapshot.getChildren()) {

                            //String key = da.getKey();
                            //String value = dataSnapshot.child("username").getValue().toString();
                            //Toast.makeText(getContext(), value+"", Toast.LENGTH_SHORT).show();

                            userProfile = new UserProfile(

                                    dataSnapshot.child("userId").getValue().toString(),
                                    dataSnapshot.child("username").getValue().toString(),
                                    dataSnapshot.child("pname").getValue().toString(),
                                    dataSnapshot.child("surname").getValue().toString(),
                                    dataSnapshot.child("mail").getValue().toString(),
                                    dataSnapshot.child("phone").getValue().toString(),
                                    dataSnapshot.child("bloodGroup").getValue().toString()
                            );

                            list.add(userProfile);

                            name_surname.setText(userProfile.getPname() + " " + userProfile.getSurname());
                            email.setText(userProfile.getMail());
                            phone.setText(userProfile.getPhone());
                            bloodGroup.setText(userProfile.getBloodGroup());
                            pusername.setText(userProfile.getUsername());
                        }
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Hata oluştu", Toast.LENGTH_SHORT).show();
                }
            });
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    UserProfile customer = new UserProfile(userProfile.getUserId(), userProfile.getUsername(), userProfile.getPname(), userProfile.getSurname(), email.getText().toString(), phone.getText().toString(), bloodGroup.getText().toString());
                    databaseReference.setValue(customer);
                    Toast.makeText(getContext(), "Başarılı bir şekilde güncellendi.", Toast.LENGTH_SHORT).show();
                } catch (Exception exception) {
                    Toast.makeText(getContext(), "Güncelleme yapılırken bir hata oluştu." + exception, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return root;
    }
}