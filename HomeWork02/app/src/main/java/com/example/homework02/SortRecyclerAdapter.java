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
import java.util.List;

public class SortRecyclerAdapter extends RecyclerView.Adapter<SortRecyclerAdapter.ViewHolder> {
    List<String> criteria = Arrays.asList(Utils.AGE, Utils.NAME, Utils.STATE);
    IFilterSort mListener;
    List<DataServices.User> users;
    Config config;
    public SortRecyclerAdapter(List<DataServices.User> users, Config config, IFilterSort mListener) {
        this.mListener = mListener;
        this.users = users;
        this.config = config;
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
        String sortBy = criteria.get(position);
        holder.sortCriteria.setText(sortBy);
        holder.asc.setOnClickListener(view -> {
            config.setSortBy(sortBy).setOrder(true);
            List<DataServices.User> users = Utils.manipulate(config.getFilterBy(), config.getSortBy(), config.isOrder());
            mListener.applyConfig(users, config);
        });
        holder.desc.setOnClickListener(view -> {
            config.setSortBy(sortBy).setOrder(false);
            List<DataServices.User> users = Utils.manipulate(config.getFilterBy(), config.getSortBy(), config.isOrder());
            mListener.applyConfig(users, config);
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
