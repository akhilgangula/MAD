package com.example.midterm;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.midterm.response.AuthenticationResponse;
import com.example.midterm.response.PostPOJO;

import java.util.List;

public class PaginationAdapter extends RecyclerView.Adapter<PaginationAdapter.PageViewHolder>{
    List<Integer> pageList;
    AuthenticationResponse auth;
    public PaginationAdapter(List<Integer> pageList, AuthenticationResponse authInfo, IPage mListener) {
        this.auth = authInfo;
        this.pageList = pageList;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public PageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pagination_scroll, parent, false);
        return new PageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PageViewHolder holder, int position) {
        holder.pagination_btn.setText(Integer.toString(pageList.get(position)));
        holder.pagination_btn.setOnClickListener(v -> {
            mListener.gotoPage(position+1);
        });
    }

    @Override
    public int getItemCount() {
        return pageList.size();
    }
    class PageViewHolder extends RecyclerView.ViewHolder {
        Button pagination_btn;
        public PageViewHolder(@NonNull View itemView) {
            super(itemView);
            pagination_btn = itemView.findViewById(R.id.pagination_btn);
        }
    }

    IPage mListener;
    interface IPage {
        void gotoPage(int page);
    }
}

