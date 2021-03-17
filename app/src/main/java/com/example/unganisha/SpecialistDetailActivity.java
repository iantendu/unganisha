package com.example.unganisha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SpecialistDetailActivity extends AppCompatActivity {
    TextView specialistName,specialistEmail,specialistNumber,specialistRegion,specialistBio,specialistServices;
    public void backToHome(View view) {
        Intent intent=new Intent(getApplicationContext(),FarmerHomeActivity.class);
        startActivity(intent);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialist_detail);
        specialistName = findViewById(R.id.specialist_name);
        specialistEmail = findViewById(R.id.specialist_email);
        specialistNumber = findViewById(R.id.specialist_number);
        specialistRegion = findViewById(R.id.specialist_region);
        specialistBio = findViewById(R.id.specialist_bio);
        specialistServices = findViewById(R.id.specialist_services);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent = getIntent();


        specialistName.setText(intent.getStringExtra("fname")+" "+intent.getStringExtra("lname"));
        specialistEmail.setText(intent.getStringExtra("email"));
        specialistNumber.setText(intent.getStringExtra("phone"));
        specialistRegion.setText(intent.getStringExtra("region"));
        specialistBio.setText(intent.getStringExtra("bio"));
        specialistServices.setText(intent.getStringExtra("services"));

    }




    }
