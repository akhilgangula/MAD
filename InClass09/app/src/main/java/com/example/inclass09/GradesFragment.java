package com.example.inclass09;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GradesFragment extends Fragment implements GradeCardAdapter.IGradeCard {
    List<Course> courseList;
    GradeCardAdapter adapter;
    IGradesAction mListener;

    public GradesFragment() {
        // Required empty public constructor
    }

    public GradesFragment(IGradesAction mListener) {
        this.mListener = mListener;
    }

    public static GradesFragment newInstance(IGradesAction mListener) {
        return new GradesFragment(mListener);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.grades_toolbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addBtn:
                mListener.onAddCourseClick();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private double[] getGPAHours() {
        double overall_hours = courseList.stream().parallel().mapToDouble(course -> course.hour).sum();
        double overall_gpa = overall_hours == 0.0 ? 4.0 : courseList.stream().parallel().mapToDouble(course -> Util.getGradeToInt(course.grade) * course.hour).sum() / overall_hours;
        return new double[]{overall_gpa, overall_hours};
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grades, container, false);
        getActivity().setTitle(R.string.grades_title);
        courseList = DBAdapter.getInstance().courseDao().getAll();
        setHasOptionsMenu(true);
        double[] data = getGPAHours();
        ((TextView) view.findViewById(R.id.gpa)).setText(String.format("%.2f", data[0]) + " " + getString(R.string.gpa));
        ((TextView) view.findViewById(R.id.hours)).setText(data[1] + " " + getString(R.string.hours));
        RecyclerView recyclerView = view.findViewById(R.id.recyler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new GradeCardAdapter(courseList, this);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                double[] data = getGPAHours();
                ((TextView) view.findViewById(R.id.gpa)).setText(String.format("%.2f", data[0]) + " " + getString(R.string.gpa));
                ((TextView) view.findViewById(R.id.hours)).setText(data[1] + " " + getString(R.string.hours));
            }
        });
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onGradeDelete(Course course) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.warn))
                .setMessage(getString(R.string.delete_message))
                .setNegativeButton(getString(R.string.cancel), (dialogInterface, i) -> dialogInterface.dismiss())
                .setPositiveButton(getString(R.string.ok), (dialogInterface, i) -> {
                    DBAdapter.getInstance().courseDao().delete(course);
                    int index = courseList.indexOf(course);
                    courseList.remove(index);
                    adapter.notifyItemRemoved(index);
                    dialogInterface.dismiss();
                });
        builder.create().show();

    }

    interface IGradesAction {
        void onAddCourseClick();

        void onGradeDelete();
    }
}