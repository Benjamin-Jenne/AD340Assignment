package com.example.helloworld2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, android.text.TextWatcher {
    //Referenced below video for help with DatePickerDialog listener
    //https://www.youtube.com/watch?v=E1LSY3g-CtY

    private Button button_dob;
    private Button button_submit;

    private EditText input_firstname;
    private EditText input_lastname;
    private EditText input_email;
    private EditText input_username;
    private EditText input_occupation;
    private EditText input_description;

    private TextView valid_firstname;
    private TextView valid_lastname;
    private TextView valid_email;
    private TextView valid_username;
    private TextView valid_dob;
    private TextView valid_occupation;
    private TextView valid_description;

    public int birth_year;
    public int birth_month;
    public int birth_day;

    DatePickerDialog.OnDateSetListener date_listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input_firstname = findViewById(R.id.input_firstname);
        input_lastname = findViewById(R.id.input_lastname);
        input_email = findViewById(R.id.input_email);
        input_username = findViewById(R.id.input_username);
        input_occupation = findViewById(R.id.input_occupation);
        input_description = findViewById(R.id.input_description);

        button_dob = findViewById(R.id.button_dob);
        button_submit = findViewById(R.id.button_submit);

        valid_firstname = findViewById(R.id.valid_firstname);
        valid_lastname = findViewById(R.id.valid_lastname);
        valid_email = findViewById(R.id.valid_email);
        valid_username = findViewById(R.id.valid_username);
        valid_dob = findViewById(R.id.valid_dob);
        valid_occupation = findViewById(R.id.valid_occupation);
        valid_description = findViewById(R.id.valid_description);

        input_firstname.addTextChangedListener((TextWatcher) this);
        input_lastname.addTextChangedListener((TextWatcher) this);
        input_email.addTextChangedListener((TextWatcher) this);
        input_username.addTextChangedListener((TextWatcher) this);
        button_dob.setOnClickListener(this);
        button_submit.setOnClickListener(this);
        input_occupation.addTextChangedListener((TextWatcher) this);
        input_description.addTextChangedListener((TextWatcher) this);

        Calendar c = Calendar.getInstance();
        birth_year = c.get(Calendar.YEAR);
        birth_month = c.get(Calendar.MONTH);
        birth_day = c.get(Calendar.DAY_OF_MONTH);

        button_submit.setEnabled(false);

        date_listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selected_birth_year, int selected_birth_month, int selected_birth_day) {
                birth_year = selected_birth_year;
                birth_month = selected_birth_month;
                birth_day = selected_birth_day;
                if (inputCheck.dobCheck(birth_year, birth_month, birth_day) == false) {
                    valid_dob.setText(getResources().getString(R.string.invalid_dob));
                    valid_dob.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorFailure, null));
                    button_submit.setEnabled(false);
                }
                if (inputCheck.dobCheck(birth_year, birth_month, birth_day) == true) {
                    valid_dob.setText(getResources().getString(R.string.valid_dob));
                    valid_dob.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorSuccess, null));
                    setButtonSubmitEnabled();
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_dob) {
            DatePickerDialog dpd = new DatePickerDialog(this, date_listener, birth_year, birth_month, birth_day);
            dpd.show();
        }
        if (v.getId() == R.id.button_submit) {
            //Create intent to go to submit activity class
            Intent submitActivity = new Intent(MainActivity.this, SubmitActivity.class);
            //Add the name to the intent
            submitActivity.putExtra(Constants.INPUT_FIRSTNAME, input_firstname.getText().toString());
            submitActivity.putExtra(Constants.INPUT_OCCUPATION, input_occupation.getText().toString());
            submitActivity.putExtra(Constants.INPUT_DESCRIPTION, input_description.getText().toString());
            submitActivity.putExtra(Constants.AGE, Integer.toString(inputCheck.getAge(birth_year,birth_month,birth_day)));
            startActivity(submitActivity);
        }
    }

    public void setButtonSubmitEnabled() {
        if (inputCheck.nameCheck(input_firstname.getText().toString()).equals(Constants.VALID) &&
                inputCheck.nameCheck(input_lastname.getText().toString()).equals(Constants.VALID) &&
                inputCheck.emailCheck(input_email.getText().toString()) == true &&
                inputCheck.userNameCheck(input_username.getText().toString()).equals(Constants.VALID) &&
                inputCheck.dobCheck(birth_year, birth_month, birth_day) == true &&
                inputCheck.occupationCheck(input_occupation.getText().toString()).equals(Constants.VALID) &&
                inputCheck.descriptionCheck(input_description.getText().toString()).equals(Constants.VALID)) {
            button_submit.setEnabled(true);
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey(Constants.BIRTH_YEAR) &&
                savedInstanceState.containsKey(Constants.BIRTH_MONTH) &&
                savedInstanceState.containsKey(Constants.BIRTH_DAY)) {
            birth_year = savedInstanceState.getInt(Constants.BIRTH_YEAR);
            birth_month = savedInstanceState.getInt(Constants.BIRTH_MONTH);
            birth_day = savedInstanceState.getInt(Constants.BIRTH_DAY);
        }
        if (inputCheck.dobCheck(birth_year, birth_month, birth_day) == false) {
            valid_dob.setText(getResources().getString(R.string.invalid_dob));
            valid_dob.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorFailure, null));
            button_submit.setEnabled(false);
        }
        if (inputCheck.dobCheck(birth_year, birth_month, birth_day) == true) {
            valid_dob.setText(getResources().getString(R.string.valid_dob));
            valid_dob.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorSuccess, null));
            setButtonSubmitEnabled();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants.BIRTH_YEAR, birth_year);
        outState.putInt(Constants.BIRTH_MONTH, birth_month);
        outState.putInt(Constants.BIRTH_DAY, birth_day);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        input_firstname.getText().clear();
        input_lastname.getText().clear();
        input_email.getText().clear();
        input_username.getText().clear();
        input_occupation.getText().clear();
        input_description.getText().clear();
        birth_year = 2000;
        birth_month = 0;
        birth_day = 1;
        valid_username.setText("");
        valid_firstname.setText("");
        valid_lastname.setText("");
        valid_email.setText("");
        valid_dob.setText("");
        valid_occupation.setText("");
        valid_description.setText("");
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (inputCheck.nameCheck(input_firstname.getText().toString()).equals(Constants.VALID)) {
            valid_firstname.setText("");
            setButtonSubmitEnabled();
        }
        if (inputCheck.nameCheck(input_firstname.getText().toString()).equals(Constants.EMPTY)) {
            valid_firstname.setText(getResources().getString(R.string.enter_firstname));
            valid_firstname.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorFailure, null));
            button_submit.setEnabled(false);
        }
        if (inputCheck.nameCheck(input_firstname.getText().toString()).equals(Constants.NON_LETTER)) {
            valid_firstname.setText(getResources().getString(R.string.letters));
            valid_firstname.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorFailure, null));
            setButtonSubmitEnabled();
        }
        if (inputCheck.nameCheck(input_lastname.getText().toString()).equals(Constants.VALID)) {
            valid_lastname.setText("");
            setButtonSubmitEnabled();
        }
        if (inputCheck.nameCheck(input_lastname.getText().toString()).equals(Constants.EMPTY)) {
            valid_lastname.setText(getResources().getString(R.string.enter_lastname));
            valid_lastname.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorFailure, null));
            button_submit.setEnabled(false);
        }
        if (inputCheck.nameCheck(input_lastname.getText().toString()).equals(Constants.NON_LETTER)) {
            valid_lastname.setText(getResources().getString(R.string.letters));
            valid_lastname.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorFailure, null));
            button_submit.setEnabled(false);
        }
        if (inputCheck.emailCheck(input_email.getText().toString()) == true) {
            valid_email.setText("");
            setButtonSubmitEnabled();
        }
        if (inputCheck.emailCheck(input_email.getText().toString()) == false) {
            valid_email.setText(getResources().getString(R.string.enter_email));
            valid_email.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorFailure, null));
            button_submit.setEnabled(false);
        }
        if (inputCheck.userNameCheck(input_username.getText().toString()).equals(Constants.VALID)) {
            valid_username.setText("");
            setButtonSubmitEnabled();
        }
        if (inputCheck.userNameCheck(input_username.getText().toString()).equals(Constants.EMPTY)) {
            valid_username.setText(getResources().getString(R.string.enter_username));
            valid_username.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorFailure, null));
            button_submit.setEnabled(false);
        }
        if (inputCheck.occupationCheck(input_occupation.getText().toString()).equals(Constants.VALID)) {
            valid_occupation.setText("");
            setButtonSubmitEnabled();
        }
        if (inputCheck.occupationCheck(input_occupation.getText().toString()).equals(Constants.NON_LETTER)) {
            valid_occupation.setText(getResources().getString(R.string.letters));
            valid_occupation.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorFailure, null));
            button_submit.setEnabled(false);
        }
        if (inputCheck.occupationCheck(input_occupation.getText().toString()).equals(Constants.EMPTY)) {
            valid_occupation.setText(getResources().getString(R.string.enter_username));
            valid_occupation.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorFailure, null));
            button_submit.setEnabled(false);
        }
        if (inputCheck.descriptionCheck(input_description.getText().toString()).equals(Constants.VALID)) {
            valid_description.setText("");
            setButtonSubmitEnabled();
        }
        if (inputCheck.descriptionCheck(input_description.getText().toString()).equals(Constants.NON_LETTER)) {
            valid_description.setText(getResources().getString(R.string.letters_or_numbers));
            valid_description.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorFailure, null));
            button_submit.setEnabled(false);
        }
        if (inputCheck.descriptionCheck(input_description.getText().toString()).equals(Constants.EMPTY)) {
            valid_description.setText(getResources().getString(R.string.enter_description));
            valid_description.setTextColor(ResourcesCompat.getColor(getResources(), R.color.colorFailure, null));
            button_submit.setEnabled(false);
        }
    }
}