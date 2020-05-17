package com.example.helloworld2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment{
    private TextView nameAge;
    private TextView occupation;
    private TextView description;
    private SubmitActivity.Operation operation;
    private String TAG = "Inside Profile Fragment";

    private String nameAgeString;
    private String occupationString;
    private String descriptionString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_profile, null);
        nameAge = view.findViewById(R.id.nameAge);
        occupation = view.findViewById(R.id.occupation);
        description = view.findViewById(R.id.description);
        try {
            nameAgeString = operation.nameAge;
            occupationString = operation.occupation;
            descriptionString = operation.description;

            nameAge.setText(nameAgeString);
            occupation.setText(occupationString);
            description.setText(descriptionString);
        } catch(NullPointerException e) {
        }
        return view;
    }
    public void setOperation(SubmitActivity.Operation operation){
        this.operation = operation;
    }
    @Override
    public void onActivityCreated(@NonNull Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            if(nameAgeString.equals(null)) {
            }
        } catch(NullPointerException e) {
            nameAgeString = savedInstanceState.getString(Constants.NAME_AGE);
            occupationString = savedInstanceState.getString(Constants.INPUT_OCCUPATION);
            descriptionString = savedInstanceState.getString(Constants.INPUT_DESCRIPTION);
            nameAge.setText(nameAgeString);
            occupation.setText(occupationString);
            description.setText(descriptionString);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.NAME_AGE, nameAgeString);
        outState.putString(Constants.INPUT_OCCUPATION, occupationString);
        outState.putString(Constants.INPUT_DESCRIPTION, descriptionString);
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

}
