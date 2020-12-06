package com.example.employeeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity {
    EditText full_name,emailUser,passwordUser,districtUser,departmentUser;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        mAuth = FirebaseAuth.getInstance();

        full_name = findViewById(R.id.fullName);
        emailUser = findViewById(R.id.email);
        passwordUser = findViewById(R.id.password);
        districtUser = findViewById(R.id.district);
        departmentUser = findViewById(R.id.department);

        FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String fName = snapshot.getValue(String.class);
//                Log.d(TAG, "Value is :"+ fName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//                Log.w(TAG, "Failed to read Value.", error.toException());
            }
        });
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        Toast.makeText(this, "Logged Out !", Toast.LENGTH_LONG).show();
        finish();
    }
}