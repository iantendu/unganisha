package com.example.unganisha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SpecialistDetailActivity extends AppCompatActivity {
    TextView specialistName,specialistEmail,specialistNumber,specialistRegion,specialistBio,specialistServices,specialistCounty;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialist_detail);
        specialistName=findViewById(R.id.specialist_name);
        specialistEmail=findViewById(R.id.specialist_email);
        specialistNumber=findViewById(R.id.specialist_number);
        specialistRegion=findViewById(R.id.specialist_region);
        specialistBio=findViewById(R.id.specialist_bio);
        specialistServices=findViewById(R.id.specialist_services);
        specialistCounty=findViewById(R.id.specialist_county);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String RegisteredUserID = currentUser.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(RegisteredUserID);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                showData(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void showData(DataSnapshot dataSnapshot) {
        String fname = dataSnapshot.child("fname").getValue(String.class);
        String lname = dataSnapshot.child("lname").getValue(String.class);
        String email = dataSnapshot.child("email").getValue(String.class);
        String number= dataSnapshot.child("phone").getValue(String.class);
        String bio=dataSnapshot.child("bio").getValue(String.class);
        String profession = dataSnapshot.child("profession").getValue(String.class);
        String region = dataSnapshot.child("region").getValue(String.class);
        String county=dataSnapshot.child("county").getValue(String.class);
        specialistName.setText(fname+" "+lname);
        specialistEmail.setText(email);
        specialistNumber.setText(number);
        specialistRegion.setText(region);
        specialistBio.setText(bio);
        specialistServices.setText(profession);
        specialistCounty.setText(county);





    }



    }
