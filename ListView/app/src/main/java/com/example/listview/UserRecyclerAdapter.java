package com.example.listview;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.ViewHolder> {

    List<User> users;
    IUserListener mListener;
    public UserRecyclerAdapter(List<User> users, IUserListener mListener) {
        this.users = users;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_card, parent, false);
        return new ViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = users.get(position);
        holder.userName.setText(user.getName());
        holder.userAge.setText(user.getAge() + " Years old");
        holder.user = users.get(position);
    }

    @Override
    public int getItemCount() {
        return this.users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView userName, userAge;
        User user;
        Button likeBtn;
        public ViewHolder(@NonNull View itemView, IUserListener mListener) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName);
            userAge = itemView.findViewById(R.id.userAge);
            likeBtn = itemView.findViewById(R.id.like);
            itemView.findViewById(R.id.user_card).setOnClickListener(view -> {
                Toast.makeText(itemView.getContext(), "Clicked" + user, Toast.LENGTH_SHORT).show();
            });

            likeBtn.setOnClickListener(view -> {
                mListener.gotoMainActivity(user);
            });
        }
    }

    public interface IUserListener {
        public void gotoMainActivity (User user);
    }
}
