package com.example.helloworld2;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import androidx.test.rule.ActivityTestRule;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
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
    //Referenced https://spin.atomicobject.com/2016/04/15/espresso-testing-recyclerviews/
    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }
    @Test
    //Referenced https://stackoverflow.com/questions/27396583/how-to-click-on-an-item-inside-a-recyclerview-in-espresso
    public void testMatchesTab(){
        onView(withText("MATCHES")).perform(click());
        onView(withId(R.id.my_recycler_view)).perform(actionOnItemAtPosition(1, click()));
        onView(withId(R.id.my_recycler_view)).perform(actionOnItemAtPosition(2, click()));
        onView(withId(R.id.my_recycler_view)).perform(actionOnItemAtPosition(3, click()));
        onView(withId(R.id.my_recycler_view)).perform(actionOnItemAtPosition(4, click()));
        onView(withId(R.id.my_recycler_view)).perform(actionOnItemAtPosition(5, click()));
    }
    @Test
    public void testSettingsTab(){
        onView(withText("SETTINGS")).perform(click());
        onView(withId(R.id.settings_placeholder)).check(matches(withText(R.string.settings)));
    }
}