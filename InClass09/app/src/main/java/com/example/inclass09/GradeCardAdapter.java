package com.example.inclass09;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GradeCardAdapter extends RecyclerView.Adapter<GradeCardAdapter.ViewHolder> {

    private List<Course> courseList;

    public GradeCardAdapter(List<Course> courseList, IGradeCard mListener) {
        this.courseList = courseList;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grade_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.courseId.setText(courseList.get(position).cid);
        holder.courseName.setText(courseList.get(position).title);
        holder.courseGrade.setText(courseList.get(position).grade);
        holder.creditHours.setText(Integer.toString(courseList.get(position).hour));
        Course course = courseList.get(position);
        holder.deleteBtn.setOnClickListener(view -> mListener.onGradeDelete(course));
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView courseName, courseId, courseGrade, creditHours;
        ImageButton deleteBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            courseId = itemView.findViewById(R.id.list_course_id);
            courseName = itemView.findViewById(R.id.list_course_name);
            courseGrade = itemView.findViewById(R.id.list_grade);
            creditHours = itemView.findViewById(R.id.list_credit_hour);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);

        }
    }
    IGradeCard mListener;
    interface IGradeCard {
        void onGradeDelete(Course course);
    }
}
