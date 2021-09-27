package com.example.homework02;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.ViewHolder> {
    List<DataServices.User> users;
    public UserRecyclerAdapter(List<DataServices.User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataServices.User user = users.get(position);
        holder.userName.setText(user.name);
        holder.userState.setText(user.state);
        holder.userAge.setText(user.age + " Years Old");
        holder.userRelation.setText(user.group);
        holder.userPic.setImageResource(user.gender.equals("Male") ? R.mipmap.male_icon : R.mipmap.female_icon);
    }

    @Override
    public int getItemCount() {
        return this.users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView userName, userAge, userState, userRelation;
        private ImageView userPic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name);
            userAge = itemView.findViewById(R.id.user_age);
            userState = itemView.findViewById(R.id.user_state);
            userRelation = itemView.findViewById(R.id.user_relation);
            userPic = itemView.findViewById(R.id.user_pic);
        }
    }
}
