package com.example.helloworld2;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.os.Bundle;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment{
    private TextView nameAge;
    private TextView occupation;
    private TextView description;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_profile, null);

        nameAge = getView().findViewById(R.id.nameAge);
        occupation = getView().findViewById(R.id.occupation);
        description = getView().findViewById(R.id.description);

        return view;
    }
}
