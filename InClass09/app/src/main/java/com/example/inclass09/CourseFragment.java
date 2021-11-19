package com.example.inclass09;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CourseFragment extends Fragment {

    public CourseFragment() {
        // Required empty public constructor
    }

    public CourseFragment(IAddCourse mListener) {
        this.mListener = mListener;
    }

    public static CourseFragment newInstance(IAddCourse mListener) {
        CourseFragment fragment = new CourseFragment(mListener);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course, container, false);
        getActivity().setTitle("Add Course");
        ((RadioGroup)view.findViewById(R.id.grade_group)).check(R.id.grade_a);
        view.findViewById(R.id.addSubmitBtn).setOnClickListener(view1 -> {
            String courseName = ((TextView) view.findViewById(R.id.add_course_name)).getText().toString();
            String courseNumber = ((TextView) view.findViewById(R.id.add_course_number)).getText().toString();
            String creditHours = ((TextView) view.findViewById(R.id.add_credit_hours)).getText().toString();
            RadioGroup radioGroup = view.findViewById(R.id.grade_group);
            int selectedId = radioGroup.getCheckedRadioButtonId();
            if (courseName.isEmpty()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(getString(R.string.error))
                        .setMessage(getString(R.string.error_course_name))
                        .setPositiveButton(getString(R.string.ok), (dialogInterface, i) -> dialogInterface.dismiss());
                builder.create().show();
                return;
            }
            if (courseNumber.isEmpty()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(getString(R.string.error))
                        .setMessage(getString(R.string.error_course_number))
                        .setPositiveButton(getString(R.string.ok), (dialogInterface, i) -> dialogInterface.dismiss());
                builder.create().show();
                return;
            }
            if (creditHours.isEmpty() || Integer.parseInt(creditHours) <= 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(getString(R.string.error))
                        .setMessage(getString(R.string.error_credit_hours))
                        .setPositiveButton(getString(R.string.ok), (dialogInterface, i) -> dialogInterface.dismiss());
                builder.create().show();
                return;
            }
            String grade = ((RadioButton) radioGroup.findViewById(selectedId)).getText().toString();
            DBAdapter.getInstance().courseDao().insertAll(new Course(courseNumber, grade, courseName, Integer.parseInt(creditHours)));
            mListener.onCourseAdd();

        });
        view.findViewById(R.id.addCancelBtn).setOnClickListener(view1 -> mListener.onCancelAdd());
        return view;
    }

    IAddCourse mListener;
    interface IAddCourse {
        void onCourseAdd();
        void onCancelAdd();
    }
}