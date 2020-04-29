package com.example.helloworld2;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<MainActivity>(MainActivity.class);
    @Test
    public void hasTextOnScreen(){
        onView(withId(R.id.hello_world))
                .check(matches(withText(R.string.hello_world)));
    }
    @Test
    public void hasFooter(){
        onView(withId(R.id.author))
                .check(matches(withText(R.string.author)));
    }
    @Test
    public void testTextInput(){
        onView(withId(R.id.input_name))
                .perform(typeText("Ben")).check(matches(withText("Ben")));
        onView(withId(R.id.input_email))
                .perform(typeText("Ben@gmail.com")).check(matches(withText("Ben@gmail.com")));
        onView(withId(R.id.input_username))
                .perform(typeText("Ben10")).check(matches(withText("Ben10")));
    }
}