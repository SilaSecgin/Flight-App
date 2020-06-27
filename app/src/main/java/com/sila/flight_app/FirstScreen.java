package com.sila.flight_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FirstScreen extends AppCompatActivity {

    private Button loginScreen, registerScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_screen);

        init();
        loginScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FirstScreen.this, Login.class);
                startActivity(i);
            }
        });

        registerScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FirstScreen.this, Register.class);
                startActivity(i);
            }
        });
    }

    private void init() {
        loginScreen = findViewById(R.id.loginScreen);
        registerScreen = findViewById(R.id.registerScreen);
    }
}
