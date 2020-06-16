package com.projekt.myship;

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

/// \brief Register Fragment
/// \details Fragment linked to Register xml View
public class RegisterFragment extends Fragment {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private EditText userfname, password, userlname, phone;

    /// Inflate the layout for this fragment
    /// Setting Toolbar Title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        ((MainActivity) getActivity()).setActionBarTitle("Register");
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button RegisterButton = view.findViewById(R.id.RegisterButton);
        Button Back = view.findViewById(R.id.BackButt);
        userfname = view.findViewById(R.id.RegisterUserFName);
        password = view.findViewById(R.id.RegisterPassword);
        userlname = view.findViewById(R.id.RegisterLUser);
        phone = view.findViewById(R.id.RegisterNumber);
        ///Button Nav Back to Login Fragment
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(RegisterFragment.this)
                        .navigate(R.id.action_registerFragment_to_loginFragment);
            }
        });
        ///Button executing Register
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register register = new Register();
                register.execute();
            }
        });

    }


/// \brief Api usage
/// \details Connecting to API Checking Data , Checking if user Already exist if not Inserting Into Data base new User
    public class Register extends AsyncTask<String, String, String> {
        String z = "";
        /// \param isSuccess Boolean
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        /// \brief Nav to login fragment
        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                Toast.makeText(getActivity(), "Login Successfull", Toast.LENGTH_LONG).show();
                NavHostFragment.findNavController(RegisterFragment.this)
                        .navigate(R.id.action_registerFragment_to_loginFragment);
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... params) {
            String userfname = RegisterFragment.this.userfname.getText().toString();
            String userlname = RegisterFragment.this.userlname.getText().toString();
            String password = RegisterFragment.this.password.getText().toString();
            String phone = RegisterFragment.this.phone.getText().toString();
            DataValidation dataValidation = new DataValidation();
            dataValidation.setRFname(userfname);
            dataValidation.setRLname(userlname);
            dataValidation.setRPassword(password);
            /// Checking if TextFields are not empty
            if (userfname.trim().equals("") || userlname.trim().equals("") || password.trim().equals("") || phone.trim().equals("")) {
                z = "Please enter Data";
                /// Calling Regular Expression methods if data are valid
            } else if (!dataValidation.RegisterDataCheck()) {
                z = "Please enter Valid Data";
            } else {
                ///Connecting Api and Inserting User TextFields Data into Data base
                String jsonString = "{\"userfname\":\"" + userfname + "\",\"userlname\":\"" + userlname + "\",\"password\":\"" + password + "\",\"number\":\"" + phone + "\"}";
                URL url = null;
                try {
                    url = new URL("http://192.168.0.100:3000/Register");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = RequestBody.create(jsonString, JSON);
                assert url != null;
                Request request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .build();
                try (Response res = client.newCall(request).execute()) {
                    String content = res.body().string();
                    if (res.isSuccessful()) {
                        z = "Welcome";
                        isSuccess = true;
                    } else
                        ///Handling Response Codes that are not successful
                        switch (content) {
                            case "Conflict":
                                z = "User already exist";
                                break;
                            case "Internal Server Error":
                                z = "Something went wrong";
                                break;
                            default:
                                z = "Try again Later";
                                break;
                        }


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            /// returning String Value to Toast
            return z;
        }


    }
}

