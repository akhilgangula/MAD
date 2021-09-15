package com.example.inclass04;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    ILoginListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        view.findViewById(R.id.create_account_btn).setOnClickListener(view1 -> mListener.onCreate());
        ((EditText) view.findViewById(R.id.login_email)).setText("");
        ((EditText) view.findViewById(R.id.login_password)).setText("");
        view.findViewById(R.id.loginBtn).setOnClickListener(view1 -> {
            String email = ((EditText) view.findViewById(R.id.login_email)).getText().toString();
            String password = ((EditText) view.findViewById(R.id.login_password)).getText().toString();
            if (email.isEmpty()) {
                Toast.makeText(getActivity(), getString(R.string.email_validation_error), Toast.LENGTH_SHORT).show();
                return;
            } else if (password.isEmpty()) {
                Toast.makeText(getActivity(), getString(R.string.password_placeholder), Toast.LENGTH_SHORT).show();
                return;
            }
            DataServices.AccountRequestTask task = DataServices.login(email, password);

            if (task.isSuccessful()) {
                Toast.makeText(getActivity(), getString(R.string.login_success), Toast.LENGTH_SHORT).show();
                mListener.onLogin(task.getAccount());
            } else {
                Toast.makeText(getActivity(), task.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ILoginListener) {
            mListener = (ILoginListener) context;
        } else {
            throw new RuntimeException(context.toString() + "must implement ILoginListener");
        }
    }

    public interface ILoginListener {
        void onCreate();

        void onLogin(DataServices.Account account);
    }
}