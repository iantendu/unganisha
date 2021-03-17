package com.example.unganisha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FarmerRegisterActivity extends AppCompatActivity {

    Button registerButton;
    AutoCompleteTextView regionsDropdown;
    TextInputEditText fName, lName, phone, email, password;
    TextView errorText;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_register);

        mAuth = FirebaseAuth.getInstance();

        fName = findViewById(R.id.fname);
        lName = findViewById(R.id.lname);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        registerButton = findViewById(R.id.register);
        regionsDropdown = findViewById(R.id.region);
        errorText = findViewById(R.id.errorText);

        errorText.setVisibility(View.INVISIBLE);

        ArrayList<String> regions = new ArrayList<String>();
        regions.add("Kamakwa");
        regions.add("Karatina");
        regions.add("Kimathi");
        regions.add("Chaka");

        ArrayAdapter<String> regionsAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item, regions);
        regionsDropdown.setAdapter(regionsAdapter);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerFarmer();
            }
        });
    }

    private void registerFarmer() {
        String regionInput = regionsDropdown.getText().toString().trim();
        String fNameInput = fName.getText().toString().trim();
        String lNameInput = lName.getText().toString().trim();
        String phoneInput = phone.getText().toString().trim();
        String emailInput = email.getText().toString().trim();
        String passwordInput = password.getText().toString().trim();

        registerButton.setText("Working");
        registerButton.setEnabled(false);
        errorText.setVisibility(View.INVISIBLE);

        mAuth.createUserWithEmailAndPassword(emailInput, passwordInput).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Farmer farmer = new Farmer(fNameInput, lNameInput, phoneInput, emailInput, regionInput);

                    FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(farmer).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                registerButton.setText("Success");
                                Intent loginIntent =new Intent(getApplicationContext(),LoginActivity.class);
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