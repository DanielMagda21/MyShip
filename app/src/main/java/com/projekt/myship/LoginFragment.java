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

///\brief Class linked to Login xml view
public class LoginFragment extends Fragment {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private DataValidation dataValidation = new DataValidation(); ///DataValidation object
    private EditText username, password; ///XML EditText in login_fragment Layout

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        ((MainActivity) getActivity()).setActionBarTitle("Login"); ///Setting new Toolbar Tittle
        return inflater.inflate(R.layout.login_fragment, container, false);

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button login = view.findViewById(R.id.LoginButton);
        Button register = view.findViewById(R.id.RegisterButton);
        username = view.findViewById(R.id.LoginUser);
        password = view.findViewById(R.id.LoginPassword);
        ///Button executing CheckLogin
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckLogin checkLogin = new CheckLogin();
                checkLogin.execute();

            }
        });
        /// On button Click Nav to Register Fragment
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

        Boolean isSuccess = false; /// isSuccess Boolean

        Data data = new Data();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String r) {

            Toast.makeText(getActivity(), r, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                /// if isSuccess Boolean is set up true Nav to LoggedIn Fragment
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
            /// Checking if field are not empty
            if (username.trim().equals("") || password.trim().equals("")) /// Checking if TextFields are not empty
            {
                z = "Please enter Username and Password"; ///Setting String that will be used for Toast
            } else  /// Using Data Validation method to check if text entered are ok
            {

                dataValidation.setUserName(username); /// DataValidation setting  setUserName
                dataValidation.setPassword(password); /// DataValidation setting  setPassword
                if (!dataValidation.LoginDataCheck())   /// Calling Regular Expression methods
                {
                    z = "Please enter Valid Data"; ///Setting String that will be used for Toast
                } else {
                    ///Connecting to Api checking if Login Data are Valid
                    String jsonString = "{\"name\":\"" + username + "\",\"pass\":\"" + password + "\"}";
                    URL url = null;
                    try {
                        url = new URL("http://192.168.0.100:3000/Login"); ///Api URL
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
                            ///Setting up local Variable that will identify logged user
                            data.setPhoneNum(content);
                            z = "Welcome"; ///Setting String that will be used for Toast
                            isSuccess = true; /// isSuccess Boolean true if Api respond with good code

                        } else

                            switch (content) ///Handling Response Codes that are not successful
                            {
                                case "Not Found":
                                    z = "User not Found"; ///Setting String that will be used for Toast
                                    break;
                                case "Internal Server Error":
                                    z = "Something went wrong"; ///Setting String that will be used for Toast
                                    break;
                                case "Unauthorized":
                                    z = "Wrong Password"; ///Setting String that will be used for Toast
                                    break;
                                default:
                                    z = "Try again Later"; ///Setting String that will be used for Toast
                                    break;
                            }

                    } catch (IOException e) {
                        e.printStackTrace();
                        z = "Check your connection"; ///Setting String that will be used for Toast
                    }

                }
            }

            return z; /// returning String Value to Toast
        }
    }

}