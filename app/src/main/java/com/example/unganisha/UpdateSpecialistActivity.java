package com.example.unganisha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UpdateSpecialistActivity extends AppCompatActivity {
    TextInputEditText fname, lname, phone, email, bio, profession;
    AutoCompleteTextView region;
    Button updateButton;
    DatabaseReference mDatabase;
    String RegisteredUserID;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_specialist);

        RegisteredUserID = currentUser.getUid();
        fname = (TextInputEditText) findViewById(R.id.sfirstname);
        lname = (TextInputEditText) findViewById(R.id.slastname);
        phone = (TextInputEditText) findViewById(R.id.snumber);
        email = (TextInputEditText) findViewById(R.id.semail);
        region = (AutoCompleteTextView) findViewById(R.id.region);
        bio = (TextInputEditText) findViewById(R.id.sbio);
        profession = (TextInputEditText) findViewById(R.id.sservices);
        updateButton = (Button) findViewById(R.id.supdateButton);

        ArrayList<String> regions = new ArrayList<String>();
        regions.add("Kamakwa");
        regions.add("Karatina");
        regions.add("Kimathi");

        ArrayAdapter<String> regionsAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item, regions);
        region.setAdapter(regionsAdapter);

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
        String specialist_fname = dataSnapshot.child("fname").getValue(String.class);
        String specialist_lname = dataSnapshot.child("lname").getValue(String.class);
        String specialist_email = dataSnapshot.child("email").getValue(String.class);
        String specialist_number = dataSnapshot.child("phone").getValue(String.class);
        String specialist_bio = dataSnapshot.child("bio").getValue(String.class);
        String specialist_profession = dataSnapshot.child("profession").getValue(String.class);
        String specialist_region = dataSnapshot.child("region").getValue(String.class);


        fname.setText(specialist_fname);
        lname.setText(specialist_lname);
        email.setText(specialist_email);
        phone.setText(specialist_number);
        bio.setText(specialist_bio);
        profession.setText(specialist_profession);
        region.setText(specialist_region);
    }

    private void updateSpecialist() {
        String regionInput = region.getText().toString().trim();
        String fNameInput = fname.getText().toString().trim();
        String lNameInput = lname.getText().toString().trim();
        String phoneInput = phone.getText().toString().trim();
        String emailInput = email.getText().toString().trim();
        String inputBio = bio.getText().toString().trim();
        String inputProfession = bio.getText().toString().trim();





        mDatabase.child("fname").setValue(fNameInput);
        mDatabase.child("lname").setValue(lNameInput);
        mDatabase.child("phone").setValue(phoneInput);
        mDatabase.child("profession").setValue(inputProfession);
        mDatabase.child("region").setValue(regionInput);
        mDatabase.child("email").setValue(emailInput);
        mDatabase.child("bio").setValue(inputBio);

        Intent intentSpecialistProf=new Intent(getApplicationContext(),SpecialistHomeActivity.class);
        //Toast.makeText(getApplicationContext(),"Successfully Updated",Toast.LENGTH_LONG).show();
        startActivity(intentSpecialistProf);
    }
}