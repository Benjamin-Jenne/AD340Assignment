package com.example.helloworld2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SubmitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        Intent SubmitActivity = getIntent();
        //Get the name from main activity
        String input_username_string = SubmitActivity.getStringExtra(MainActivity.input_username_string_var);
        //Get the TextView object from the xml class
        TextView submit_message = (TextView) findViewById(R.id.submit_message);
        //Send our name to the TextView object to display on the screen
        String final_submit_message = getString(R.string.thanks) + input_username_string + getString(R.string.exclamation);
        submit_message.setText(final_submit_message);
    }
}
