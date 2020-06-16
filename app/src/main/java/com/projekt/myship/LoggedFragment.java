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
import android.widget.LinearLayout;
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

///\brief Class linked to logged_fragment xml view
public class LoggedFragment extends Fragment {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private TextView Nam1, Stat1, Send1, Nam2, Stat2, Send2, Nam3, Stat3, Send3;    //XML EditText in logged_fragment Layout
    private List<String> Num = new ArrayList<>(); ///List for JSON Output
    private List<String> Stat = new ArrayList<>();  ///List for JSON Output
    private List<String> Send = new ArrayList<>();  ///List for JSON Output
    private LinearLayout layout1, layout2, layout3;

    @Override

    public void onCreate(@Nullable Bundle savedInstanceState) {
        ((MainActivity) getActivity()).setActionBarTitle("MainPage"); ///Setting up new Toolbar Title
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    /// Inflate the layout for this fragment
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.logged_fragment, container, false);

        return v;
    }

    /// Returning User Input into text fields when entering this fragment ,
    /// Executing LoggedData
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
        layout1 = view.findViewById(R.id.linearLayout1);
        layout2 = view.findViewById(R.id.linearLayout2);
        layout3 = view.findViewById(R.id.linearLayout3);
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


    ///\brief Class that finding top 3 packages for current logged user and Setting values into Text Fields
    public class LoggedData extends AsyncTask<String, String, String> {
        String z = ""; ///\param String for Toast
        Boolean isSuccess = false; ///\param isSuccess Boolean

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String r) {
            Toast.makeText(getActivity(), r, Toast.LENGTH_SHORT).show(); ///Toast if isSuccess = false
            if (isSuccess) {

                Toast.makeText(getActivity(), "Your Packages", Toast.LENGTH_LONG).show(); ///Toast if isSuccess = true
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @SuppressLint("WrongThread")
        @Override
        ///Connecting to api and receiving Post Request Output for current logged user
        /// Details Receiving Output from Api formatting Json Response and saving data in Lists then list data is set on TextFields
        protected String doInBackground(String... params) {
            Data data = new Data();
            String phone2 = data.getPhoneNum(); /// Getting PhoneNum that identify logged user
            String jsonString = "{\"Number\":\"" + phone2 + "\"}";
            URL url = null;
            try {
                url = new URL("http://192.168.0.100:3000/Data"); ///Api URL
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            OkHttpClient client = new OkHttpClient(); /// OkHttp client used for Api Connection
            RequestBody requestBody = RequestBody.create(jsonString, JSON);
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            try (Response res = client.newCall(request).execute()) {
                String content = res.body().string(); ///Getting JSON API Response to String
                if (res.code() == 200) {
                    JSONArray JA = new JSONArray(content); /// JSON Array
                    for (int i = 0; i < JA.length(); i++) {
                        JSONObject object = JA.getJSONObject(i);    /// JSON Object
                        String Number = object.getString("PackageNumber");  /// Getting object element
                        String Status = object.getString("PackageStatus");  /// Getting object element
                        String Sender = object.getString("PackageSender");  /// Getting object element
                        Num.add(Number.trim());     ///Saving data to List object
                        Stat.add(Status.trim());    ///Saving data to List object
                        Send.add(Sender.trim());    ///Saving data to List object

                    }
                    if (Num.size() == 3) {
                        layout1.setVisibility(View.VISIBLE);
                        layout2.setVisibility(View.VISIBLE);
                        layout3.setVisibility(View.VISIBLE);
                        Nam1.setText(Num.get(0));   ///Setting TextViews text property with List Objects
                        Stat1.setText(Stat.get(0));
                        Send1.setText(Send.get(0));
                        Nam2.setText(Num.get(1));
                        Stat2.setText(Stat.get(1));
                        Send2.setText(Send.get(1));
                        Nam3.setText(Num.get(2));
                        Stat3.setText(Stat.get(2));
                        Send3.setText(Send.get(2));

                    } else if (Num.size() == 2) {
                        layout1.setVisibility(View.VISIBLE);
                        layout2.setVisibility(View.VISIBLE);
                         Nam1.setText(Num.get(0));   ///Setting TextViews text property with List Objects
                        Stat1.setText(Stat.get(0));
                        Send1.setText(Send.get(0));
                        Nam2.setText(Num.get(1));
                        Stat2.setText(Stat.get(1));
                        Send2.setText(Send.get(1));
                    } else
                        layout1.setVisibility(View.VISIBLE);
                    Nam1.setText(Num.get(0));   ///Setting TextViews text property with List Objects
                    Stat1.setText(Stat.get(0));
                    Send1.setText(Send.get(0));

                    z = "Your";
                    isSuccess = true; /// isSuccess Boolean true if Api respond with good code
                } else

                    switch (content)  ///Handling Response Codes that are not successful
                    {
                        case "Not Found":
                            z = "User not Found";   ///Setting String that will be used for Toast
                            break;
                        case "Internal Server Error":
                            z = "Something went wrong"; ///Setting String that will be used for Toast
                            break;
                        case "Unauthorized":
                            z = "Wrong Password";   ///Setting String that will be used for Toast
                            break;
                        default:
                            z = "Try again Later";  ///Setting String that will be used for Toast
                            break;
                    }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
                z = "Check your connection";    ///Setting String that will be used for Toast
            }


            return z;  /// returning String Value to Toast
        }
    }
}
