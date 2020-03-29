package com.example.hnd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hnd.auth.AuthViewModel;
import com.example.hnd.uitil.Helper;
import com.google.firebase.auth.FirebaseAuth;

import static android.widget.Toast.LENGTH_LONG;

public class LoginAcivity extends AppCompatActivity {
    private Helper helper;
    private AuthViewModel authViewModel;
    FirebaseAuth mAuth;

    Button signInButton;
    TextView doNotHaveAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acivity);

        mAuth = FirebaseAuth.getInstance();
        helper = new Helper();
        authViewModel = new AuthViewModel();

        signInButton = findViewById(R.id.sign_in_button);
        doNotHaveAccount = findViewById(R.id.dont_have_account);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authViewModel.authUserLogIn(v);
            }
        });

        doNotHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.goToSignUpActivity(v);
                finish();
            }
        });
    }

    public void goToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
