package com.example.unganisha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;


public class SpecialistRegisterActivity extends AppCompatActivity {
    TextInputEditText fname, lname, phone, email, password, bio;
    AutoCompleteTextView region;
    Button registerButton;
    TextView errorText;
    TextInputEditText services;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialist_register);
        mAuth = FirebaseAuth.getInstance();
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        phone = findViewById(R.id.specialist_number);
        email = findViewById(R.id.specialist_email);
        region = findViewById(R.id.specialist_region);
        bio = findViewById(R.id.specialist_bio);
        password = findViewById(R.id.password);
        registerButton = findViewById(R.id.register);
        errorText = findViewById(R.id.errorText);
        services = findViewById(R.id.services);


        ArrayList<String> regions = new ArrayList<String>();
        regions.add("Kamakwa");
        regions.add("Karatina");
        regions.add("Kimathi");
        ArrayAdapter<String> regionsAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item, regions);
        region.setAdapter(regionsAdapter);



        errorText.setVisibility(View.INVISIBLE);



        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerSpecialist();
            }
        });
    }

    public void registerSpecialist() {
        String regionInput = region.getText().toString().trim();
        String fNameInput = fname.getText().toString().trim();
        String lNameInput = lname.getText().toString().trim();
        String phoneInput = phone.getText().toString().trim();
        String emailInput = email.getText().toString().trim();
        String inputBio = bio.getText().toString().trim();
        String passwordInput = password.getText().toString().trim();
        String profession = services.getText().toString().trim();


        registerButton.setText("Working");
        registerButton.setEnabled(false);
        errorText.setVisibility(View.INVISIBLE);

        mAuth.createUserWithEmailAndPassword(emailInput, passwordInput).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Specialist specialist = new Specialist(fNameInput, lNameInput, phoneInput, emailInput, regionInput, profession, inputBio);


                    FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(specialist).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                registerButton.setText("Success");
                                Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(loginIntent);
                                //redirect to login
                            } else {
                                errorText.setVisibility(View.VISIBLE);
                                errorText.setText("Error: " + task.getException().getMessage());
                                registerButton.setEnabled(true);
                            }
                        }
                    });

                } else {
                    errorText.setVisibility(View.VISIBLE);
                    errorText.setText("Error: " + task.getException().getMessage());
                    registerButton.setEnabled(true);
                }
            }
        });
    }
}