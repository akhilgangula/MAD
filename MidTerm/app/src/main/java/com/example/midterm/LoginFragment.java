package com.example.midterm;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.midterm.response.AuthenticationFailedResponse;
import com.example.midterm.response.AuthenticationResponse;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    private static final String ARG_PARAM2 = "param2";

    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(IHome mListener) {
        LoginFragment fragment = new LoginFragment(mListener);
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        getActivity().setTitle(getString(R.string.login_title));
        view.findViewById(R.id.login_btn).setOnClickListener(view1 -> {
            String email = ((EditText)view.findViewById(R.id.login_email)).getText().toString();
            String password = ((EditText)view.findViewById(R.id.login_password)).getText().toString();
            if(email.isEmpty()) {
                AlertDialog.Builder builder =new AlertDialog.Builder(getContext());
                builder.setTitle(Constants.ERROR_TITLE)
                        .setMessage(getString(R.string.error_email))
                        .setPositiveButton(getString(R.string.ok),(dialogInterface,i)->{
                            dialogInterface.dismiss();
                        });
                builder.create().show();
                return;
            }
            if(password.isEmpty()) {
                AlertDialog.Builder builder =new AlertDialog.Builder(getContext());
                builder.setTitle(Constants.ERROR_TITLE)
                        .setMessage(getString(R.string.error_password))
                        .setPositiveButton(getString(R.string.ok),(dialogInterface,i)->{
                            dialogInterface.dismiss();
                        });
                builder.create().show();
                return;
            }
            Service.loginUser(new UserPOJO(email, password), new Handler(message -> {
                if(message.arg1 != 1) {
                    AlertDialog.Builder builder =new AlertDialog.Builder(getContext());
                    builder.setTitle(Constants.ERROR_TITLE)
                            .setMessage(((AuthenticationFailedResponse)message.obj).getMessage())
                            .setPositiveButton(getString(R.string.ok),(dialogInterface,i)->{
                                dialogInterface.dismiss();
                            });
                    builder.create().show();
                    return false;
                }
                mListener.launchList((AuthenticationResponse)message.obj);
                return false;
            }));
        });
        view.findViewById(R.id.create_btn).setOnClickListener(view1 -> {
            mListener.registerUser();
        });
        return view;
    }

    public LoginFragment(IHome mListener) {
        this.mListener = mListener;
    }

    IHome mListener;
    interface IHome {
        void registerUser();
        void launchList(AuthenticationResponse response);
    }
}