package com.example.homework02;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortRecyclerAdapter extends RecyclerView.Adapter<SortRecyclerAdapter.ViewHolder> {
    List<String> criteria = Arrays.asList("Age", "Name", "State");
    SortFragment.ISort mListener;
    List<DataServices.User> users;
    public SortRecyclerAdapter(List<DataServices.User> users, SortFragment.ISort mListener) {
        this.mListener = mListener;
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sort_item, parent, false);
        return new SortRecyclerAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String sort = criteria.get(position);
        holder.sortCriteria.setText(sort);
        Comparator comparator = (user, t1) -> {
            switch (sort) {
                case "Age":
                    return ((DataServices.User)user).age - ((DataServices.User)t1).age;
                case "Name":
                    return ((DataServices.User)user).name.compareTo(((DataServices.User)t1).name);
                default:
                    return  ((DataServices.User)user).state.compareTo(((DataServices.User)t1).state);
            }
        };
        holder.asc.setOnClickListener(view -> {
            Collections.sort(users, comparator);
            mListener.sort(users);
        });
        holder.desc.setOnClickListener(view -> {
            users.sort(comparator.reversed());
            mListener.sort(users);
        });
    }

    @Override
    public int getItemCount() {
        return criteria.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView sortCriteria;
        private ImageButton asc, desc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sortCriteria = itemView.findViewById(R.id.sort_criteria);
            asc = itemView.findViewById(R.id.ascImageButton);
            desc = itemView.findViewById(R.id.descImageButton);
        }
    }
}
