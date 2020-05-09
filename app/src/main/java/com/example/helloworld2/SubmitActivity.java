package com.example.helloworld2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class SubmitActivity extends AppCompatActivity {
    private ImageView profile_image;
    private TextView nameAge;
    private TextView occupation;
    private TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        Intent SubmitActivity = getIntent();

        profile_image = findViewById(R.id.profile_image);
        nameAge = findViewById(R.id.nameAge);
        occupation = findViewById(R.id.occupation);
        description = findViewById(R.id.description);

        String nameAgeString = SubmitActivity.getStringExtra(Constants.INPUT_FIRSTNAME) + ", " + SubmitActivity.getStringExtra(Constants.AGE);
        nameAge.setText(nameAgeString);
        occupation.setText(Constants.INPUT_OCCUPATION);
        description.setText(Constants.INPUT_DESCRIPTION);
    }
}
