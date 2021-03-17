package com.example.unganisha;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

      TextView name,region,services,phone;



    public MyViewHolder(@NonNull View itemView) {

        super(itemView);
        name=itemView.findViewById(R.id.spe_name);
        region=itemView.findViewById(R.id.spe_region);
        services=itemView.findViewById(R.id.spe_services);
        phone=itemView.findViewById(R.id.spe_contact);


    }
}
