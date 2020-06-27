package com.sila.flight_app.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.sila.flight_app.R;

public class Admin extends AppCompatActivity {

    public Button btn_user, btn_airplane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);

        init();

        btn_airplane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AirplanesShowData.class);
                startActivity(intent);
            }
        });

        btn_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), UserShowData.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        btn_user = findViewById(R.id.btn_user);
        btn_airplane = findViewById(R.id.btn_airplane);
    }
}