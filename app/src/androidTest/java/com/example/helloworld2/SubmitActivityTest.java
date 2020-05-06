package com.example.helloworld2;

import android.content.Intent;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class SubmitActivityTest {
    @Rule
    public ActivityTestRule<SubmitActivity> activityTestRule
            = new ActivityTestRule<SubmitActivity>(SubmitActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Intent i = new Intent();
            i.putExtra(Constants.INPUT_FIRSTNAME, Constants.TEST_FIRST_NAME);
            i.putExtra(Constants.INPUT_OCCUPATION, "Welder");
            i.putExtra(Constants.INPUT_DESCRIPTION, "I like cooking and reading.");
            return i;
        }
    };
}