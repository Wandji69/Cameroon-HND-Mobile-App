package com.example.hnd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginAcivity extends AppCompatActivity {

    Button sign_in;
    TextView go_sign_up;
    EditText password, email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acivity);

        sign_in = findViewById(R.id.sign_in);
        go_sign_up = findViewById(R.id.dont_have_account);

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity();
            }
        });

        go_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSetUpActivity();
            }
        });
    }

    public void goToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void goToSetUpActivity(){
        Intent intent = new Intent(this, SetUpUserActivity.class);
        startActivity(intent);
    }
}
