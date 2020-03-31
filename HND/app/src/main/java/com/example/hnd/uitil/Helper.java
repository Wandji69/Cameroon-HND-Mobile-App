package com.example.hnd.uitil;

import android.app.ProgressDialog;
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

    public Helper(){
    }

    public void ToastMessage(Context context, String toastMessage){
        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
    }

    public void goToMainActivity(Context context){
        Intent intent = new Intent(context,  MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void goToLoginActivity(Context context){
        Intent intent = new Intent(context, LoginAcivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void goToSetUpUserActivity(Context context){
        Intent intent = new Intent(context, SetUpUserActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void goToSignUpActivity(Context context){
        Intent intent = new Intent(context, SignUpAcivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public void progressDialog(Context context){
        ProgressDialog progressDialog = new ProgressDialog(context);
    }
}


