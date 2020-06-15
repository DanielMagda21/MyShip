package com.projekt.myship;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * Sets action bar title.
     *
     * @param title the title
     */
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}

/**
 * The type Data.
 */
/// Class Handling Setter and Getter for Variable that will identify user after Login
class Data {

    /**
     * The constant PhoneNum.
     */
    public static String PhoneNum;

    /**
     * Gets phone num.
     *
     * @return the phone num
     */
    String getPhoneNum() {
        return PhoneNum;
    }

    /**
     * Sets phone num.
     *
     * @param PhoneNum the phone num
     */
    void setPhoneNum(String PhoneNum) {
        Data.PhoneNum = PhoneNum;
    }

}



