package com.example.inclass07;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    List<Contact> contactList;
    Activity activity;
    ContactListFragment.IContactDelete mListener;

    public ContactAdapter(List<Contact> contacts, Activity activity, ContactListFragment.IContactDelete mListener) {
        this.contactList = contacts;
        this.activity = activity;
        this.mListener = mListener;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_card, parent, false);
        return new ContactAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = contactList.get(position);
        holder.contactName.setText(contact.getName());
        holder.contactEmail.setText(contact.getEmail());
        holder.contactPhone.setText(contact.getPhone());
        holder.contactPhoneType.setText(contact.getPhoneType());
        holder.contactId.setText(contact.getCid());
        holder.contactCard.setOnClickListener(view -> {
            mListener.openContactDetail(contact);
        });
        holder.contactDelete.setOnClickListener(view -> {
            Service.deleteContact(contact.getCid(), new Handler(message -> {
                int success = message.arg1;
                Toast.makeText(view.getContext(),
                        view.getContext().getString(success == 1 ? R.string.delete_success : R.string.delete_failed),
                        Toast.LENGTH_SHORT)
                        .show();
                Service.getContacts(new Handler(message1 -> {
                    Bundle bundle = message1.getData();
                    List<Contact> new_contacts = (List<Contact>) bundle.getSerializable(Service.KEY);
                    setContactList(new_contacts);
                    this.notifyDataSetChanged();
                    return false;
                }));
                return false;
            }));
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView contactName, contactEmail, contactPhone, contactPhoneType, contactId;
        private final Button contactDelete;
        private final CardView contactCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contactName = itemView.findViewById(R.id.contact_name);
            contactEmail = itemView.findViewById(R.id.contact_email);
            contactPhone = itemView.findViewById(R.id.contact_phone);
            contactPhoneType = itemView.findViewById(R.id.contact_phone_type);
            contactDelete = itemView.findViewById(R.id.contact_delete_btn);
            contactCard = itemView.findViewById(R.id.contact_card);
            contactId = itemView.findViewById(R.id.contact_id);
        }
    }
}
