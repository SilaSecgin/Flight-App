package com.sila.flight_app.Admin;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sila.flight_app.Model.Airplanes;
import com.sila.flight_app.R;
import com.sila.flight_app.RecyclerViewItemClickListener;

import java.util.ArrayList;

public class AirplanesAdapterClass extends RecyclerView.Adapter<AirplanesAdapterClass.MyViewHolder> {

    ArrayList<Airplanes> list;
    private RecyclerViewItemClickListener recyclerViewItemClickListener;

    public AirplanesAdapterClass(ArrayList<Airplanes> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.airplanes_card_holder, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.position = i;
        // Integer.parseInt(list.get(i).getUserFlightsList().get(list.get(i).getUserFlightsList().size()).getTime())-Integer.parseInt(list.get(i).getUserFlightsList().get(0).getTime());
        myViewHolder.txt_airplanes_name.setText(list.get(i).getName());
        if (list.get(i).getDuration().contains("Gün")) {
            myViewHolder.txt_airplanes_duration.setBackgroundColor(Color.parseColor("#ff1d0b"));
            myViewHolder.txt_airplanes_duration.setText("BAKIM GEREKLI " + list.get(i).getDuration());
        } else {
            myViewHolder.txt_airplanes_duration.setBackgroundColor(Color.parseColor("#a9ff77"));
            myViewHolder.txt_airplanes_duration.setText(list.get(i).getDuration());
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_airplanes_name, txt_airplanes_duration;
        public int position = 0;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            txt_airplanes_name = itemView.findViewById(R.id.txt_airplanes_name);
            txt_airplanes_duration = itemView.findViewById(R.id.txt_airplanes_duration);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewItemClickListener != null) {
                        recyclerViewItemClickListener.onItemClick(itemView, position);
                    }
                }
            });
        }
    }

    public void setOnItemClickListener(RecyclerViewItemClickListener recyclerViewItemClickListener) {
        this.recyclerViewItemClickListener = recyclerViewItemClickListener;
    }
}
