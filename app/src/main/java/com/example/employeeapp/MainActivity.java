package com.example.employeeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText emailLog,passwordLog;
    Button loginBtn;
    ProgressBar progressBar2;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailLog = findViewById(R.id.email);
        passwordLog = findViewById(R.id.password);
        loginBtn = findViewById(R.id.login);
        progressBar2 = findViewById(R.id.progressBar2);

        mAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailLog.getText().toString().trim();
                String password = passwordLog.getText().toString().trim();

                if(!email.contentEquals("admin@employee.com") && password.contentEquals("123three")){
                    emailLog.setError("Incorrect email!!");
                    passwordLog.setError("Incorrect Password!!");
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    emailLog.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    passwordLog.setError("Password is Required");
                    return;
                }
                if(password.length() < 6){
                    passwordLog.setError("Password must be >= 6 characters");
                    return;
                }
                progressBar2.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Logged in successfully", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
                    }else{
                        Toast.makeText(MainActivity.this, "Error!"+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        progressBar2.setVisibility(View.GONE);
                    }
                }
            });
            }
        });

    }
    public void openCreate(View view){
        Intent intent = new Intent(this, CreateUser.class);
        startActivity(intent);
    }
}