package com.sila.flight_app.Admin;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sila.flight_app.Model.UserProfile;
import com.sila.flight_app.R;
import com.sila.flight_app.RecyclerViewItemClickListener;

import java.util.ArrayList;

public class UserDetailAdapterClass extends RecyclerView.Adapter<UserDetailAdapterClass.MyViewHolder> {

    ArrayList<UserProfile> list;
    private RecyclerViewItemClickListener recyclerViewItemClickListener;

    public UserDetailAdapterClass(ArrayList<UserProfile> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.users_detail_card_holder, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.position = i;
        // Integer.parseInt(list.get(i).getUserFlightsList().get(list.get(i).getUserFlightsList().size()).getTime())-Integer.parseInt(list.get(i).getUserFlightsList().get(0).getTime());
        myViewHolder.pnamee.setText(list.get(i).getPname());
        myViewHolder.surname.setText(list.get(i).getSurname());
        myViewHolder.mail.setText(list.get(i).getMail());
        myViewHolder.phone.setText(list.get(i).getPhone());
        myViewHolder.username.setText(list.get(i).getUsername());
        myViewHolder.userid.setText(list.get(i).getUserId());
        myViewHolder.blood.setText(list.get(i).getBloodGroup());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView pnamee, surname, mail, phone, username, userid, blood;
        public int position = 0;

        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            pnamee = itemView.findViewById(R.id.pnamee);
            surname = itemView.findViewById(R.id.surname);
            mail = itemView.findViewById(R.id.mail);
            phone = itemView.findViewById(R.id.phone);
            username = itemView.findViewById(R.id.username);
            userid = itemView.findViewById(R.id.userid);
            blood = itemView.findViewById(R.id.blood);

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
