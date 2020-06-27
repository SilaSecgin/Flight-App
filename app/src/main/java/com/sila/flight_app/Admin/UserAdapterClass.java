package com.sila.flight_app.Admin;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sila.flight_app.Model.Usernames;
import com.sila.flight_app.R;
import com.sila.flight_app.RecyclerViewItemClickListener;

import java.util.ArrayList;

public class UserAdapterClass extends RecyclerView.Adapter<UserAdapterClass.MyViewHolder> {

    ArrayList<Usernames> list;
    private RecyclerViewItemClickListener recyclerViewItemClickListener;

    public UserAdapterClass(ArrayList<Usernames> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.users_card_holder, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.position = i;
        // Integer.parseInt(list.get(i).getUserFlightsList().get(list.get(i).getUserFlightsList().size()).getTime())-Integer.parseInt(list.get(i).getUserFlightsList().get(0).getTime());
        myViewHolder.txt_user_names.setText(list.get(i).getUsername_key());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_user_names;
        public int position = 0;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            txt_user_names = itemView.findViewById(R.id.txt_user_names);
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
