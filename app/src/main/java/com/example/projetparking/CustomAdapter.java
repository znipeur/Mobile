package com.example.projetparking;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        //inflate layout
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model_layout,parent,false);

        ViewHolder viewHolder = new ViewHolder(itemView);
        //handle item Click
        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {


                //show direction
                        String adresse = modelList.get(position).getAdresse();
                        Uri gmmIntentUri = Uri.parse("google.navigation:q="+adresse);
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW,gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        parkingActivity.startActivity(mapIntent);
                    }


            @Override
            public void onItemLongClick(View view, final int position) {
                //on Long Click

                //alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(parkingActivity);

                String[] options = {"Update", "Delete"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            //update
                            //get data
                            String id = modelList.get(position).getId();
                            String user = modelList.get(position).getUser();
                            String adresse = modelList.get(position).getAdresse();
                            Long capacite = modelList.get(position).getCapacite();

                            //intent
                            Intent i = new Intent(parkingActivity,HomeActivity.class);
                            //put data in Intent
                            i.putExtra("parkid",id);
                            i.putExtra("parkUser",user);
                            i.putExtra("parkAdresse",adresse);
                            i.putExtra("parkCapacite",capacite);

                            //start activity
                            parkingActivity.startActivity(i);


                        }
                    }
                });

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
