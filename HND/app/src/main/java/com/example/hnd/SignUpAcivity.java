package com.example.hnd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hnd.auth.AuthListener;
import com.example.hnd.auth.AuthViewModel;
import com.example.hnd.uitil.Helper;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpAcivity extends AppCompatActivity {
    private FirebaseAuth mAuth1;
    private AuthViewModel authViewModel;
    private AuthListener authListener;
    private Helper helper;


    private EditText email, password, confirmPassword;
    private TextView haveAnAccount;
    private Button signUpButton;

    private String emailStr, passwordStr, confirmPasswordStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_acivity);
        //get instance for authentication
        mAuth1 = FirebaseAuth.getInstance();

        authListener = new AuthListener();
        authViewModel = new AuthViewModel();
        helper = new Helper();

        haveAnAccount = findViewById(R.id.have_an_account);
        signUpButton = findViewById(R.id.sign_up_button);

        haveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.goToLoginActivity(getApplicationContext());
                finish();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authViewModel.authUserCreateAccount(v);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        helper.goToLoginActivity(getApplicationContext());
    }

}
