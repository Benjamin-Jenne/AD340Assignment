package com.example.helloworld2;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.PersistableBundle;
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
    private Boolean is_valid_dob = false;

    public String date_message;

    public static final String input_name_string_var = MainActivity.input_name_string_var;
    public static final String input_username_string_var = MainActivity.input_username_string_var;

    public static int birth_year = 2000;
    public static int birth_month = 0;
    public static int birth_day = 1;

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
            public void onDateSet(DatePicker view, int selected_birth_year, int selected_birth_month, int selected_birth_day) {
                birth_year = selected_birth_year;
                birth_month = selected_birth_month;
                birth_day = selected_birth_day;
                String y = Integer.toString(birth_year);
                String m = Integer.toString(birth_month);
                String d = Integer.toString(birth_day);
                String date = "birth_year: " + y + ", birth_month: " + m + ", birth_day: " +d;
                Log.i("Date set", date);
                is_valid_dob = dobCheck(birth_year, birth_month, birth_day);
                if(is_valid_dob == false){
                    valid_dob.setText(getResources().getString(R.string.invalid_dob));
                    valid_dob.setTextColor(Color.parseColor("#D8000C"));
                    button_submit.setEnabled(false);
                }
                if(is_valid_dob == true){
                    valid_dob.setText(getResources().getString(R.string.valid_dob));
                    valid_dob.setTextColor(Color.parseColor("#4F8A10"));
                    button_submit.setEnabled(true);
                }
            }
        };
    }
    @Override
    public void onClick(View v){
        if(v.getId() == R.id.button_dob){
            Log.i("Date of birth button", "clicked");
            DatePickerDialog dpd = new DatePickerDialog(this, date_listener, birth_year, birth_month,birth_day);
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
    public boolean dobCheck(int birth_year, int birth_month, int birth_day){
        Calendar c = Calendar.getInstance();
        int current_year = c.get(Calendar.YEAR);
        int current_month = c.get(Calendar.MONTH);
        int current_day = c.get(Calendar.DAY_OF_MONTH);
        int min_year = current_year -18;
        int min_month = current_month;
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
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("onrestpreinstancestate - is_valid_dob", Boolean.toString(savedInstanceState.getBoolean("is_valid_dob")));
        if(savedInstanceState.containsKey("birth_year") &&
                savedInstanceState.containsKey("birth_month") &&
                    savedInstanceState.containsKey("birth_day")){
            birth_year = savedInstanceState.getInt("birth_year");
            birth_month = savedInstanceState.getInt("birth_month");
            birth_day = savedInstanceState.getInt("birth_day");
        }
            if(savedInstanceState.getBoolean("is_valid_dob")== false){
                valid_dob.setText(getResources().getString(R.string.invalid_dob));
                valid_dob.setTextColor(Color.parseColor("#D8000C"));
                button_submit.setEnabled(false);
            }
            if(savedInstanceState.getBoolean("is_valid_dob")== true){
               valid_dob.setText(getResources().getString(R.string.valid_dob));
               valid_dob.setTextColor(Color.parseColor("#4F8A10"));
               button_submit.setEnabled(true);
            }
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
            outState.putInt("birth_year", birth_year);
            outState.putInt("birth_month", birth_month);
            outState.putInt("birth_day", birth_day);
            Log.i("onsaveinstancestate - is_valid_dob", Boolean.toString(is_valid_dob));
            outState.putBoolean("is_valid_dob",is_valid_dob);
    }
}
