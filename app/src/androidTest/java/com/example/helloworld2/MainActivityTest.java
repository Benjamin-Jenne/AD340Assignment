package com.example.helloworld2;

import android.content.pm.ActivityInfo;
import android.util.Log;
import android.widget.DatePicker;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

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
    public ActivityTestRule<MainActivity> activityTestRule
            = new ActivityTestRule<>(MainActivity.class);
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
                .perform(typeText(Constants.TEST_FIRST_NAME)).check(matches(withText(Constants.TEST_FIRST_NAME)));
        onView(withId(R.id.input_lastname))
                .perform(typeText(Constants.TEST_LAST_NAME)).check(matches(withText(Constants.TEST_LAST_NAME)));
        onView(withId(R.id.input_email))
                .perform(typeText(Constants.TEST_EMAIL)).check(matches(withText(Constants.TEST_EMAIL)));
        onView(withId(R.id.input_username))
                .perform(typeText(Constants.TEST_USERNAME)).check(matches(withText(Constants.TEST_USERNAME)));
    }
    @Test
    public void emptyTextWarning(){
        onView(withId(R.id.input_firstname)).perform(typeText(Constants.TEST_FIRST_NAME));
        onView(withId(R.id.input_firstname)).perform(clearText());
        onView(withId(R.id.valid_firstname)).check(matches(withText(R.string.enter_firstname)));
        onView(withId(R.id.input_lastname)).perform(typeText(Constants.TEST_FIRST_NAME));
        onView(withId(R.id.input_lastname)).perform(clearText());
        onView(withId(R.id.valid_lastname)).check(matches(withText(R.string.enter_lastname)));
        onView(withId(R.id.input_email)).perform(typeText(Constants.TEST_EMAIL));
        onView(withId(R.id.input_email)).perform(clearText());
        onView(withId(R.id.valid_email)).check(matches(withText(R.string.enter_email)));
        onView(withId(R.id.input_username)).perform(typeText(Constants.TEST_USERNAME));
        onView(withId(R.id.input_username)).perform(clearText());
        onView(withId(R.id.valid_username)).check(matches(withText(R.string.enter_username)));
    }
    @Test
    public void badNameInput(){
        onView(withId(R.id.input_firstname)).perform(typeText(Constants.TEST_BAD_NAME));
        onView(withId(R.id.valid_firstname)).check(matches(withText(R.string.letters)));
        onView(withId(R.id.input_lastname)).perform(typeText(Constants.TEST_BAD_NAME));
        onView(withId(R.id.valid_lastname)).check(matches(withText(R.string.letters)));
    }
    @Test
    public void badEmailInput(){
        onView(withId(R.id.input_email)).perform(typeText(Constants.TEST_FIRST_NAME));
        onView(withId(R.id.input_email)).perform(clearText());
        onView(withId(R.id.valid_email)).check(matches(withText(R.string.enter_email)));
        onView(withId(R.id.input_email)).perform(typeText(Constants.TEST_BAD_EMAIL_1));
        onView(withId(R.id.input_email)).perform(clearText());
        onView(withId(R.id.valid_email)).check(matches(withText(R.string.enter_email)));
        onView(withId(R.id.input_email)).perform(typeText(Constants.TEST_BAD_EMAIL_2));
        onView(withId(R.id.input_email)).perform(clearText());
        onView(withId(R.id.valid_email)).check(matches(withText(R.string.enter_email)));
        Espresso.closeSoftKeyboard();
    }
    @Test
    public void badOccupationInput(){
        onView(withId(R.id.input_occupation)).perform(typeText(Constants.TEST_BAD_OCCUPATION));
        onView(withId(R.id.valid_occupation)).check(matches(withText(R.string.letters)));
    }
    @Test
    public void badDescriptionInput(){
        onView(withId(R.id.input_description)).perform(typeText(Constants.TEST_BAD_DESCRIPTION));
        onView(withId(R.id.valid_description)).check(matches(withText(R.string.letters_or_numbers)));
    }
    @Test
    public void testDatePickerTooYoung(){
        //Referenced https://stackoverflow.com/questions/43149728/select-date-from-calendar-in-android-espresso
        //Born years too early
        Calendar c = Calendar.getInstance();
        int currentDay = c.get(Calendar.DAY_OF_MONTH);
        int currentMonth = c.get(Calendar.MONTH) + 1;
        int currentYear = c.get(Calendar.YEAR);
        onView(withId(R.id.button_dob)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(currentYear - 10, 1, 1));
        onView(withText(Constants.OK)).perform(click());
        onView(withId(R.id.valid_dob)).check(matches(withText(R.string.invalid_dob)));
        //Born months too early
        onView(withId(R.id.button_dob)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(currentYear-18, currentMonth + 3, 1));
        onView(withText(Constants.OK)).perform(click());
        onView(withId(R.id.valid_dob)).check(matches(withText(R.string.invalid_dob)));
        //Born days too early
        onView(withId(R.id.button_dob)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(currentYear-18, currentMonth, currentDay + 3));
        onView(withText(Constants.OK)).perform(click());
        onView(withId(R.id.valid_dob)).check(matches(withText(R.string.invalid_dob)));
    }
    @Test
    public void testDatePickerOldEnough(){
        //Referenced https://stackoverflow.com/questions/43149728/select-date-from-calendar-in-android-espresso
        onView(withId(R.id.button_dob)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(1994, 4, 4));
        onView(withText(Constants.OK)).perform(click());
        onView(withId(R.id.valid_dob)).check(matches(withText(R.string.valid_dob)));
        onView(withId(R.id.button_dob)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2002, 5, 5));
        onView(withText(Constants.OK)).perform(click());
        onView(withId(R.id.valid_dob)).check(matches(withText(R.string.valid_dob)));
    }
    @Test
    public void submitButtonDisabledBadInput(){
        onView(withId(R.id.button_submit)).check(matches(not(isEnabled())));
        onView(withId(R.id.input_firstname)).perform(typeText(Constants.TEST_FIRST_NAME));
        onView(withId(R.id.input_firstname)).perform(clearText());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.button_submit)).check(matches(not(isEnabled())));
        onView(withId(R.id.input_lastname)).perform(typeText(Constants.TEST_LAST_NAME));
        onView(withId(R.id.input_lastname)).perform(clearText());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.button_submit)).check(matches(not(isEnabled())));
        onView(withId(R.id.input_email)).perform(typeText(Constants.TEST_EMAIL));
        onView(withId(R.id.input_email)).perform(clearText());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.button_submit)).check(matches(not(isEnabled())));
        onView(withId(R.id.input_username)).perform(typeText(Constants.TEST_USERNAME));
        onView(withId(R.id.input_username)).perform(clearText());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.button_submit)).check(matches(not(isEnabled())));
        onView(withId(R.id.input_occupation)).perform(typeText(Constants.TEST_OCCUPATION));
        onView(withId(R.id.input_occupation)).perform(clearText());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.button_submit)).check(matches(not(isEnabled())));
        onView(withId(R.id.input_description)).perform(typeText(Constants.TEST_OCCUPATION));
        onView(withId(R.id.input_description)).perform(clearText());
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.button_submit)).check(matches(not(isEnabled())));
        onView(withId(R.id.button_dob)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2008, 4, 4));
        onView(withText(Constants.OK)).perform(click());
        onView(withId(R.id.button_submit)).check(matches(not(isEnabled())));
    }
    @Test
    public void submitButtonValidInput(){
        onView(withId(R.id.input_firstname)).perform(typeText(Constants.TEST_FIRST_NAME));
        onView(withId(R.id.input_lastname)).perform(typeText(Constants.TEST_LAST_NAME));
        onView(withId(R.id.input_email)).perform(typeText(Constants.TEST_EMAIL));
        onView(withId(R.id.input_username)).perform(typeText(Constants.TEST_USERNAME));
        onView(withId(R.id.input_occupation)).perform(typeText(Constants.TEST_OCCUPATION));
        onView(withId(R.id.input_description)).perform(typeText(Constants.TEST_DESCRIPTION));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.button_dob)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(1994, 4, 4));
        onView(withText(Constants.OK)).perform(click());
        onView(withId(R.id.button_submit)).perform(click());
        onView(withId(R.id.nameAge)).check(matches(withText(Constants.TEST_FIRST_NAME + ", " + Constants.TEST_AGE)));
    }
    @Test
    public void RotateState(){
        //Referenced https://stackoverflow.com/questions/37362200/how-to-rotate-activity-i-mean-screen-orientation-change-using-espresso

        //Enter some good input in portrait mode
        onView(withId(R.id.input_firstname)).perform(typeText(Constants.TEST_FIRST_NAME));
        onView(withId(R.id.input_lastname)).perform(typeText(Constants.TEST_LAST_NAME));
        onView(withId(R.id.input_email)).perform(typeText(Constants.TEST_EMAIL));
        onView(withId(R.id.input_username)).perform(typeText(Constants.TEST_USERNAME));
        onView(withId(R.id.input_occupation)).perform(typeText(Constants.TEST_OCCUPATION));
        onView(withId(R.id.input_description)).perform(typeText(Constants.TEST_DESCRIPTION));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.button_dob)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(1994, 4, 4));
        onView(withText(Constants.OK)).perform(click());

        //Rotate the screen into landscape and check the state.
        activityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        onView(withId(R.id.input_firstname)).check(matches(withText(Constants.TEST_FIRST_NAME)));
        onView(withId(R.id.valid_firstname)).check(matches(withText("")));
        onView(withId(R.id.input_lastname)).check(matches(withText(Constants.TEST_LAST_NAME)));
        onView(withId(R.id.valid_lastname)).check(matches(withText("")));
        onView(withId(R.id.input_email)).check(matches(withText(Constants.TEST_EMAIL)));
        onView(withId(R.id.valid_email)).check(matches(withText("")));
        onView(withId(R.id.input_username)).check(matches(withText(Constants.TEST_USERNAME)));
        onView(withId(R.id.valid_username)).check(matches(withText("")));
        onView(withId(R.id.input_occupation)).check(matches(withText(Constants.TEST_OCCUPATION)));
        onView(withId(R.id.valid_occupation)).check(matches(withText("")));
        onView(withId(R.id.input_description)).perform(ViewActions.scrollTo()).check(matches(withText(Constants.TEST_DESCRIPTION)));
        onView(withId(R.id.valid_description)).perform(ViewActions.scrollTo()).check(matches(withText("")));
        onView(withId(R.id.valid_dob)).perform(ViewActions.scrollTo()).check(matches(withText(R.string.valid_dob)));

        //Clear Text
        onView(withId(R.id.input_firstname)).perform(clearText());
        onView(withId(R.id.input_lastname)).perform(clearText());
        onView(withId(R.id.input_email)).perform(clearText());
        onView(withId(R.id.input_username)).perform(clearText());
        onView(withId(R.id.input_occupation)).perform(clearText());
        onView(withId(R.id.input_description)).perform(ViewActions.scrollTo()).perform(clearText());

        //Enter some bad info in landscape
        onView(withId(R.id.input_firstname)).perform(typeText(Constants.TEST_BAD_NAME));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.input_lastname)).perform(typeText(Constants.TEST_BAD_NAME));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.input_email)).perform(typeText(Constants.TEST_BAD_EMAIL_1));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.input_username)).perform(typeText(Constants.TEST_USERNAME));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.input_occupation)).perform(typeText(Constants.TEST_BAD_OCCUPATION));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.input_description)).perform(ViewActions.scrollTo()).perform(typeText(Constants.TEST_BAD_DESCRIPTION));
        Espresso.closeSoftKeyboard();

        //Rotate the screen into landscape and check the state.
        activityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        onView(withId(R.id.valid_firstname)).check(matches(withText(R.string.letters)));
        onView(withId(R.id.valid_lastname)).check(matches(withText(R.string.letters)));
        onView(withId(R.id.valid_email)).check(matches(withText(R.string.enter_email)));
        onView(withId(R.id.valid_username)).check(matches(withText("")));
        onView(withId(R.id.valid_occupation)).check(matches(withText(R.string.letters)));
        onView(withId(R.id.valid_description)).check(matches(withText(R.string.letters_or_numbers)));
        onView(withId(R.id.valid_dob)).check(matches(withText(R.string.valid_dob)));
    }
}