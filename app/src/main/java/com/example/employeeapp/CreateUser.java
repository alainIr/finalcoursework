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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateUser extends AppCompatActivity {

    EditText full_name,emailUser,passwordUser,districtUser,departmentUser;
    Button createBtn;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;

//    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference myRef = database.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        mAuth = FirebaseAuth.getInstance();

        full_name = findViewById(R.id.fullName);
        emailUser = findViewById(R.id.email);
        passwordUser = findViewById(R.id.password);
        districtUser = findViewById(R.id.district);
        departmentUser = findViewById(R.id.department);
        createBtn = findViewById(R.id.submit);
        progressBar = findViewById(R.id.progressBar);


        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = full_name.getText().toString().trim();
                String email = emailUser.getText().toString().trim();
                String password = passwordUser.getText().toString().trim();
                String district = districtUser.getText().toString().trim();
                String department = departmentUser.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    emailUser.setError("Email is Required");
                }
                if(TextUtils.isEmpty(password)){
                    passwordUser.setError("Password is Required");
                }
                if(password.length()<6){
                    passwordUser.setError("Pasword must be >= 6");
                }

                progressBar.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(fullName,email,password,district,department);

                            FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressBar.setVisibility(View.INVISIBLE);
                                            startActivity(new Intent(getApplicationContext(), AdminDashboard.class));
                                            Toast.makeText(CreateUser.this, "Created an Employee successfully",
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }else{
                            Toast.makeText(CreateUser.this, "error"+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

    }
}