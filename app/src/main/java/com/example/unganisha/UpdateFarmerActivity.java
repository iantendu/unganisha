package com.example.unganisha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateFarmerActivity extends AppCompatActivity {
    TextInputEditText fname,lname,email,number,region;
    Button updateButton;
    DatabaseReference mDatabase;
    String RegisteredUserID;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_farmer);
        RegisteredUserID = currentUser.getUid();
        fname=findViewById(R.id.farmer_fname);
        lname=findViewById(R.id.farmer_lname);
        email=findViewById(R.id.farmer_email);
        number=findViewById(R.id.farmer_number);
        region=findViewById(R.id.farmer_region);
        updateButton=findViewById(R.id.updateButton);




        mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(RegisteredUserID);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                showData(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//                Log.i("Info", error.getMessage());
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                updateSpecialist();

            }
        });

    }
    private void showData(DataSnapshot dataSnapshot) {
        String farmer_fname = dataSnapshot.child("fname").getValue(String.class);
        String farmer_lname = dataSnapshot.child("lname").getValue(String.class);
        String farmer_email = dataSnapshot.child("email").getValue(String.class);
        String farmer_number = dataSnapshot.child("phone").getValue(String.class);
        String farmer_region = dataSnapshot.child("region").getValue(String.class);

        fname.setText(farmer_fname);
        lname.setText(farmer_lname);
        email.setText(farmer_email);
        number.setText(farmer_number);
        region.setText(farmer_region);

    }

    private void updateSpecialist() {
        String regionInput = region.getText().toString().trim();
        String fNameInput = fname.getText().toString().trim();
        String lNameInput = lname.getText().toString().trim();
        String phoneInput = number.getText().toString().trim();
        String emailInput = email.getText().toString().trim();






        mDatabase.child("fName").setValue(fNameInput);
        mDatabase.child("lName").setValue(lNameInput);
        mDatabase.child("phone").setValue(phoneInput);
        mDatabase.child("region").setValue(regionInput);
        mDatabase.child("email").setValue(emailInput);


        Intent intentSpecialistFam=new Intent(getApplicationContext(),FarmerProfileActivity.class);
        //Toast.makeText(getApplicationContext(),"Successfully Updated",Toast.LENGTH_LONG).show();
        startActivity(intentSpecialistFam);
    }
}