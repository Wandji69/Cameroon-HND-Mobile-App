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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.widget.Toast.LENGTH_LONG;

public class LoginAcivity extends AppCompatActivity {
    FirebaseAuth mAuth;

    Button signInButton;
    TextView doNotHaveAccount;
    EditText password, email;
    String emailStr, passwordStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acivity);

        mAuth = FirebaseAuth.getInstance();

        signInButton = findViewById(R.id.sign_in_button);
        doNotHaveAccount = findViewById(R.id.dont_have_account);
        password = findViewById(R.id.sign_in_password);
        email = findViewById(R.id.sign_in_email);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountSignIn();
            }
        });

        doNotHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSetUpActivity();
            }
        });
    }

    private void accountSignIn() {
        emailStr = email.getText().toString();
        passwordStr = password.getText().toString();

        if(AuthViewModel.signInValidity(emailStr, passwordStr) != null){
            Toast.makeText(this, AuthViewModel.signInValidity(emailStr, passwordStr), LENGTH_LONG).show();
        }else{
            goToMainActivity();
            /*
            AuthListener.authUserLogIn(emailStr, passwordStr);
            if(AuthListener.taskStr.equals("You are logged in successfully")){
                goToMainActivity();
            }else{
                Toast.makeText(this, AuthListener.taskStr, LENGTH_LONG).show();
            }*/
        }
    }

    /*@Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            goToMainActivity();
        }
    }*/

    public void goToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToSetUpActivity(){
        Intent intent = new Intent(this, SignUpAcivity.class);
        startActivity(intent);
        finish();
    }
}
