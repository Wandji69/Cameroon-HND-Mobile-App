package com.example.hnd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUpAcivity extends AppCompatActivity {

    private EditText email, password, confirmPassword;
    private TextView haveAnAccount;
    private Button signUpButton;

    private String emailStr, passwordStr, confirmPasswordStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_acivity);

        email = findViewById(R.id.enter_email_02);
        password = findViewById(R.id.enter_password_02);
        confirmPassword = findViewById(R.id.re_enter_password_02);
        haveAnAccount = findViewById(R.id.have_an_account);
        signUpButton = findViewById(R.id.sign_up_button);

        haveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccout();
                goToLogInActivity();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSetUpUserActivity();
            }
        });
    }

    private void createAccout() {
        emailStr = email.getText().toString();
        passwordStr = password.getText().toString();
        confirmPasswordStr = confirmPassword.getText().toString();
    }

    private void goToSetUpUserActivity() {
        Intent intent = new Intent(this, SetUpUserActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goToLogInActivity();
    }

    private void goToLogInActivity(){
        Intent intent = new Intent(this, LoginAcivity.class);
        startActivity(intent);
        finish();
    }
}
