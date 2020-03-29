package com.example.hnd.auth;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.hnd.MainActivity;
import com.example.hnd.R;
import com.example.hnd.uitil.Helper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AuthViewModel extends ViewModel {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String currentUserID = mAuth.getCurrentUser().getUid();
    //reference to realtime database
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myReference = database.getReference().child("Users").child(currentUserID);
    //reference to profile image
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference myProfileImageReference = storage.getReference().child("Profile Image");
    private StorageReference imageFilePath = myProfileImageReference.child(currentUserID + ".jpg");

    private Helper helper = new Helper();

    private EditText email, password, confirmPassword;
    //Edit text object to receive user account info
    EditText fullName, matriculeNumber, levelofStudies, schoolName, departmentName, phoneNumber;
    private String emailStr, passwordStr, confirmPasswordStr;
    private String TAG = "com.example.hnd.auth.AuthViewModel.TAG";
    private String profileImageDownloadUrl;

    public void authUserLogIn(final View view){
        password = view.getRootView().findViewById(R.id.sign_in_password);
        email = view.getRootView().findViewById(R.id.sign_in_email);

        emailStr = email.getText().toString();
        passwordStr = password.getText().toString();

        if(email.getText().toString().isEmpty()){
            helper.Toastmessage(view, "Enter Email Adddress");
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
            helper.Toastmessage(view, "Valid Email Required");
        }
        else if (passwordStr.isEmpty() || passwordStr.length()<6){
            helper.Toastmessage(view.getContext(), "6 Characters For Password Required");
        }
        else {
            mAuth.signInWithEmailAndPassword(emailStr, passwordStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        helper.goToMainActivity(view);
                        helper.Toastmessage(view, "You are logged in successfully");
                    }
                    else{
                        helper.Toastmessage(view, "Failed to login \n" + task.getException().getMessage());
                    }
                }
            });
        }
    }

    public void authUserCreateAccount(final View view){
        email = view.getRootView().findViewById(R.id.enter_email_02);
        password = view.getRootView().findViewById(R.id.enter_password_02);
        confirmPassword = view.getRootView().findViewById(R.id.re_enter_password_02);

        emailStr = email.getText().toString();
        passwordStr = password.getText().toString();
        confirmPasswordStr = confirmPassword.getText().toString();

        if(emailStr.isEmpty()){
            helper.Toastmessage(view, "Enter Email Address");
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()){
            helper.Toastmessage(view, "Valid Email Required");
        }
        else if (passwordStr.isEmpty() || passwordStr.length()<6){
            helper.Toastmessage(view, "6 Characters For Password Required");
        }
        else if (!passwordStr.equals(confirmPasswordStr)){
            helper.Toastmessage(view, "Passwords do not Match");
        }
        else {
            mAuth = FirebaseAuth.getInstance();
            mAuth.createUserWithEmailAndPassword(emailStr, passwordStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        helper.goToSetUpUserActivity(view);
                        helper.Toastmessage(view, "You are logged in successfully");
                    }else{
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        helper.Toastmessage(view, "Failed to login \n" + task.getException().getMessage());
                    }
                }
            });
        }
    }

    public void uploadProfileImage(Uri uri, final Context context){
        imageFilePath.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isComplete()){
                    imageFilePath.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            profileImageDownloadUrl = task.getResult().toString();
                            helper.Toastmessage(context, "downloadUrl is \n" + profileImageDownloadUrl);
                        }
                    });
                    helper.Toastmessage(context, "ImageUploaded Successfully Saved");
                }else {
                    String errorMesasge = task.getException().toString();
                    helper.Toastmessage(context, "Error message \n"+ errorMesasge);
                }
            }
        });
    }


    public void saveAccountInfo(View view){
        fullName = view.getRootView().findViewById(R.id.editText6);
        matriculeNumber = view.getRootView().findViewById(R.id.editText7);
        levelofStudies = view.getRootView().findViewById(R.id.editText9);
        schoolName = view.getRootView().findViewById(R.id.editText10);
        departmentName = view.getRootView().findViewById(R.id.editText12);
        phoneNumber = view.getRootView().findViewById(R.id.editText14);




    }

    public String getEmailAdress(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            return user.getEmail();
        }else {
            return "no email";
        }
    }
    public String getProfileImageDownloadUrl(){
        return profileImageDownloadUrl;
    }

}
