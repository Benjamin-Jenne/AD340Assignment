package com.example.helloworld2;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
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
            i.putExtra(Constants.INPUT_OCCUPATION, Constants.TEST_OCCUPATION);
            i.putExtra(Constants.INPUT_DESCRIPTION, Constants.TEST_DESCRIPTION);
            i.putExtra(Constants.AGE, Constants.TEST_AGE);
            return i;
        }
    };
    @Test
    public void testTitle() {
        onView(withId(R.id.nameAge))
                .check(matches(withText(Constants.TEST_FIRST_NAME + ", " + Constants.TEST_AGE)));
    }
    @Test
    public void testImage(){
        onView(withId(R.id.profile_image))
                .check(matches(isDisplayed()));
    }
    @Test
    public void testOccupation() {
        onView(withId(R.id.occupation))
                .check(matches(withText(Constants.TEST_OCCUPATION)));
    }
    @Test
    public void testDescription() {
        onView(withId(R.id.description))
                .check(matches(withText(Constants.TEST_DESCRIPTION)));
    }
    @Test
    public void RotateState(){
        activityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        onView(withId(R.id.nameAge))
                .check(matches(withText(Constants.TEST_FIRST_NAME + ", " + Constants.TEST_AGE)));
        onView(withId(R.id.occupation))
                .check(matches(withText(Constants.TEST_OCCUPATION)));
        onView(withId(R.id.description))
                .check(matches(withText(Constants.TEST_DESCRIPTION)));
    }
    @Test
    public void testMatchesTab(){
        onView(withText("MATCHES")).perform(click());
        onView(withId(R.id.matches_placeholder)).check(matches(withText(R.string.matches)));
    }
    @Test
    public void testSettingsTab(){
        onView(withText("SETTINGS")).perform(click());
        onView(withId(R.id.settings_placeholder)).check(matches(withText(R.string.settings)));
    }
}