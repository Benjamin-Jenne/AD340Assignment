package com.example.helloworld2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SubmitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        Intent SubmitActivity = getIntent();
        String nameAgeString = SubmitActivity.getStringExtra(Constants.INPUT_FIRSTNAME) + SubmitActivity.getStringExtra("age");
        //Log.i("nameAgeString", nameAgeString);
        TextView nameAge = (TextView) findViewById(R.id.nameAge);
        //nameAge.setText(SubmitActivity.getStringExtra(nameAgeString));
        nameAge.setText(nameAgeString);
    }
}
