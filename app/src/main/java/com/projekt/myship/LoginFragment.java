package com.projekt.myship;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LoginFragment extends Fragment {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final DataValidation dataValidation = new DataValidation();
    private EditText username, password;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        ((MainActivity) getActivity()).setActionBarTitle("Login");
        return inflater.inflate(R.layout.fragment_first, container, false);

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button login = view.findViewById(R.id.LoginButton);
        Button register = view.findViewById(R.id.RegisterButton);
        username = view.findViewById(R.id.LoginUser);
        password = view.findViewById(R.id.LoginPassword);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckLogin checkLogin = new CheckLogin();
                checkLogin.execute();

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });


    }


    public class CheckLogin extends AsyncTask<String, String, String> {

        String z = "";

        Boolean isSuccess = false;

        Data data = new Data();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String r) {

            Toast.makeText(getActivity(), r, Toast.LENGTH_SHORT).show();
            if (isSuccess) {

                Toast.makeText(getActivity(), "Welcome", Toast.LENGTH_LONG).show();
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.action_loginFragment_to_loggedFragment);
            }
        }


        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... params) {
            String username = LoginFragment.this.username.getText().toString();
            String password = LoginFragment.this.password.getText().toString();

            if (username.trim().equals("") || password.trim().equals("")) {
                z = "Please enter Username and Password";
            } else {

                dataValidation.setUserName(username);
                dataValidation.setPassword(password);
                if (!dataValidation.LoginDataCheck()) {
                    z = "Please enter Valid Data";
                } else {

                    String jsonString = "{\"name\":\"" + username + "\",\"pass\":\"" + password + "\"}";
                    URL url = null;
                    try {
                        url = new URL("http://192.168.0.100:3000/Login");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = RequestBody.create(jsonString, JSON);
                    Request request = new Request.Builder()
                            .url(url)
                            .post(requestBody)
                            .build();
                    try (Response res = client.newCall(request).execute()) {
                        String content = res.body().string();
                        if (res.isSuccessful()) {

                            data.setPhoneNum(content);
                            z = "Welcome";
                            isSuccess = true;

                        } else

                            switch (content) {
                                case "Not Found":
                                    z = "User not Found";
                                    break;
                                case "Internal Server Error":
                                    z = "Something went wrong";
                                    break;
                                case "Unauthorized":
                                    z = "Wrong Password";
                                    break;
                                default:
                                    z = "Try again Later";
                                    break;
                            }

                    } catch (IOException e) {
                        e.printStackTrace();
                        z = "Check your connection";
                    }

                }
            }

            return z;
        }
    }

}