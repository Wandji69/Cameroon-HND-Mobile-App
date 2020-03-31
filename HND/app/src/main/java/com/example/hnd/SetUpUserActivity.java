package com.example.hnd;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hnd.auth.AuthViewModel;
import com.example.hnd.uitil.Helper;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.pm.PackageManager.PERMISSION_DENIED;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class SetUpUserActivity extends AppCompatActivity {
    private Helper helper;
    private AuthViewModel authViewModel;

    private static final int PERMISSION_CODE_01 = 1001;
    private static final int PERMISSION_CODE_02 = 1002;
    private static final int galleryPick = 1;
    private CircleImageView circleImageView;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_user);
        helper = new Helper();
        authViewModel = new AuthViewModel();

        circleImageView = findViewById(R.id.circleImageView);
        saveButton = findViewById(R.id.save_info_button);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                checkStoragePermission();
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, galleryPick);
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authViewModel.saveAccountInfo(v);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == galleryPick && resultCode == RESULT_OK && data != null){
            Uri imageUri = data.getData();
            CropImage.activity().setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            helper.ToastMessage(this, "Image Cropped Successfully");

            if(resultCode == RESULT_OK){
                helper.ToastMessage(this, "Cropped image gotten successfully");
                authViewModel.uploadProfileImage(result.getUri(), this);
                if(authViewModel.getProfileImageDownloadUrl() != null){
                    circleImageView.setImageURI(result.getUri());
                }
            }
        }else{
            helper.ToastMessage(this, "Error Message \n Image can't be cropped");
        }

    }

    public void checkStoragePermission(){
        //if statement to check wheather storage permission has being granted
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PERMISSION_DENIED){
                //permission not granted, request it
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                //show popup for runtime permission request
                requestPermissions(permissions, PERMISSION_CODE_01);
            }
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PERMISSION_DENIED){
                //permission not granted, request it
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                //show popup for runtime permission request
                requestPermissions(permissions, PERMISSION_CODE_02);
            }
            /*
             *Remember to remove the final from the resultUri in other to use a reference variable and assign it it the resultUri
             * so that we can directly put the image in the userProfileImage after asking storage permission
             **/
        }else{
            //System O.S is less than Marshmallow
            Toast.makeText(this, "System O.S is less than Marshmallow", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE_01:
                if(grantResults.length >0 && grantResults[0] == PERMISSION_GRANTED){
                    helper.ToastMessage(this, "You granted READ storage permission to this app");
                }else{
                    helper.ToastMessage(this, "Storage Permission denied...!");
                }

            case PERMISSION_CODE_02:
                if(grantResults.length >0 && grantResults[0] == PERMISSION_GRANTED){
                    helper.ToastMessage(this, "You granted WRITE storage permission to this app");
                }else{
                    helper.ToastMessage(this, "Storage Permission denied...!");
                }
        }
    }

}
