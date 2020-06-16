package com.projekt.myship;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}


/// Class Handling Setter and Getter for Variable that will identify user after Login
class Data {


    public static String PhoneNum;


    String getPhoneNum() {
        return PhoneNum;
    }


    void setPhoneNum(String PhoneNum) {
        Data.PhoneNum = PhoneNum;
    }

}



