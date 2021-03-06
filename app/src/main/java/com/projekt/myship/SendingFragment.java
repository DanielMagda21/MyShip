package com.projekt.myship;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class SendingFragment extends Fragment {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private EditText OrderName, OrderReciver, OrderID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState

    ) {
        ((MainActivity) getActivity()).setActionBarTitle("SendingFragment");
        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.send_fragment, container, false);

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button SendOrder = view.findViewById(R.id.sendorder);
        OrderName = view.findViewById(R.id.OrderName);
        OrderReciver = view.findViewById(R.id.OrderReciver);
        OrderID = view.findViewById(R.id.OrderID);
        SendOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendingData sendingData = new SendingData();
                sendingData.execute();
            }
        });


    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_logged, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.Main) {
            NavHostFragment.findNavController(SendingFragment.this)
                    .navigate(R.id.action_packageArchives_to_loggedFragment);
            return true;
        } else if (itemId == R.id.About) {
            NavHostFragment.findNavController(SendingFragment.this)
                    .navigate(R.id.action_packageArchives_to_aboutFragment);
            return true;
        } else if (itemId == R.id.Logout) {
            NavHostFragment.findNavController(SendingFragment.this)
                    .navigate(R.id.action_packageArchives_to_loginFragment);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class SendingData extends AsyncTask<String, String, String> {

        String z = "";

        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String r) {
            Toast.makeText(getActivity(), r, Toast.LENGTH_SHORT).show();
            if (isSuccess) {

                Toast.makeText(getActivity(), "Your order was sent", Toast.LENGTH_LONG).show();
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @SuppressLint("WrongThread")
        @Override

        protected String doInBackground(String... params) {
            String name = SendingFragment.this.OrderName.getText().toString();
            String id = SendingFragment.this.OrderID.getText().toString();
            String sender = SendingFragment.this.OrderReciver.getText().toString();
            DataValidation dataValidation = new DataValidation();
            dataValidation.setID(id);
            dataValidation.setNumber(name);
            dataValidation.setSender(sender);
            if (name.trim().equals("") || id.trim().equals("") || sender.trim().equals("")) {
                z = "Please enter Data";
            } else if (!dataValidation.SendingCheck()) {
                z = "Please enter Valid Data";
            } else {
                String jsonString = "{\"id\":\"" + id + "\",\"name\":\"" + name + "\",\"sender\":\"" + sender + "\"}";
                URL url = null;
                try {
                    url = new URL("http://192.168.0.100:3000/Send"); ///Api URL
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

                        isSuccess = true;

                    } else

                        switch (content) {
                            case "Not Found":
                                z = "Something went wrong";
                                break;
                            case "Internal Server Error":
                                z = "Something went wrong2";
                                break;
                            case "Unauthorized":
                                z = "Something went wrong3";
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

            return z;
        }
    }
}
