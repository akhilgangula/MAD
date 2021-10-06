package com.example.inclass07;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.Serializable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactUpdateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactUpdateFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private IUpdateView mListener;
    private Contact contact;

    public ContactUpdateFragment(IUpdateView mListener) {
        this.mListener = mListener;
    }

    public ContactUpdateFragment() {
        // Required empty public constructor
    }

    public static ContactUpdateFragment newInstance(IUpdateView mListener, Contact contact) {
        ContactUpdateFragment fragment = new ContactUpdateFragment(mListener);
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM2, (Serializable) contact);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            contact = (Contact) getArguments().getSerializable(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_update, container, false);
        getActivity().setTitle(Util.UPDATE_CONTACT_TITLE);
        EditText name = (EditText) view.findViewById(R.id.update_contact_name);
        EditText email = (EditText) view.findViewById(R.id.update_contact_email);
        EditText phone = (EditText) view.findViewById(R.id.update_contact_phone);
        EditText type = (EditText) view.findViewById(R.id.update_contact_type);
        name.setText(contact.getName());
        email.setText(contact.getEmail());
        phone.setText(contact.getPhone());
        type.setText(contact.getPhoneType());
        view.findViewById(R.id.update_contact_btn).setOnClickListener(view1 -> {
            String newName = name.getText().toString();
            if (!Util.isValidName(newName)) {
                ErrorAlert.showError(view.getContext(), getString(R.string.error_name));
                return;
            }
            String newEmail = email.getText().toString();
            if (!Util.isValidEmail(newEmail)) {
                ErrorAlert.showError(view.getContext(), getString(R.string.error_email));
                return;
            }
            String newPhone = phone.getText().toString();
            if (!Util.isValidPhone(newPhone)) {
                ErrorAlert.showError(view.getContext(), getString(R.string.error_phone));
                return;
            }
            String newType = type.getText().toString();
            if (!Util.isValidPhoneType(newType)) {
                ErrorAlert.showError(view.getContext(), getString(R.string.error_type));
                return;
            }
            Service
                    .updateContact(new Contact(contact.getCid(), newName, newEmail, newPhone, newType),
                            new Handler(message -> {
                                if (message.arg1 == 1) {
                                    Contact contact = (Contact) message.getData().getSerializable(Service.KEY);
                                    Toast.makeText(getContext(), getString(R.string.update_success), Toast.LENGTH_SHORT).show();
                                    mListener.onUpdateComplete(contact);
                                } else {
                                    Toast.makeText(getContext(), getString(R.string.update_failed), Toast.LENGTH_SHORT).show();
                                }
                                return false;
                            }));
        });
        return view;
    }

    interface IUpdateView {
        void onUpdateComplete(Contact contact);
    }
}