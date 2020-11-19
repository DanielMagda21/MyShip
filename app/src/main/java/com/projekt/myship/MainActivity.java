package com.projekt.myship;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}

class Data {


    public static String PhoneNum; /// PhoneNum String

    ///\brief getter PhoneNub
    String getPhoneNum() {
        return PhoneNum;
    }

    ///\brief setter PhoneNub
    void setPhoneNum(String PhoneNum) {
        Data.PhoneNum = PhoneNum;
    }

}



