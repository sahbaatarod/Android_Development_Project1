package com.example.firebaseapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class AdminMessageAdapter extends RecyclerView.Adapter<AdminMessageAdapter.AdminViewModel> {
    List<MessageModel> messagelist;
    Context context;
    public AdminMessageAdapter(List<MessageModel> messagelist, Context context) {
        this.context = context;
        this.messagelist = messagelist;
    }

    @NonNull
    @Override
    public AdminViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.adming_message_list,parent,false);
        return (new AdminViewModel(view));
    }

    @Override
    public void onBindViewHolder(@NonNull AdminViewModel holder, int position) {
        MessageModel model = messagelist.get(position);
        holder.messagetv.setText(model.getMessage());
    }

    @Override
    public int getItemCount() {
        return messagelist.size();
    }

    class AdminViewModel extends RecyclerView.ViewHolder {
        TextView messagetv;
        public AdminViewModel(@NonNull View itemView) {
            super(itemView);
            messagetv = itemView.findViewById(R.id.adminmessagetv);
        }
    }
}
