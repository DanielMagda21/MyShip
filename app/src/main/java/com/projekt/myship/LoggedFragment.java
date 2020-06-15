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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * The type Logged fragment.
 */
public class LoggedFragment extends Fragment {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private TextView Nam1, Stat1, Send1, Nam2, Stat2, Send2, Nam3, Stat3, Send3;
    private List<String> Num = new ArrayList<>();
    private List<String> Stat = new ArrayList<>();
    private List<String> Send = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        ((MainActivity) getActivity()).setActionBarTitle("MainPage"); ///Setting up new Toolbar Title
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.logged_fragment, container, false);

        return v;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Nam1 = view.findViewById(R.id.PackageNameData);
        Stat1 = view.findViewById(R.id.PackageStatusData);
        Send1 = view.findViewById(R.id.PackagesenderData);
        Nam2 = view.findViewById(R.id.OrderName2);
        Stat2 = view.findViewById(R.id.OrderStatus2);
        Send2 = view.findViewById(R.id.OrderReciver2);
        Nam3 = view.findViewById(R.id.PackageNameData3);
        Stat3 = view.findViewById(R.id.PackageStatusData3);
        Send3 = view.findViewById(R.id.PackagesenderData3);
        /// Returning User Data into text fields when entering this fragment
        LoggedData loggedData = new LoggedData();
        loggedData.execute();


    }

    ///Setting up Toolbar Menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_logged, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.package_archives:
                NavHostFragment.findNavController(LoggedFragment.this)
                        .navigate(R.id.action_loggedFragment_to_packageArchives);
                return true;

            case R.id.About:
                NavHostFragment.findNavController(LoggedFragment.this)
                        .navigate(R.id.action_loggedFragment_to_aboutFragment);
                return true;
            case R.id.Logout:
                NavHostFragment.findNavController(LoggedFragment.this)
                        .navigate(R.id.action_loggedFragment_to_loginFragment);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * The type Logged data.
     */
/// Class that finding top 3 packages for current logged user and Setting values into Text Fields
    public class LoggedData extends AsyncTask<String, String, String> {
        /**
         * The Z.
         */
        String z = "";
        /**
         * The Is success.
         */
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String r) {
            Toast.makeText(getActivity(), r, Toast.LENGTH_SHORT).show();
            if (isSuccess) {

                Toast.makeText(getActivity(), "Welcome", Toast.LENGTH_LONG).show();
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @SuppressLint("WrongThread")
        @Override
        ///Connecting to api and receiving Post Request Data for current logged user
        protected String doInBackground(String... params) {
            Data data = new Data();
            String phone2 = data.getPhoneNum();
            String jsonString = "{\"Number\":\"" + phone2 + "\"}";
            URL url = null;
            try {
                url = new URL("http://192.168.0.100:3000/Data");
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
                    ///Receiving Data from Json Response and saving data in Lists
                    JSONArray JA = new JSONArray(content);
                    for (int i = 0; i < JA.length(); i++) {
                        JSONObject object = JA.getJSONObject(i);
                        String Number = object.getString("PackageNumber");
                        String Status = object.getString("PackageStatus");
                        String Sender = object.getString("PackageSender");
                        Num.add(Number.trim());
                        Stat.add(Status.trim());
                        Send.add(Sender.trim());

                    }       /// Setting Lists Values Into TextFields
                    Nam1.setText(Num.get(0));
                    Stat1.setText(Stat.get(0));
                    Send1.setText(Send.get(0));
                    Nam2.setText(Num.get(1));
                    Stat2.setText(Stat.get(1));
                    Send2.setText(Send.get(1));
                    Nam3.setText(Num.get(2));
                    Stat3.setText(Stat.get(2));
                    Send3.setText(Send.get(2));


                    z = "Your Packages";
                } else
                    ///Handling Response Codes that are not successful
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

            } catch (IOException | JSONException e) {
                e.printStackTrace();
                z = "Check your connection";
            }

            /// returning String Value to Toast
            return z;
        }
    }
}
