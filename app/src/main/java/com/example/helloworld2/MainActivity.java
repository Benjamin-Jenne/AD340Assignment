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
import android.text.Editable;
import android.text.TextWatcher;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener, android.text.TextWatcher{
    //Referenced below video for help with DatePickerDialog listener
    //https://www.youtube.com/watch?v=E1LSY3g-CtY

    private Button button_dob;
    private Button button_submit;

    private EditText input_name;
    private EditText input_email;
    private EditText input_username;
    private TextView valid_dob;

    public String date_message;

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

        input_name.addTextChangedListener((TextWatcher) this);
        input_email.addTextChangedListener((TextWatcher) this);
        input_username.addTextChangedListener((TextWatcher) this);
        button_dob.setOnClickListener(this);
        button_submit.setOnClickListener(this);

        button_submit.setEnabled(false);

        date_listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selected_birth_year, int selected_birth_month, int selected_birth_day) {
                birth_year = selected_birth_year;
                birth_month = selected_birth_month;
                birth_day = selected_birth_day;
                is_valid_dob = inputCheck.dobCheck(birth_year, birth_month, birth_day);
                if(is_valid_dob == false){
                    valid_dob.setText(getResources().getString(R.string.invalid_dob));
                    valid_dob.setTextColor(Color.parseColor("#D8000C"));
                }
                if(is_valid_dob == true){
                    valid_dob.setText(getResources().getString(R.string.valid_dob));
                    valid_dob.setTextColor(Color.parseColor("#4F8A10"));
                    setButtonSubmitEnabled();
                }
            }
        };
    }
    @Override
    public void onClick(View v){
        if(v.getId() == R.id.button_dob){
            DatePickerDialog dpd = new DatePickerDialog(this, date_listener, birth_year, birth_month,birth_day);
            dpd.show();
        }
        if(v.getId() == R.id.button_submit){
            //Create intent to go to submit activity class
            Intent submitActivity = new Intent(MainActivity.this, SubmitActivity.class);
            //Add the name to the intent
            submitActivity.putExtra("input_username", input_username.getText().toString());
            startActivity(submitActivity);
        }
    }
    public void setButtonSubmitEnabled(){
        if(is_valid_name == true && is_valid_email == true && is_valid_username == true && is_valid_dob == true){
            button_submit.setEnabled(true);
        }
    }
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
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
            outState.putBoolean("is_valid_dob",is_valid_dob);
    }
    @Override
    protected void onRestart(){
        super.onRestart();
        Log.i("Main Activity: ", "onRestart()");
        input_name.getText().clear();
        input_email.getText().clear();
        input_username.getText().clear();
        birth_year = 2000;
        birth_month = 0;
        birth_day = 1;
        valid_dob.setText("");
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }
    @Override
    public void afterTextChanged(Editable s) {
        String input_name_string = input_name.getText().toString();
        String input_email_string = input_email.getText().toString();
        String input_username_string = input_username.getText().toString();
        String is_valid_name = 
    }
}
