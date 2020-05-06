package com.example.helloworld2;

import android.util.Patterns;
import android.widget.TextView;

import java.util.Calendar;

public class inputCheck {
    private TextView valid_name;
    private TextView valid_email;
    private TextView valid_username;
    private TextView valid_dob;
    public static int getAge(int birth_year, int birth_month, int birth_day){
        Calendar c = Calendar.getInstance();
        int current_year = c.get(Calendar.YEAR);
        int current_month = c.get(Calendar.MONTH);
        int current_day = c.get(Calendar.DAY_OF_MONTH);
        int yearsOld = current_year - birth_year;
        if(birth_month > current_month){
            return yearsOld -1;
        }
        if(birth_month == current_month && birth_day > current_day){
            return yearsOld -1;
        }
        else{
            return yearsOld;
        }
    }
    public static boolean dobCheck(int birth_year, int birth_month, int birth_day){
        Calendar c = Calendar.getInstance();
        int current_year = c.get(Calendar.YEAR);
        int current_month = c.get(Calendar.MONTH);
        int current_day = c.get(Calendar.DAY_OF_MONTH);
        int min_year = current_year -18;
        int min_month = current_month;
        //The person is years younger than min year
        if(birth_year > min_year){
            return false;
        }
        //The person is same years as min year but they were born in an later month
        if((birth_year == min_year) && (birth_month > min_month)){
            return false;
        }
        //The person is same years and months as min but they were born on a later day
        if((birth_year == min_year) && (birth_month == min_month) && (birth_day > current_day)){
            return false;
        }
        else {
            return true;
        }
    }
    public static String nameCheck(String name){
        //Referenced https://stackoverflow.com/questions/5238491/check-if-string-contains-only-letters
        if(name.equals("")){
            return "empty";
        }
        char[] c = name.toCharArray();
        for(int i = 0; i<c.length; i++){
            if(Character.isLetter(c[i]) == false){
                return "non_letter";
            }
        }
        return "valid";
    }
    public static boolean emailCheck(String email){
        //Referenced https://stackoverflow.com/questions/1819142/how-should-i-validate-an-e-mail-address
        if(email.equals("")){
            return false;
        }
        else{
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }
    public static String userNameCheck(String username){
        if(username.equals("")){
            return"empty";
        }
        else{
            return"valid";
        }
    }
    public static String occupationCheck(String occupation){
        if(occupation.equals("")){
            return "empty";
        }
        char[] c = occupation.toCharArray();
        for(int i = 0; i<c.length; i++){
            if(Character.isLetter(c[i]) == false && Character.isWhitespace(c[i]) == false){
                return "non_letter";
            }
        }
        return "valid";
    }
    public static String descriptionCheck(String description){
        if(description.equals("")){
            return "empty";
        }
        char[] c = description.toCharArray();
        for(int i = 0; i<c.length; i++){
            if(Character.isLetter(c[i]) == false && Character.isWhitespace(c[i]) == false && Character.isDigit(c[i]) == false){
                return "non_letter";
            }
        }
        return "valid";
    }
}
