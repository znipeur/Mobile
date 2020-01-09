package com.example.projetparking;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessageViewHolder extends RecyclerView.ViewHolder {
    TextView userTv,messageTv;
    View mView;


    public MessageViewHolder(@NonNull View itemView) {
        super(itemView);

        mView = itemView;

        //item click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v,getAdapterPosition());
            }
        });

        //item Long click listener
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClickListener.onItemLongClick(v,getAdapterPosition());
                return true;
            }
        });

        //initialize views with model layout
        //userTv = itemView.findViewById(R.id.userMessage);
        messageTv = itemView.findViewById(R.id.message);

    }

    private MessageViewHolder.ClickListener mClickListener;
    //interface click listener
    public interface ClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
}
    public void setOnClickListener(MessageViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }
}
