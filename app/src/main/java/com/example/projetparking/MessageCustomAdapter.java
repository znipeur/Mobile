package com.example.projetparking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MessageCustomAdapter extends RecyclerView.Adapter<ViewHolder> {
    MessageActivity messageActivity;
    FirebaseFirestore db;
    List<Message> messageList;

    public MessageCustomAdapter(MessageActivity messageActivity, List<Message> messageList) {
        this.messageActivity = messageActivity;
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_layout,parent,false);

        MessageViewHolder messageViewHolder = new MessageViewHolder(itemView);
        //handle item Click
        messageViewHolder.setOnClickListener(new MessageViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {


            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //bind Views / set data
        holder.mUserTv.setText(messageList.get(position).getEmailSender());
        holder.mAdresseTv.setText(messageList.get(position).getMessage());


    }

    @Override
    public int getItemCount() {
        return  messageList.size();
    }
}
