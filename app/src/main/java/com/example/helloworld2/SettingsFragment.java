package com.example.helloworld2;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.firestore.auth.User;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Observer;

public class SettingsFragment extends Fragment implements SeekBar.OnSeekBarChangeListener, Switch.OnCheckedChangeListener{
    //Textviews
    private TextView MinAgeText;
    private TextView MaxAgeText;
    private TextView RangeText;
    private TextView AccountPrivacyText;
    private TextView DailyReminderText;
    private TextView setMinAgeText;
    private TextView setMaxAgeText;
    private TextView setRangeText;
    private TextView setPublicText;
    private TextView setDailyReminderText;

    //Seekbars
    private SeekBar MinAgeBar;
    private SeekBar MaxAgeBar;
    private SeekBar RangeBar;

    //Switch
    private Switch AccountPrivacySwitch;
    private Switch DailyReminderSwitch;

    //Reminder Time
    private int reminder_hour = 1;
    private int reminder_minute = 1;
    private boolean set_reminder = false;

    //Time Listener
    TimePickerDialog.OnTimeSetListener time_listener;

    //View model
    private SettingsViewModel settingsViewModel;
    private Settings settings;

    //

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_settings, null);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //Get Textviews
        MinAgeText = getActivity().findViewById(R.id.Min_Age_Text);
        MaxAgeText = getActivity().findViewById(R.id.Max_Age_Text);
        RangeText = getActivity().findViewById(R.id.Range_Text);
        AccountPrivacyText = getActivity().findViewById(R.id.Account_Privacy_Text);
        DailyReminderText = getActivity().findViewById(R.id.Daily_Reminder_Text);
        setMinAgeText = getActivity().findViewById(R.id.set_Min_Age_Text);
        setMaxAgeText = getActivity().findViewById(R.id.set_Max_Age_Text);
        setRangeText = getActivity().findViewById(R.id.set_Range_Text);
        setPublicText = getActivity().findViewById(R.id.set_Public_Text);
        setDailyReminderText = getActivity().findViewById(R.id.set_Daily_Reminder_Text);

        //Set Textviews
        MinAgeText.setText(getResources().getString(R.string.min_age));
        MaxAgeText.setText(getResources().getString(R.string.max_age));
        RangeText.setText(getResources().getString(R.string.range));
        AccountPrivacyText.setText(getResources().getString(R.string.account_privacy));
        DailyReminderText.setText(getResources().getString(R.string.daily_reminder));

        setMinAgeText.setText(getResources().getString(R.string.your_min_age) + " " + Integer.toString(18));
        setMaxAgeText.setText(getResources().getString(R.string.your_max_age) + " " + Integer.toString(18));
        setRangeText.setText(getResources().getString(R.string.your_range)+ " " + Integer.toString(1) + " " + getResources().getString(R.string.miles));
        setPublicText.setText(getResources().getString(R.string.your_acount_is) + " " + getResources().getString(R.string.is_public));
        setDailyReminderText.setText(getResources().getString(R.string.reminder_off));

        //Get SeekBars
        MinAgeBar = getActivity().findViewById(R.id.Min_Age_Bar);
        MaxAgeBar = getActivity().findViewById(R.id.Max_Age_Bar);
        RangeBar = getActivity().findViewById(R.id.Range_Bar);

        //Set listeners for seekbars
        MinAgeBar.setOnSeekBarChangeListener((SeekBar.OnSeekBarChangeListener) this);
        MaxAgeBar.setOnSeekBarChangeListener((SeekBar.OnSeekBarChangeListener) this);
        RangeBar.setOnSeekBarChangeListener((SeekBar.OnSeekBarChangeListener) this);

        //Get switch
        AccountPrivacySwitch = getActivity().findViewById(R.id.Account_Privacy_Switch);
        DailyReminderSwitch = getActivity().findViewById(R.id.Daily_Reminder_Switch);

        //Set listeners for switch
        AccountPrivacySwitch.setOnCheckedChangeListener((Switch.OnCheckedChangeListener) this);
        DailyReminderSwitch.setOnCheckedChangeListener((Switch.OnCheckedChangeListener) this);

        //Create new Settings instance and a View Model for Room Persistence
        settings = new Settings();
        settings.id = 0;
        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        //Time Picker
        time_listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hours, int minutes) {
                reminder_hour = hours;
                reminder_minute = minutes;
                if(reminder_hour <= 12){
                    if(reminder_minute<10){
                        setDailyReminderText.setText(getResources().getString(R.string.reminder_on) + " " + Integer.toString(reminder_hour) + ":" + "0" + Integer.toString(reminder_minute) + "am");
                    }
                    else{
                        setDailyReminderText.setText(getResources().getString(R.string.reminder_on) + " " + Integer.toString(reminder_hour) + ":" + Integer.toString(reminder_minute) + "am");
                    }
                }
                if(reminder_hour > 12){
                    if(reminder_minute<10){
                        setDailyReminderText.setText(getResources().getString(R.string.reminder_on) + " " + Integer.toString(reminder_hour - 12) + ":" + "0" + Integer.toString(reminder_minute) + "pm");
                    }
                    else{
                        setDailyReminderText.setText(getResources().getString(R.string.reminder_on) + " " + Integer.toString(reminder_hour - 12) + ":" + Integer.toString(reminder_minute) + "pm");
                    }
                }
                settings.setReminderHour(reminder_hour);
                settings.setReminderHour(reminder_minute);
                applySettings();
            }
        };
    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
        if (seekBar.getId() == R.id.Min_Age_Bar) {
            int min_age = progress + 18;
            String min_age_string = Integer.toString(min_age);
            setMinAgeText.setText(getResources().getString(R.string.your_min_age) + " " + min_age_string);
            settings.setMinAge(min_age);
        }
        if (seekBar.getId() == R.id.Max_Age_Bar) {
            int max_age = progress + 18;
            String max_age_string = Integer.toString(max_age);
            setMaxAgeText.setText(getResources().getString(R.string.your_max_age) + " " + max_age_string);
            settings.setMaxAge(max_age);
        }
        if (seekBar.getId() == R.id.Range_Bar) {
            int range = progress + 18;
            String range_string = Integer.toString(range);
            setRangeText.setText(getResources().getString(R.string.your_range)+ " " + range_string + " " + getResources().getString(R.string.miles));
            settings.setMinAge(range);
        }
        applySettings();
    }
    @Override
    public void onStartTrackingTouch(SeekBar seekBar){

    }
    @Override
    public void onStopTrackingTouch(SeekBar seekBar){

    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView.getId() == R.id.Account_Privacy_Switch){
            if(isChecked == true){
                setPublicText.setText(getResources().getString(R.string.your_acount_is) + " " + getResources().getString(R.string.is_private));
                settings.setIsPrivate(true);
            }
            else{
                setPublicText.setText(getResources().getString(R.string.your_acount_is) + " " + getResources().getString(R.string.is_public));
                settings.setIsPrivate(false);
            }
        }
        if(buttonView.getId() == R.id.Daily_Reminder_Switch){
            if(isChecked == true){
                TimePickerDialog tpd = new TimePickerDialog(getContext(), time_listener, reminder_hour, reminder_minute, false);
                tpd.show();
                settings.setDailyReminder(true);
            }
            else{ //isChecked == false
                setDailyReminderText.setText(getResources().getString(R.string.reminder_off));
                set_reminder = false;
                reminder_minute = 1;
                reminder_hour = 1;
                settings.setDailyReminder(false);
                settings.setReminderHour(1);
                settings.setReminderMinutes(1);
            }
        }
        applySettings();
    }
    @Override
    public void onActivityCreated(@NonNull Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try{
            reminder_hour = savedInstanceState.getInt(Constants.REMINDER_HOUR);
            reminder_minute = savedInstanceState.getInt(Constants.REMINDER_MINUTE);
            set_reminder = savedInstanceState.getBoolean(Constants.SET_REMINDER);
        }
        catch(NullPointerException e){
            Log.i("Inside onActivityCreated", "caught the exception");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants.REMINDER_HOUR, reminder_hour);
        outState.putInt(Constants.REMINDER_MINUTE, reminder_minute);
        outState.putBoolean(Constants.SET_REMINDER, set_reminder);
    }
    public void applySettings(){
        settingsViewModel.insert(getContext(), settings);
    }
}

