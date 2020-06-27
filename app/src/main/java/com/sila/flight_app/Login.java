package com.sila.flight_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sila.flight_app.Admin.Admin;
import com.sila.flight_app.Model.User;

import butterknife.ButterKnife;


public class Login extends AppCompatActivity {

    public EditText username, password;
    public Button login;
    public TextView forgotPassword;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private User user = new User();
    public static String USERNAME = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);

        ProgressDialog progressDialog = new ProgressDialog(Login.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Yükleniyor...");
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        // onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 300);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Hesabım Var");
        }

        init();

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = new User(username.getText().toString(), password.getText().toString());

                if (user.getUsername().isEmpty() || user.getPassword().isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Lütfen gerekli alanları doldurunuz!", Toast.LENGTH_SHORT).show();

                } else {

                    if (user.getUsername().equals("admin") && user.getPassword().equals("admin")) {

                        Intent i = new Intent(Login.this, Admin.class);
                        startActivity(i);
                    } else {
                        loginFunc();

                    }

                }
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (username.getText().toString().isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Lütfen kullanıcı adınızı doldurunuz!", Toast.LENGTH_SHORT).show();

                } else {

                    resetFunc();
                }
            }
        });
    }

    private void init() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        forgotPassword = findViewById(R.id.forgotPassword);
        login = findViewById(R.id.login);
    }

    private void loginFunc() {
        ProgressDialog progressDialog = new ProgressDialog(Login.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Giriş Yapılıyor...");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(user.getUsername() + "@gmail.com", user.getPassword()).addOnCompleteListener(Login.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            USERNAME = user.getUsername();
                            progressDialog.dismiss();
                            Toast.makeText(Login.this, "Giriş Başarılı", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(Login.this, HomePage.class);
                            startActivity(i);
                            finish();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(Login.this, "Kullanıcı Adı veya Şifre Hatalı", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void resetFunc() {
        /// bu username in mailini çek mail yolla. Mail kontrol var yok ona göre uyarı.
        mAuth.sendPasswordResetEmail(username.getText().toString() + "@gmail.com")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            username.setText("");
                            Toast.makeText(Login.this, "Şifreniz Gönderilmiştir.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}