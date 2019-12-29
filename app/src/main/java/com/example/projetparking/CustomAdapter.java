package com.example.projetparking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {
    ShowParkingActivity parkingActivity;
    List<Model> modelList;
    Context context;

    public CustomAdapter(ShowParkingActivity parkingActivity, List<Model> modelList) {
        this.parkingActivity = parkingActivity;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model_layout,parent,false);

        ViewHolder viewHolder = new ViewHolder(itemView);
        //handle item Click
        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {


                //show data
                String adresse = modelList.get(position).getAdresse();
                Toast.makeText(parkingActivity, adresse, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //bind Views / set data
        holder.mUserTv.setText(modelList.get(position).getUser());
        holder.mAdresseTv.setText(modelList.get(position).getAdresse());
        holder.mCapaciteTv.setText(modelList.get(position).getCapacite().toString());
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}
