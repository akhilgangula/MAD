package com.example.midterm;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.example.midterm.response.AuthenticationFailedResponse;
import com.example.midterm.response.AuthenticationResponse;
import com.example.midterm.response.GenericResponse;
import com.example.midterm.response.PostCreateResponse;
import com.example.midterm.response.PostResponse;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Service {
    static OkHttpClient client = new OkHttpClient();
    public static void createUser(UserPOJO user, Handler handler) {
        FormBody formBody = new FormBody.Builder()
                .add(Constants.EMAIL_KEY, user.getEmail())
                .add(Constants.NAME_KEY, user.getName())
                .add(Constants.PASS_KEY, user.getPassword())
                .build();
        Request request =  new Request.Builder()
                .url(Constants.BASE_URL + Constants.SIGNUP_URL)
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
                    Gson gson = new Gson();
                if(response.isSuccessful()) {
                    AuthenticationResponse createResponse = gson.fromJson(response.body().charStream(), AuthenticationResponse.class);
                    message.arg1 = 1;
                    message.obj = createResponse;
                } else {
                    message.arg1 = 0;
                    AuthenticationFailedResponse failedResponse = gson.fromJson(response.body().charStream(), AuthenticationFailedResponse.class);
                    message.obj = failedResponse;
                }
                handler.sendMessage(message);
            }
        });
    }

    public static void loginUser(UserPOJO user, Handler handler) {
        FormBody formBody = new FormBody.Builder()
                .add(Constants.EMAIL_KEY, user.getEmail())
                .add(Constants.PASS_KEY, user.getPassword())
                .build();
        Request request =  new Request.Builder()
                .url(Constants.BASE_URL + Constants.LOGIN_URL)
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
                Gson gson = new Gson();
                if(response.isSuccessful()) {
                    AuthenticationResponse createResponse = gson.fromJson(response.body().charStream(), AuthenticationResponse.class);
                    message.arg1 = 1;
                    message.obj = createResponse;
                } else {
                    message.arg1 = 0;
                    AuthenticationFailedResponse failedResponse = gson.fromJson(response.body().charStream(), AuthenticationFailedResponse.class);
                    message.obj = failedResponse;
                }
                handler.sendMessage(message);
            }
        });
    }

    public static void getPost(AuthenticationResponse auth, String page, Handler handler) {
        HttpUrl request = HttpUrl
                .parse(Constants.BASE_URL + Constants.GET_POST)
                .newBuilder()
                .addQueryParameter(Constants.PAGE_KEY, page)
                .build();
        client.newCall(new Request.Builder().url(request).header(Constants.AUTH_KEY, Constants.BEARER_KEY+" "+auth.getToken()).build()).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                Message message = new Message();
                Gson gson = new Gson();
                if(response.isSuccessful()) {
                    PostResponse createResponse = gson.fromJson(response.body().charStream(), PostResponse.class);
                    message.arg1 = 1;
                    message.obj = createResponse;
                } else {
                    message.arg1 = 0;
                }
                handler.sendMessage(message);
            }
        });
    }

    public static void createPost(AuthenticationResponse auth, String post, Handler handler) {
        FormBody formBody = new FormBody.Builder()
                .add(Constants.POST_TEXT_KEY, post)
                .build();
        Request request =  new Request.Builder()
                .url(Constants.BASE_URL + Constants.CREATE_POST)
                .post(formBody)
                .header(Constants.AUTH_KEY, Constants.BEARER_KEY+" "+ auth.getToken())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                Message message = new Message();
                Gson gson = new Gson();
                if(response.isSuccessful()) {
                    PostCreateResponse createResponse = gson.fromJson(response.body().charStream(), PostCreateResponse.class);
                    message.arg1 = 1;
                    message.obj = createResponse;
                } else {
                    PostCreateResponse createResponse = gson.fromJson(response.body().charStream(), PostCreateResponse.class);
                    message.arg1 = 0;
                    message.obj = createResponse;
                }
                handler.sendMessage(message);
            }
        });
    }

    public static void deletePost(AuthenticationResponse auth, String postId, Handler handler) {
        FormBody formBody = new FormBody.Builder()
                .add(Constants.POST_ID_KEY, postId)
                .build();
        Request request =  new Request.Builder()
                .url(Constants.BASE_URL + Constants.DELETE_POST)
                .post(formBody)
                .header(Constants.AUTH_KEY, Constants.BEARER_KEY+" "+ auth.getToken())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                Message message = new Message();
                Gson gson = new Gson();
                if(response.isSuccessful()) {
                    GenericResponse deleteResponseSuccess = gson.fromJson(response.body().charStream(), GenericResponse.class);
                    message.arg1 = 1;
                    message.obj = deleteResponseSuccess;
                } else {
                    GenericResponse deleteResponseFailed = gson.fromJson(response.body().charStream(), GenericResponse.class);
                    message.arg1 = 0;
                    message.obj = deleteResponseFailed;
                }
                handler.sendMessage(message);
            }
        });
    }
}
