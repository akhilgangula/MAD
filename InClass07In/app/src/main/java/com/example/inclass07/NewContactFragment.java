package com.example.inclass07;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewContactFragment extends Fragment {

    public NewContactFragment() {
        // Required empty public constructor
    }

    public static NewContactFragment newInstance(INewContact mListener) {
        NewContactFragment fragment = new NewContactFragment(mListener);
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
        View view= inflater.inflate(R.layout.fragment_new_contact, container, false);
        getActivity().setTitle(Util.NEW_CONTACT_TITLE);
        view.findViewById(R.id.cancel_add_new_btn).setOnClickListener(view1 -> {
            mListener.cancelAddNew();
        });
        view.findViewById(R.id.update_contact_btn).setOnClickListener(view1 -> {
            Contact contact = new Contact();
            contact.setName(((TextView)view.findViewById(R.id.update_contact_name)).getText().toString());
            contact.setEmail(((TextView)view.findViewById(R.id.update_contact_email)).getText().toString());
            contact.setPhone(((TextView)view.findViewById(R.id.update_contact_phone)).getText().toString());
            contact.setPhoneType(((TextView)view.findViewById(R.id.update_contact_type)).getText().toString());
            if(!Util.isValidName(contact.getName())) {
                ErrorAlert.showError(getContext(), getString(R.string.error_name));
                return;
            }
            if(!Util.isValidEmail(contact.getEmail())) {
                ErrorAlert.showError(getContext(), getString(R.string.error_email));
                return;
            }
            if(!Util.isValidPhone(contact.getPhone())) {
                ErrorAlert.showError(getContext(), getString(R.string.error_phone));
                return;
            }
            if(!Util.isValidPhoneType(contact.getPhoneType())) {
                ErrorAlert.showError(getContext(), getString(R.string.error_type));
                return;
            }
            Service.addContact(contact, new Handler(message -> {
                int success =message.arg1;
                if(success == 1) {
                    mListener.onSubmitAddNew();
                    Toast.makeText(getContext(), getString(R.string.create_success), Toast.LENGTH_SHORT).show();
                } else {
                    // TODO: Replace with Alert Dialog
                    Toast.makeText(getContext(), getString(R.string.create_failed), Toast.LENGTH_SHORT).show();
                }
                return false;
            }));
        });
        return view;
    }

    public NewContactFragment(INewContact mListener) {
        this.mListener = mListener;
    }

    INewContact mListener;
    interface INewContact {
        void cancelAddNew();
        void onSubmitAddNew();
    }
}