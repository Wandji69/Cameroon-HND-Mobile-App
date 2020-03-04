package com.example.hnd.auth;

import android.util.Patterns;

public class AuthViewModel {
    String emailVerification, passwordVeri, re_passwordVeri;

    public static String signInValidity(String email, String password){
        if(email.isEmpty()){
            return "Enter Email Adddress";
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return "Valid Email Required";
        }

        else if (password.isEmpty() || password.length()<6){
            return "6 Characters For Password Required";
        }
        else {
            return null;
        }
    }

    public static String signUpValidity(String email, String password, String re_password){
        if(email.isEmpty()){
            return "Enter Email Adddress";
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return "Valid Email Required";
        }
        else if (password.isEmpty() || password.length()<6){
            return "6 Characters For Password Required";
        }
        else if (!password.equals(re_password)){
            return "Passwords do not Match";
        }
        else {
            return null;
        }
    }

}
