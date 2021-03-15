package com.example.unganisha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    Button loginButton;
    TextView registerFarmerButton, registerSpecialistButton, errorText;
    TextInputEditText email, password;

    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();


        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        errorText = findViewById(R.id.errorText);
        loginButton = findViewById(R.id.loginButton);
        registerFarmerButton = findViewById(R.id.textFarmer);
        registerSpecialistButton = findViewById(R.id.textSpecialist);

        Intent toFarmerHome = new Intent(getApplicationContext(), FarmerHomeActivity.class);
        Intent toFarmerRegister = new Intent(getApplicationContext(), FarmerRegisterActivity.class);
        Intent toSpecialistRegister = new Intent(getApplicationContext(), SpecialistRegisterActivity.class);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(toFarmerHome);
                signIn();
            }
        });

        registerFarmerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(toFarmerRegister);
            }
        });

        registerSpecialistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(toSpecialistRegister);
            }
        });
    }

    private void signIn() {
        String emailText = email.getText().toString().trim();
        String passwordText = password.getText().toString().trim();

        loginButton.setText("Working");
        loginButton.setEnabled(false);
        errorText.setVisibility(View.INVISIBLE);

        mAuth.signInWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            loginButton.setText("Redirecting");


                            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                            String RegisteredUserID = currentUser.getUid();
                            mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(RegisteredUserID);
                            mDatabase.addValueEventListener(new ValueEventListener(){

                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String userType = snapshot.child("profile").getValue().toString();
                                    if (userType.equals("specialist")) {
                                        Intent specialistIntent = new Intent(LoginActivity.this,SpecialistHomeActivity.class);
                                        specialistIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(specialistIntent);
                                        finish();
                                    }
                                    else if(userType.equals("farmer")){
                                        Intent farmerIntent = new Intent(LoginActivity.this, FarmerHomeActivity.class);
                                        farmerIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(farmerIntent);
                                        finish();

                                    }
                                    else{
                                        Toast.makeText(LoginActivity.this, "Failed Login. Please Try Again", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }

                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


                        } else {
                            errorText.setVisibility(View.VISIBLE);
                            errorText.setText("Error: " + task.getException().getMessage());
                            loginButton.setEnabled(true);
                        }
                    }
                });


    }
}