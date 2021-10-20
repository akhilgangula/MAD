package com.example.midterm;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.midterm.response.AuthenticationFailedResponse;
import com.example.midterm.response.AuthenticationResponse;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    IRegister mListener;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }

    public RegisterFragment(IRegister mListener) {
        this.mListener = mListener;
    }

    public static RegisterFragment newInstance(IRegister mListener) {
        RegisterFragment fragment = new RegisterFragment(mListener);
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        getActivity().setTitle(getString(R.string.create_title));
        view.findViewById(R.id.register_submit).setOnClickListener(view1 -> {
            String email = ((EditText) view.findViewById(R.id.register_email)).getText().toString();
            String name = ((EditText) view.findViewById(R.id.register_name)).getText().toString();
            String password = ((EditText) view.findViewById(R.id.register_pasword)).getText().toString();
            if (email.isEmpty()) {
                AlertDialog.Builder builder =new AlertDialog.Builder(getContext());
                builder.setTitle(Constants.ERROR_TITLE)
                        .setMessage(getString(R.string.error_email))
                        .setPositiveButton(getString(R.string.ok),(dialogInterface,i)->{
                            dialogInterface.dismiss();
                        });
                builder.create().show();
                return;
            }
            if (name.isEmpty()) {
                AlertDialog.Builder builder =new AlertDialog.Builder(getContext());
                builder.setTitle(Constants.ERROR_TITLE)
                        .setMessage(getString(R.string.error_name))
                        .setPositiveButton(getString(R.string.ok),(dialogInterface,i)->{
                            dialogInterface.dismiss();
                        });
                builder.create().show();
                return;
            }
            if (password.isEmpty()) {
                AlertDialog.Builder builder =new AlertDialog.Builder(getContext());
                builder.setTitle(Constants.ERROR_TITLE)
                        .setMessage(getString(R.string.error_password))
                        .setPositiveButton(getString(R.string.ok),(dialogInterface,i)->{
                            dialogInterface.dismiss();
                        });
                builder.create().show();
                return;
            }
            Service.createUser(new UserPOJO(name, email, password), new Handler(message -> {
                if (message.arg1 != 1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle(Constants.ERROR_TITLE)
                            .setMessage(((AuthenticationFailedResponse) message.obj).getMessage())
                            .setPositiveButton(getString(R.string.ok), (dialogInterface, i) -> {
                                dialogInterface.dismiss();
                            });
                    builder.create().show();
                    return false;
                }
                mListener.launchList((AuthenticationResponse) message.obj);
                return false;
            }));
        });
        view.findViewById(R.id.register_cancel).setOnClickListener(view1 -> {
            mListener.goToHomePage();
        });
        return view;
    }

    interface IRegister extends LoginFragment.IHome {
        void goToHomePage();
    }
}