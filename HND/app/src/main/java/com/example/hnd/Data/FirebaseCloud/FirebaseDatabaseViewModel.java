package com.example.hnd.Data.FirebaseCloud;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.hnd.R;
import com.example.hnd.uitil.Helper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class FirebaseDatabaseViewModel {
    Helper helper = new Helper();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String currentUserID = mAuth.getCurrentUser().getUid();
    //reference to realtime database
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myReference = database.getReference().child("Users").child(currentUserID);
    //reference to profile image
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference myProfileImageReference = storage.getReference().child("Profile Image");
    private StorageReference imageFilePath = myProfileImageReference.child(currentUserID + ".jpg");



}
