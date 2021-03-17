package com.example.unganisha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FarmerProfileActivity extends AppCompatActivity {
    TextView farmerName, farmerEmail, farmerNumber, farmerRegion;
    DatabaseReference mDatabase;
    Button updateActivityButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_profile);
       farmerName=findViewById(R.id.farmer_name);
       farmerEmail=findViewById(R.id.farmer_email);
       farmerNumber=findViewById(R.id.farmer_number);
       farmerRegion=findViewById(R.id.farmer_region);
       updateActivityButton=findViewById(R.id.farmer_update_info);


        updateActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent update=new Intent(getApplicationContext(),UpdateFarmerActivity.class);
                startActivity(update);
            }
        });

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
        String fname = dataSnapshot.child("fName").getValue(String.class);
        String lname = dataSnapshot.child("lName").getValue(String.class);
        String email = dataSnapshot.child("email").getValue(String.class);
        String number= dataSnapshot.child("phone").getValue(String.class);
        String region = dataSnapshot.child("region").getValue(String.class);
        farmerName.setText(fname+" "+lname);
        farmerEmail.setText(email);
        farmerNumber.setText(number);
        farmerRegion.setText(region);






    }


}