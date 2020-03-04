package com.example.hnd.auth;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AuthListener {
    private static FirebaseAuth mAuth;
    public static String taskStr = "";


    public static void authUserLogIn(String email, String password) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    taskStr = "You are logged in successfully";
                }
                else{
                    taskStr = task.getException().getMessage();                }
            }
        });
    }

    public static void authUserCreateAccount(String email, String password, String confirePassword){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //
                }else{
                    taskStr = task.getException().getMessage();
                }
            }
        });
    }
}
