package com.example.helloworld2;

import android.util.Log;
import android.widget.DatePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;


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
        onView(withId(R.id.input_firstname))
                .perform(typeText("Benjamin")).check(matches(withText("Benjamin")));
        onView(withId(R.id.input_lastname))
                .perform(typeText("Jenne")).check(matches(withText("Jenne")));
        onView(withId(R.id.input_email))
                .perform(typeText("jenne.ben.e@gmail.com")).check(matches(withText("jenne.ben.e@gmail.com")));
        onView(withId(R.id.input_username))
                .perform(typeText("Ben10")).check(matches(withText("Ben10")));
    }
    @Test
    public void emptyTextWarning(){
        onView(withId(R.id.input_firstname)).perform(typeText("Ben"));
        onView(withId(R.id.input_firstname)).perform(clearText());
        onView(withId(R.id.valid_firstname)).check(matches(withText("Please enter your first name")));
        onView(withId(R.id.input_lastname)).perform(typeText("Ben"));
        onView(withId(R.id.input_lastname)).perform(clearText());
        onView(withId(R.id.valid_lastname)).check(matches(withText("Please enter your last name")));
        onView(withId(R.id.input_email)).perform(typeText("jenne.ben.e@gmail.com"));
        onView(withId(R.id.input_email)).perform(clearText());
        onView(withId(R.id.valid_email)).check(matches(withText("Please enter a valid email")));
        onView(withId(R.id.input_username)).perform(typeText("ben10"));
        onView(withId(R.id.input_username)).perform(clearText());
        onView(withId(R.id.valid_username)).check(matches(withText("Please enter your username")));
    }
    @Test
    public void badNameInput(){
        onView(withId(R.id.input_firstname)).perform(typeText("Ben134"));
        onView(withId(R.id.valid_firstname)).check(matches(withText("Please only enter letters")));
        onView(withId(R.id.input_lastname)).perform(typeText("Jenne134"));
        onView(withId(R.id.valid_lastname)).check(matches(withText("Please only enter letters")));
    }
    @Test
    public void badEmailInput(){
        onView(withId(R.id.input_email)).perform(typeText("Ben"));
        onView(withId(R.id.input_email)).perform(clearText());
        onView(withId(R.id.valid_email)).check(matches(withText("Please enter a valid email")));
        onView(withId(R.id.input_email)).perform(typeText("ben.ben@ben"));
        onView(withId(R.id.input_email)).perform(clearText());
        onView(withId(R.id.valid_email)).check(matches(withText("Please enter a valid email")));
        onView(withId(R.id.input_email)).perform(typeText("a@.com"));
        onView(withId(R.id.input_email)).perform(clearText());
        onView(withId(R.id.valid_email)).check(matches(withText("Please enter a valid email")));
    }
    @Test
    public void testDatePickerTooYoung(){
        //Referenced https://stackoverflow.com/questions/43149728/select-date-from-calendar-in-android-espresso
        //Born years too early
        Calendar c = Calendar.getInstance();
        int currentDay = c.get(Calendar.DAY_OF_MONTH);
        int currentMonth = c.get(Calendar.MONTH) + 1;
        int currentYear = c.get(Calendar.YEAR);
        Log.i("currentDay: ", Integer.toString(currentDay));
        Log.i("currentMonth: ", Integer.toString(currentMonth));
        Log.i("currentYear: ", Integer.toString(currentYear));
        onView(withId(R.id.button_dob)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(currentYear - 10, 1, 1));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.valid_dob)).check(matches(withText("✗ Must be 18 years or older")));
        //Born months too early
        onView(withId(R.id.button_dob)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(currentYear-18, currentMonth + 3, 1));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.valid_dob)).check(matches(withText("✗ Must be 18 years or older")));
        //Born days too early
        onView(withId(R.id.button_dob)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(currentYear-18, currentMonth, currentDay + 3));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.valid_dob)).check(matches(withText("✗ Must be 18 years or older")));
    }
    @Test
    public void testDatePickerOldEnough(){
        //Referenced https://stackoverflow.com/questions/43149728/select-date-from-calendar-in-android-espresso
        onView(withId(R.id.button_dob)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(1994, 4, 4));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.valid_dob)).check(matches(withText("\u2713 18 years or older")));
        onView(withId(R.id.button_dob)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2002, 5, 5));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.valid_dob)).check(matches(withText("\u2713 18 years or older")));
    }
    @Test
    public void submitButtonDisabledBadInput(){
        onView(withId(R.id.button_submit)).check(matches(not(isEnabled())));
        onView(withId(R.id.input_firstname)).perform(typeText("Ben"));
        onView(withId(R.id.input_firstname)).perform(clearText());
        onView(withId(R.id.button_submit)).check(matches(not(isEnabled())));
        onView(withId(R.id.input_lastname)).perform(typeText("Jenne"));
        onView(withId(R.id.input_lastname)).perform(clearText());
        onView(withId(R.id.button_submit)).check(matches(not(isEnabled())));
        onView(withId(R.id.input_email)).perform(typeText("jenne.ben.e@gmail.com"));
        onView(withId(R.id.input_email)).perform(clearText());
        onView(withId(R.id.button_submit)).check(matches(not(isEnabled())));
        onView(withId(R.id.input_username)).perform(typeText("ben10"));
        onView(withId(R.id.input_username)).perform(clearText());
        onView(withId(R.id.button_submit)).check(matches(not(isEnabled())));
        onView(withId(R.id.button_dob)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2008, 4, 4));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.button_submit)).check(matches(not(isEnabled())));
    }
    @Test
    public void submitButtonValidInput(){
        onView(withId(R.id.input_firstname)).perform(typeText("Ben"));
        onView(withId(R.id.input_lastname)).perform(typeText("Ben"));
        onView(withId(R.id.input_email)).perform(typeText("jenne.ben.e@gmail.com"));
        onView(withId(R.id.input_username)).perform(typeText("ben10"));
        onView(withId(R.id.button_dob)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(1994, 4, 4));
        onView(withText("OK")).perform(click());
        onView(withId(R.id.button_submit)).perform(click());
        onView(withId(R.id.submit_message)).check(matches(withText("Thanks for Signing Up ben10!")));
    }
}