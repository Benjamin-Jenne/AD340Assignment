package com.example.helloworld2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //Referenced below video for help with DatePickerDialog listener
    //https://www.youtube.com/watch?v=E1LSY3g-CtY

    private Button button_dob;
    private Button button_submit;

    private EditText input_name;
    private EditText input_email;
    private EditText input_username;

    private TextView valid_dob;

    public static final String input_name_string_var = MainActivity.input_name_string_var;
    public static final String input_username_string_var = MainActivity.input_username_string_var;

    public static int month_var = MainActivity.month_var;
    public static int year_var = MainActivity.year_var;
    public static int day_var = MainActivity.day_var;

    DatePickerDialog.OnDateSetListener date_listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_dob = findViewById(R.id.button_dob);
        button_submit = findViewById(R.id.button_submit);

        input_name = findViewById(R.id.input_name);
        input_email = findViewById(R.id.input_email);
        input_username = findViewById(R.id.input_username);

        valid_dob = findViewById(R.id.valid_dob);

        button_dob.setOnClickListener(this);
        button_submit.setOnClickListener(this);

        date_listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month_var = month;
                year_var = year;
                day_var = dayOfMonth;
                String m = Integer.toString(month+1);
                String y = Integer.toString(year);
                String d = Integer.toString(dayOfMonth);
                String date = "month: " + m + ", year: " + y + ", day: " +d;
                Log.i("Date set", date);
                boolean valid = dobCheck(month, year, dayOfMonth);
                if(valid == false){
                    valid_dob.setText("\u2717 Must be 18 years or older");
                    valid_dob.setTextColor(Color.parseColor("#D8000C"));
                }
                if(valid == true){
                    valid_dob.setText("\u2713 18 years or older");
                    valid_dob.setTextColor(Color.parseColor("#4F8A10"));
                }
            }
        };
    }
    @Override
    public void onClick(View v){
        if(v.getId() == R.id.button_dob){
            Log.i("Date of birth button", "clicked");
            DatePickerDialog dpd = new DatePickerDialog(this, date_listener, 2000, 1,1 );
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
    public boolean dobCheck(int birth_month, int birth_year, int birth_day){
        Calendar c = Calendar.getInstance();
        int current_month = c.get(Calendar.MONTH);
        int current_day = c.get(Calendar.DAY_OF_MONTH);
        int current_year = c.get(Calendar.YEAR);
        int min_year = current_year -18;
        int min_month = current_month;
        int min_day = current_day;
        //The person is years younger than min year
        if(birth_year > min_year){
            return false;
        }
        //The person is same years as min year but they were born in an later month
        if((birth_year == min_year) && (birth_month > min_month)){
            return false;
        }
        //The person is same years and months as min but they were born on a later day
        if((birth_year == min_year) && (birth_month == min_month) && (birth_day > current_day)){
            return false;
        }
        else {
            return true;
        }
    }
    
}
