package com.example.helloworld2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {
    private TextView desiredMinAgeText;
    private TextView desiredMaxAgeText;
    private TextView desiredRangeText;
    private SeekBar desiredMinAgeBar;
    private SeekBar desiredMaxAgeBar;
    private SeekBar desiredRange;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_settings, null);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        desiredMinAgeText = getActivity().findViewById(R.id.desired_Min_Age_Text);
        desiredMaxAgeText = getActivity().findViewById(R.id.desired_Max_Age_Text);
        desiredRangeText = getActivity().findViewById(R.id.desired_Range_Text);
        desiredMinAgeText.setText(R.string.desired_min_age);
        desiredMaxAgeText.setText(R.string.desired_max_age);
        desiredRangeText.setText(R.string.desired_range);
        desiredMinAgeBar = getActivity().findViewById(R.id.desired_Min_Age_Bar);
        desiredMaxAgeBar = getActivity().findViewById(R.id.desired_Max_Age_Bar);
        desiredRange = getActivity().findViewById(R.id.desired_Range_Bar);
    }
}
