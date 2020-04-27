package com.example.helloworld2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button button_dob;
    private Button button_submit;
    private EditText input_name;
    private EditText input_email;
    private EditText input_username;
    public static final String input_name_string_var = MainActivity.input_name_string_var;
    //public String input_email_string;
    public static final String input_username_string_var = MainActivity.input_username_string_var;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_dob = findViewById(R.id.button_dob);
        button_submit = findViewById(R.id.button_submit);

        input_name = findViewById(R.id.input_name);
        input_email = findViewById(R.id.input_email);
        input_username = findViewById(R.id.input_username);

        button_dob.setOnClickListener(this);
        button_submit.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        if(v.getId() == R.id.button_dob){
            Log.i("Date of birth button", "clicked");
            DatePickerDialog dpd = new DatePickerDialog(this);
            dpd.show();
        }
        if(v.getId() == R.id.button_submit){
            Log.i("Submit button", "clicked");
            String input_name_string = input_name.getText().toString();
            String input_email_string = input_email.getText().toString();
            String input_username_string = input_username.getText().toString();
            Log.i("input_name_string:", input_name_string);
            //Create intent to go to submit activity class
            Intent submitActivity = new Intent(MainActivity.this, SubmitActivity.class);
            //Add the name to the intent
            submitActivity.putExtra(input_username_string_var, input_username_string);
            startActivity(submitActivity);
        }
    }
}
