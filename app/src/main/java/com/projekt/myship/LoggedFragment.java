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

import org.jetbrains.annotations.NotNull;
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

import static com.projekt.myship.R.id.About;
import static com.projekt.myship.R.id.Logout;
import static com.projekt.myship.R.id.OrderName2;
import static com.projekt.myship.R.id.OrderReciver2;
import static com.projekt.myship.R.id.OrderStatus2;
import static com.projekt.myship.R.id.PackageNameData;
import static com.projekt.myship.R.id.PackageNameData3;
import static com.projekt.myship.R.id.PackageStatusData;
import static com.projekt.myship.R.id.PackageStatusData3;
import static com.projekt.myship.R.id.PackagesenderData;
import static com.projekt.myship.R.id.PackagesenderData3;
import static com.projekt.myship.R.id.action_loggedFragment_to_aboutFragment;
import static com.projekt.myship.R.id.action_loggedFragment_to_loginFragment;
import static com.projekt.myship.R.id.action_loggedFragment_to_packageArchives;
import static com.projekt.myship.R.id.linearLayout1;
import static com.projekt.myship.R.id.linearLayout2;
import static com.projekt.myship.R.id.linearLayout3;
import static com.projekt.myship.R.id.package_archives;
import static com.projekt.myship.R.layout;

public class LoggedFragment extends Fragment {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final List<String> Num = new ArrayList<>();
    private final List<String> Stat = new ArrayList<>();
    private final List<String> Send = new ArrayList<>();
    private TextView Nam1, Stat1, Send1, Nam2, Stat2, Send2, Nam3, Stat3, Send3;
    private LinearLayout layout1, layout2, layout3;

    @Override

    public void onCreate(@Nullable Bundle savedInstanceState) {
        ((MainActivity) getActivity()).setActionBarTitle("MainPage");
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    /// Inflate the layout for this fragment
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(layout.logged_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Nam1 = view.findViewById(PackageNameData);
        Stat1 = view.findViewById(PackageStatusData);
        Send1 = view.findViewById(PackagesenderData);
        Nam2 = view.findViewById(OrderName2);
        Stat2 = view.findViewById(OrderStatus2);
        Send2 = view.findViewById(OrderReciver2);
        Nam3 = view.findViewById(PackageNameData3);
        Stat3 = view.findViewById(PackageStatusData3);
        Send3 = view.findViewById(PackagesenderData3);
        layout1 = view.findViewById(linearLayout1);
        layout2 = view.findViewById(linearLayout2);
        layout3 = view.findViewById(linearLayout3);
        LoggedData loggedData = new LoggedData();
        loggedData.execute();


    }


    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_logged, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();
        if (itemId == package_archives) {
            NavHostFragment.findNavController(LoggedFragment.this)
                    .navigate(action_loggedFragment_to_packageArchives);
            return true;
        } else if (itemId == About) {
            NavHostFragment.findNavController(LoggedFragment.this)
                    .navigate(action_loggedFragment_to_aboutFragment);
            return true;
        } else if (itemId == Logout) {
            NavHostFragment.findNavController(LoggedFragment.this)
                    .navigate(action_loggedFragment_to_loginFragment);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public class LoggedData extends AsyncTask<String, String, String> {
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

                Toast.makeText(getActivity(), "Your Packages", Toast.LENGTH_LONG).show();
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @SuppressLint("WrongThread")
        @Override

        protected String doInBackground(String... params) {
            Data data = new Data();
            String phone2 = data.getPhoneNum();
            String jsonString = "{\"Number\":\"" + phone2 + "\"}";
            URL url = null;
            try {
                url = new URL("http://192.168.0.100:3000/Data"); ///Api URL
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
                if (res.code() == 200) {
                    JSONArray JA = new JSONArray(content);
                    for (int i = 0; i < JA.length(); i++) {
                        JSONObject object = JA.getJSONObject(i);
                        String Number = object.getString("PackageNumber");
                        String Status = object.getString("PackageStatus");
                        String Sender = object.getString("PackageSender");
                        Num.add(Number.trim());
                        Stat.add(Status.trim());
                        Send.add(Sender.trim());

                    }
                    if (Num.size() == 3) {
                        layout1.setVisibility(View.VISIBLE);
                        layout2.setVisibility(View.VISIBLE);
                        layout3.setVisibility(View.VISIBLE);
                        Nam1.setText(Num.get(0));
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
                        Nam1.setText(Num.get(0));
                        Stat1.setText(Stat.get(0));
                        Send1.setText(Send.get(0));
                        Nam2.setText(Num.get(1));
                        Stat2.setText(Stat.get(1));
                        Send2.setText(Send.get(1));
                    } else
                        layout1.setVisibility(View.VISIBLE);
                    Nam1.setText(Num.get(0));
                    Stat1.setText(Stat.get(0));
                    Send1.setText(Send.get(0));

                    z = "Your";
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

            } catch (IOException | JSONException e) {
                e.printStackTrace();
                z = "Check your connection";
            }


            return z;
        }
    }
}
