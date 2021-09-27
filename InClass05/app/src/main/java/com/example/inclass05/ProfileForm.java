package com.example.inclass05;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileForm#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileForm extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileForm() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileForm.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileForm newInstance(String param1, String param2) {
        ProfileForm fragment = new ProfileForm();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_form, container, false);
        view.findViewById(R.id.submitBtn).setOnClickListener(view1 -> {
            String firstName = ((EditText)view.findViewById(R.id.firstName)).getText().toString();
            String lastName = ((EditText)view.findViewById(R.id.lastName)).getText().toString();
            String email = ((EditText)view.findViewById(R.id.email)).getText().toString();
            String age = ((EditText)view.findViewById(R.id.age)).getText().toString();
            if(firstName.isEmpty()) {
                Toast.makeText(getActivity(),"Enter a valid First name", Toast.LENGTH_SHORT).show();
                return;
            } else if(lastName.isEmpty()){
                Toast.makeText(getActivity(),"Enter a valid Last name", Toast.LENGTH_SHORT).show();
                return;
            } else if(email.isEmpty()) {
                Toast.makeText(getActivity(),"Enter a valid Email", Toast.LENGTH_SHORT).show();
                return;
            } else if(age.isEmpty()){
                Toast.makeText(getActivity(),"Enter a valid Age", Toast.LENGTH_SHORT).show();
                return;
            }
            User user = new User(firstName, lastName, email, Integer.parseInt(age));
            mListener.submitProfile(user);
        });
        return  view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IProfileListener) {
            mListener = (IProfileListener) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement IProfileListener");
        }
    }

    IProfileListener mListener;
    public interface IProfileListener {
        void submitProfile(User user);
    }
}