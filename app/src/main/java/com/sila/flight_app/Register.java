package com.sila.flight_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sila.flight_app.Model.User;
import com.sila.flight_app.Model.UserProfile;

public class Register extends AppCompatActivity {

    public Button register;
    public EditText username, password, againPassword, name, surname, mail;
    public CheckBox contract;
    public TextView textContact;
    private FirebaseAuth mAuth;
    private User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        ProgressDialog progressDialog = new ProgressDialog(Register.this);
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
            getSupportActionBar().setTitle("Hesap Oluştur");
        }

        init();
        mAuth = FirebaseAuth.getInstance();

        textContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopUpClass popUpClass = new PopUpClass();
                popUpClass.showPopupWindow(v);
            }
        });
                
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user = new User(username.getText().toString(), password.getText().toString());
                if (user.getUsername().isEmpty() || user.getPassword().isEmpty() || !contract.isChecked()) {
                    Toast.makeText(getApplicationContext(), "Lütfen gerekli alanları doldurunuz!", Toast.LENGTH_SHORT).show();
                } else {

                    if (password.getText().toString().equals(againPassword.getText().toString())) {
                        registerFunc();
                    } else {
                        Toast.makeText(Register.this, "Şifreler uyuşmuyor.", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    private void registerFunc() {
        ProgressDialog progressDialog = new ProgressDialog(Register.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Üye olunuyor...");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(user.getUsername() + "@gmail.com", user.getPassword())
                .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            DatabaseReference databaseReferenceCustomers = FirebaseDatabase.getInstance().getReference("Users");

                            String id = databaseReferenceCustomers.push().getKey();
                            UserProfile userProfile = new UserProfile(id, user.getUsername(), name.getText().toString(), surname.getText().toString(), mail.getText().toString(), "", "");
                            databaseReferenceCustomers.child(user.getUsername()).setValue(userProfile);
                            progressDialog.dismiss();
                            Toast.makeText(Register.this, "Başarılı bir şekilde kayıt olundu.", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(Register.this, Login.class);
                            startActivity(i);
                            finish();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Lütfen başka bir kullanıcı adı giriniz..", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void init() {
        register = findViewById(R.id.register);
        username = findViewById(R.id.rusername);
        password = findViewById(R.id.rpassword);
        againPassword = findViewById(R.id.rrpassword);
        contract = findViewById(R.id.contract);
        textContact = findViewById(R.id.textContact);
        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        mail = findViewById(R.id.mail);


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