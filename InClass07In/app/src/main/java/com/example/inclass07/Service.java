package com.example.inclass07;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Service {
    public static final String KEY = "key";
    static OkHttpClient client = new OkHttpClient();
    public static void getContacts(Handler handler) {
        Request request =  new Request.Builder()
                .url(Util.GET_URL)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                if(response.isSuccessful()) {
                    Gson gson = new Gson();
                    ArrayContactResponse contactResponse = gson.fromJson(response.body().charStream(), ArrayContactResponse.class);
                    List<Contact> refined_contacts = contactResponse.contacts;
                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(KEY, (Serializable) refined_contacts);
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
            }
        });
    }

    public static void deleteContact(String id, Handler handler) {
        FormBody formBody = new FormBody.Builder()
                .add(Util.ID, id)
                .build();
        Request request =  new Request.Builder()
                .url(Util.URL + Util.URL_DELETE)
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                Message message = new Message();
                message.arg1 = response.isSuccessful() ? 1 : 0;
                handler.sendMessage(message);
            }
        });
    }

    public static void addContact(Contact contact, Handler handler) {
        FormBody formBody = new FormBody.Builder()
                .add(Util.NAME, contact.getName())
                .add(Util.EMAIL, contact.getEmail())
                .add(Util.PHONE, contact.getPhone())
                .add(Util.TYPE, contact.getPhoneType())
                .build();
        Request request =  new Request.Builder()
                .url(Util.URL + Util.URL_CREATE)
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                Message message = new Message();
                if(response.isSuccessful()) {
                    message.arg1 = 1;
                } else {
                    message.arg1 = 0;
                }
                    handler.sendMessage(message);
            }
        });
    }

    public static void updateContact(Contact contact, Handler handler) {
        FormBody formBody = new FormBody.Builder()
                .add(Util.ID, contact.getCid())
                .add(Util.NAME, contact.getName())
                .add(Util.EMAIL, contact.getEmail())
                .add(Util.PHONE, contact.getPhone())
                .add(Util.TYPE, contact.getPhoneType())
                .build();
        Request request =  new Request.Builder()
                .url(Util.URL + Util.URL_UPDATE)
                .post(formBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                Message message = new Message();
                Bundle bundle = new Bundle();
                if(response.isSuccessful()) {
                    Gson gson = new Gson();
                    message.arg1 = 1;
                    ContactUpdateResponse contactResponse = gson.fromJson(response.body().charStream(), ContactUpdateResponse.class);
                    Contact updated_contact = contactResponse.getContact();
                    bundle.putSerializable(KEY, updated_contact);
                    message.setData(bundle);
                } else {
                    message.arg1 = 0;
                }
                handler.sendMessage(message);
            }
        });
    }
}
