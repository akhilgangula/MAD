package com.example.class08;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {


    // TODO: Rename and change types of parameters
    private ILogin mListener;
    private String mParam2;
    private FirebaseAuth mAuth;
    public LoginFragment(ILogin mListener) {
        this.mListener = mListener;
    }

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(ILogin mListener) {
        LoginFragment fragment = new LoginFragment(mListener);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        getActivity().setTitle(getString(R.string.title_login));
        view.findViewById(R.id.login_btn).setOnClickListener(view1 -> {
            String userName = ((EditText) view.findViewById(R.id.login_email)).getText().toString();
            String password = ((EditText) view.findViewById(R.id.login_password)).getText().toString();
            if (userName.isEmpty()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(getString(R.string.error))
                        .setMessage(getString(R.string.error_username))
                        .setPositiveButton(getString(R.string.ok),(dialogInterface,i)->{
                            dialogInterface.dismiss();
                        });
                builder.create().show();
                return;
            }
            if (password.isEmpty()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(getString(R.string.error))
                        .setMessage(getString(R.string.error_password))
                        .setPositiveButton(getString(R.string.ok),(dialogInterface,i)->{
                            dialogInterface.dismiss();
                        });
                builder.create().show();
                return;
            }
            mAuth.signInWithEmailAndPassword(userName, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            mListener.onLogin(user.getUid());
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle(getString(R.string.error))
                                    .setMessage(getString(R.string.error_login))
                                    .setPositiveButton(getString(R.string.ok), (dialogInterface, i) -> {
                                        dialogInterface.dismiss();
                                    });
                            builder.create().show();
                            Log.d(this.getClass().toString(), task.getException().toString());
                        }
                    });
        });

        view.findViewById(R.id.login_create_acc_btn).setOnClickListener(view1 -> mListener.launchRegister());
        return view;
    }

    interface ILogin {
        void launchRegister();

        void onLogin(String uuid);
    }
}