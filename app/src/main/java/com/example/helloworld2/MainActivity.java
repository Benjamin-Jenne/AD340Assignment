package com.example.helloworld2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, android.text.TextWatcher{
    //Referenced below video for help with DatePickerDialog listener
    //https://www.youtube.com/watch?v=E1LSY3g-CtY

    private Button button_dob;
    private Button button_submit;

    private EditText input_firstname;
    private EditText input_lastname;
    private EditText input_email;
    private EditText input_username;

    private TextView valid_firstname;
    private TextView valid_lastname;
    private TextView valid_email;
    private TextView valid_username;
    private TextView valid_dob;

    public int birth_year = 2000;
    public int birth_month = 0;
    public int birth_day = 1;

    DatePickerDialog.OnDateSetListener date_listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input_firstname = findViewById(R.id.input_firstname);
        input_lastname = findViewById(R.id.input_lastname);
        input_email = findViewById(R.id.input_email);
        input_username = findViewById(R.id.input_username);

        button_dob = findViewById(R.id.button_dob);
        button_submit = findViewById(R.id.button_submit);

        valid_firstname = findViewById(R.id.valid_firstname);
        valid_lastname = findViewById(R.id.valid_lastname);
        valid_email = findViewById(R.id.valid_email);
        valid_username = findViewById(R.id.valid_username);
        valid_dob = findViewById(R.id.valid_dob);

        input_firstname.addTextChangedListener((TextWatcher) this);
        input_lastname.addTextChangedListener((TextWatcher) this);
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
                if(inputCheck.dobCheck(birth_year, birth_month, birth_day) == false){
                    valid_dob.setText(getResources().getString(R.string.invalid_dob));
                    valid_dob.setTextColor(Color.parseColor("#D8000C"));
                    button_submit.setEnabled(false);
                }
                if(inputCheck.dobCheck(birth_year, birth_month, birth_day) == true){
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
        if(inputCheck.nameCheck(input_firstname.getText().toString()).equals("valid") &&
                inputCheck.nameCheck(input_lastname.getText().toString()).equals("valid") &&
                inputCheck.emailCheck(input_email.getText().toString()) == true &&
                    inputCheck.userNameCheck(input_username.getText().toString()).equals("valid") &&
                        inputCheck.dobCheck(birth_year, birth_month, birth_day) == true){
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
        if(inputCheck.dobCheck(birth_year, birth_month, birth_day) == false){
            valid_dob.setText(getResources().getString(R.string.invalid_dob));
            valid_dob.setTextColor(Color.parseColor("#D8000C"));
            button_submit.setEnabled(false);
        }
        if(inputCheck.dobCheck(birth_year, birth_month, birth_day) == true){
            valid_dob.setText(getResources().getString(R.string.valid_dob));
            valid_dob.setTextColor(Color.parseColor("#4F8A10"));
            setButtonSubmitEnabled();
        }
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
            outState.putInt("birth_year", birth_year);
            outState.putInt("birth_month", birth_month);
            outState.putInt("birth_day", birth_day);
    }
    @Override
    protected void onRestart(){
        super.onRestart();
        Log.i("Main Activity: ", "onRestart()");
        input_firstname.getText().clear();
        input_lastname.getText().clear();
        input_email.getText().clear();
        input_username.getText().clear();
        birth_year = 2000;
        birth_month = 0;
        birth_day = 1;
        valid_username.setText("");
        valid_firstname.setText("");
        valid_lastname.setText("");
        valid_email.setText("");
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
        if(inputCheck.nameCheck(input_firstname.getText().toString()).equals("valid")){
            valid_firstname.setText("");
            setButtonSubmitEnabled();
        }
        if(inputCheck.nameCheck(input_firstname.getText().toString()).equals("empty")){
            valid_firstname.setText("Please enter your first name");
            valid_firstname.setTextColor(Color.parseColor("#D8000C"));
            button_submit.setEnabled(false);
        }
        if(inputCheck.nameCheck(input_firstname.getText().toString()).equals("non_letter")){
            valid_firstname.setText("Please only enter letters");
            valid_firstname.setTextColor(Color.parseColor("#D8000C"));
            setButtonSubmitEnabled();
        }
        if(inputCheck.nameCheck(input_lastname.getText().toString()).equals("valid")){
            valid_lastname.setText("");
            setButtonSubmitEnabled();
        }
        if(inputCheck.nameCheck(input_lastname.getText().toString()).equals("empty")){
            valid_lastname.setText("Please enter your last name");
            valid_lastname.setTextColor(Color.parseColor("#D8000C"));
            button_submit.setEnabled(false);
        }
        if(inputCheck.nameCheck(input_lastname.getText().toString()).equals("non_letter")){
            valid_lastname.setText("Please only enter letters");
            valid_lastname.setTextColor(Color.parseColor("#D8000C"));
            setButtonSubmitEnabled();
        }
        if(inputCheck.emailCheck(input_email.getText().toString()) == true){
            valid_email.setText("");
            setButtonSubmitEnabled();
        }
        if(inputCheck.emailCheck(input_email.getText().toString()) == false){
            valid_email.setText("Please enter a valid email");
            valid_email.setTextColor(Color.parseColor("#D8000C"));
            button_submit.setEnabled(false);
        }
        if(inputCheck.userNameCheck(input_username.getText().toString()).equals("valid")){
            valid_username.setText("");
            setButtonSubmitEnabled();
        }
        if(inputCheck.userNameCheck(input_username.getText().toString()).equals("empty")){
            valid_username.setText("Please enter your username");
            valid_username.setTextColor(Color.parseColor("#D8000C"));
            button_submit.setEnabled(false);
        }
    }
}
