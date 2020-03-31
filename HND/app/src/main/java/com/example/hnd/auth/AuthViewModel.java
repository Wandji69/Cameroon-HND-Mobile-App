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

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class AuthViewModel extends ViewModel {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser currentUser = mAuth.getCurrentUser();
    private String currentUserID;
    //reference to realtime database
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myUserReference = database.getReference().child("Users");//.child(currentUserID);
    //reference to firebase storage
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference myProfileImageReference = storage.getReference().child("Profile Image");
    private StorageReference imageFilePath;

    private Helper helper = new Helper();

    private EditText email, password, confirmPassword;
    //Edit text object to receive user account info
    EditText userName, matriculeNumber, levelofStudies, schoolName, departmentName, phoneNumber;
    private CircleImageView circleImageView;
    private String emailStr, passwordStr, confirmPasswordStr;
    private String TAG = "com.example.hnd.auth.AuthViewModel.TAG";
    private String profileImageDownloadUrl;

    public void authUserLogIn(@NonNull final View view){
        password = view.getRootView().findViewById(R.id.sign_in_password);
        email = view.getRootView().findViewById(R.id.sign_in_email);

        emailStr = email.getText().toString();
        passwordStr = password.getText().toString();

        if(email.getText().toString().isEmpty()){
            helper.ToastMessage(view.getContext(), "Enter Email Adddress");
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
            helper.ToastMessage(view.getContext(), "Valid Email Required");
        }
        else if (passwordStr.isEmpty() || passwordStr.length()<6){
            helper.ToastMessage(view.getContext(), "6 Characters For Password Required");
        }
        else {
            mAuth.signInWithEmailAndPassword(emailStr, passwordStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        helper.goToMainActivity(view.getContext());
                        helper.ToastMessage(view.getContext(), "You are logged in successfully");
                    }
                    else{
                        helper.ToastMessage(view.getContext(), "Failed to login \n" + task.getException().getMessage());
                    }
                }
            });
        }
    }

    public void authUserCreateAccount(@NonNull final View view){
        email = view.getRootView().findViewById(R.id.enter_email_02);
        password = view.getRootView().findViewById(R.id.enter_password_02);
        confirmPassword = view.getRootView().findViewById(R.id.re_enter_password_02);

        emailStr = email.getText().toString();
        passwordStr = password.getText().toString();
        confirmPasswordStr = confirmPassword.getText().toString();

        if(emailStr.isEmpty()){
            helper.ToastMessage(view.getContext(), "Enter Email Address");
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()){
            helper.ToastMessage(view.getContext(), "Valid Email Required");
        }
        else if (passwordStr.isEmpty() || passwordStr.length()<6){
            helper.ToastMessage(view.getContext(), "6 Characters For Password Required");
        }
        else if (!passwordStr.equals(confirmPasswordStr)){
            helper.ToastMessage(view.getContext(), "Passwords do not Match");
        }
        else {
            mAuth.createUserWithEmailAndPassword(emailStr, passwordStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @SuppressLint("LongLogTag")
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        helper.goToSetUpUserActivity(view.getContext());
                        helper.ToastMessage(view.getContext(), "You are logged in successfully");
                    }else{
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        helper.ToastMessage(view.getContext(), "Failed to login \n" + task.getException().getMessage());
                    }
                }
            });
        }
    }

    public void uploadProfileImage(Uri uri, final Context context){
        if(currentUser != null){
            currentUserID = currentUser.getUid();
            imageFilePath = myProfileImageReference.child(currentUserID + ".jpg");
            imageFilePath.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if(task.isComplete()){
                        imageFilePath.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                profileImageDownloadUrl = task.getResult().toString();
                                helper.ToastMessage(context, "downloadUrl is \n" + profileImageDownloadUrl);
                            }
                        });
                        helper.ToastMessage(context, "ImageUploaded Successfully Saved");
                    }else {
                        String errorMesasge = task.getException().toString();
                        helper.ToastMessage(context, "Error message \n"+ errorMesasge);
                    }
                }
            });
        }
    }

    public void saveAccountInfo(@NonNull final View view){
        userName = view.getRootView().findViewById(R.id.editText6);
        matriculeNumber = view.getRootView().findViewById(R.id.editText7);
        levelofStudies = view.getRootView().findViewById(R.id.editText9);
        schoolName = view.getRootView().findViewById(R.id.editText10);
        departmentName = view.getRootView().findViewById(R.id.editText12);
        phoneNumber = view.getRootView().findViewById(R.id.editText14);
        circleImageView = view.getRootView().findViewById(R.id.circleImageView);

        String userNameStr, matriculeNumberStr, levelOfStudiesStr, schoolNameStr, departmentNameStr, phoneNumberStr;
        userNameStr = userName.getText().toString();
        matriculeNumberStr = matriculeNumber.getText().toString();
        levelOfStudiesStr = levelofStudies.getText().toString();
        schoolNameStr =schoolName.getText().toString();
        departmentNameStr = departmentName.getText().toString();
        phoneNumberStr = phoneNumber.getText().toString();

        if(userNameStr.trim().isEmpty()){
            helper.ToastMessage(view.getContext(), "Enter Your Full Name");
        }else if(matriculeNumberStr.trim().isEmpty()){
            helper.ToastMessage(view.getContext(), "Enter Your Matricule Number");
        }else if(levelOfStudiesStr.trim().isEmpty()){
            helper.ToastMessage(view.getContext(), "Enter Your Level of Studies");
        }else if(schoolNameStr.trim().isEmpty()){
            helper.ToastMessage(view.getContext(), "Enter Your School Name");
        }else if(departmentNameStr.trim().isEmpty()){
            helper.ToastMessage(view.getContext(), "Enter Your School Department");
        }else if(phoneNumberStr.trim().isEmpty()){
            helper.ToastMessage(view.getContext(), "Enter a Phone Number");
        }else if(circleImageView == null){
            helper.ToastMessage(view.getContext(), "Chose a pict from gallery");
        }else if(!Patterns.PHONE.matcher(phoneNumberStr).matches()){
            helper.ToastMessage(view.getContext(), "enter a valid phone number");
        }else {
            HashMap userHashMap = new HashMap();
            userHashMap.put("userName", userNameStr);
            userHashMap.put("levelOfStudies", levelOfStudiesStr);
            userHashMap.put("schoolName", schoolNameStr);
            userHashMap.put("departmentName", departmentNameStr);
            userHashMap.put("email", getEmailAdress());
            userHashMap.put("profileImage", getProfileImageDownloadUrl());
            userHashMap.put("phoneNumber", phoneNumberStr);
            if(currentUser != null){
                currentUserID = currentUser.getUid();
                myUserReference.child(currentUserID).updateChildren(userHashMap).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            helper.ToastMessage(view.getContext(), "Account Created Successfully");
                            helper.goToMainActivity(view.getContext());
                        }else {
                            String errorMessage = task.getException().toString();
                            helper.ToastMessage(view.getContext(), "Error Occured\n"+ errorMessage);
                        }
                    }
                });
            }
        }

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
        if(profileImageDownloadUrl == null){
            return "";
        }else{
            return profileImageDownloadUrl;
        }
    }

}
