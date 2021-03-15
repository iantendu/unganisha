  package com.example.unganisha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class FarmerHomeActivity extends AppCompatActivity {
    Button viewProfile;

    AutoCompleteTextView servicesDropdown, locationDropdown;
    DatabaseReference ref;
    RecyclerView recyclerView;

    private FirebaseRecyclerOptions<specialist_card> options;
    private FirebaseRecyclerAdapter<specialist_card,MyViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_home);
        viewProfile=findViewById(R.id.profile);

        ref= FirebaseDatabase.getInstance().getReference().child("users");

        recyclerView=findViewById(R.id.recycler1);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



        Toolbar appToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(appToolbar);

        servicesDropdown = findViewById(R.id.services_autocomplete_menu);
        locationDropdown = findViewById(R.id.location_autocomplete_menu);

        ArrayList<String> services = new ArrayList<String>();
        services.add("Vertenary");
        services.add("Vertena");
        services.add("Vertenar");

        ArrayList<String> locations = new ArrayList<String>();
        locations.add("Kamakwa");
        locations.add("Kimathi");
        locations.add("Karatina");

        ArrayAdapter<String> servicesAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item, services);
        servicesDropdown.setAdapter(servicesAdapter);

        ArrayAdapter<String> locationAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item, locations);
        locationDropdown.setAdapter(locationAdapter);
        viewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profile=new Intent(getApplicationContext(),FarmerProfileActivity.class);
                startActivity(profile);
            }
        });
        options=new FirebaseRecyclerOptions.Builder<specialist_card>().setQuery(ref.orderByChild("profile").equalTo("specialist"),specialist_card.class).build();
        adapter=new FirebaseRecyclerAdapter<specialist_card, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull specialist_card model) {

                    holder.name.setText(model.getFname() + " " + model.getLname());
                    holder.services.setText("Services: "+model.getProfession());
                    holder.region.setText("Region: "+model.getRegion());
                    holder.phone.setText("Phone: "+model.getPhone());





            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.specialist,parent,false);
                return new MyViewHolder(v);

            }
        };


        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }



}