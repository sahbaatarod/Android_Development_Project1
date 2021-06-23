package com.example.firebaseapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.MyViewholder> {
    Context context;
    List<MessageModel> messageModels;
    ClientAdapter(Context context, List<MessageModel> messageModels) {
        this.context = context;
        this.messageModels = messageModels;
    }
    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adming_message_list,parent,false);
        return (new MyViewholder(view));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {
        MessageModel model = messageModels.get(position);
        holder.tv.setText(model.getMessage());
    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    class MyViewholder extends RecyclerView.ViewHolder {
        TextView  tv;
        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.adminmessagetv);
        }
    }
}
