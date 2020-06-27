package com.sila.flight_app.ui.logbook;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sila.flight_app.Model.UserFlights;
import com.sila.flight_app.R;
import com.sila.flight_app.RecyclerViewItemClickListener;
import java.util.ArrayList;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.MyViewHolder> {

    ArrayList<UserFlights> list;
    private RecyclerViewItemClickListener recyclerViewItemClickListener;

    public AdapterClass(ArrayList<UserFlights> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_holder, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.position = i;
        // Integer.parseInt(list.get(i).getUserFlightsList().get(list.get(i).getUserFlightsList().size()).getTime())-Integer.parseInt(list.get(i).getUserFlightsList().get(0).getTime());
        myViewHolder.cFrom.setText(list.get(i).getFfrom());
        myViewHolder.cTo.setText(list.get(i).getTto());
        myViewHolder.cFirstPilot.setText("1. Pilot : " + list.get(i).getFirstPilot());
        myViewHolder.cSecondPilot.setText("2. Pilot : " + list.get(i).getSecondPilot());
        myViewHolder.cFlyName.setText("Uçak Kodu : " + list.get(i).getFlyName());
        myViewHolder.cStartTime.setText("Başlangıç : " + list.get(i).getStartDate());
        myViewHolder.cEndTime.setText("Bitiş : " + list.get(i).getEndDate());
        myViewHolder.cFlyTime.setText("Uçuş Süresi : " + list.get(i).getFlyTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView cFrom, cTo, cFirstPilot, cSecondPilot, cFlyName, cStartTime, cEndTime, cFlyTime;
        public int position = 0;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            cFrom = itemView.findViewById(R.id.cFrom);
            cTo = itemView.findViewById(R.id.cTo);
            cFirstPilot = itemView.findViewById(R.id.cFirstPilot);
            cSecondPilot = itemView.findViewById(R.id.cSecondPilot);
            cFlyName = itemView.findViewById(R.id.cFlyName);
            cStartTime = itemView.findViewById(R.id.cStartTime);
            cEndTime = itemView.findViewById(R.id.cEndTime);
            cFlyTime = itemView.findViewById(R.id.cFlyTime);
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
