package com.example.hnd.uitil;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import com.example.hnd.LoginAcivity;
import com.example.hnd.MainActivity;
import com.example.hnd.SetUpUserActivity;
import com.example.hnd.SignUpAcivity;

public class Helper {
    Context context;
    /*public Helper(Context context){
        this.context = context;
    }*/

    public Helper(){

    }

    public void Toastmessage(View view, String toastMessage){
        Toast.makeText(view.getContext(), toastMessage, Toast.LENGTH_SHORT).show();
    }
    public void Toastmessage(Context context, String toastMessage){
        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
    }

    public void goToMainActivity(View view){
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        view.getContext().startActivity(intent);
    }

    public void goToLoginActivity(View view){
        Intent intent = new Intent(view.getContext(), LoginAcivity.class);
        view.getContext().startActivity(intent);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }

    public void goToSetUpUserActivity(View view){
        Intent intent = new Intent(view.getContext(), SetUpUserActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        view.getContext().startActivity(intent);
    }

    public void goToSignUpActivity(View view){
        Intent intent = new Intent(view.getContext(), SignUpAcivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        view.getContext().startActivity(intent);
    }
}


