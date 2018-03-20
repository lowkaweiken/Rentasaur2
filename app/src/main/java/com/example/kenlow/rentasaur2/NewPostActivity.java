package com.example.kenlow.rentasaur2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.ref.Reference;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import id.zelory.compressor.Compressor;

public class NewPostActivity extends AppCompatActivity {

    private Toolbar newPostToolbar;

    private ImageView newPostImage;
    private EditText newPostDesc;
    private EditText address_line_1;
    private EditText address_line_2;
    private EditText extra_info;
    private EditText monthly_rental;
    private Button newPostBtn;

    private Uri postImageUri = null;

    private ProgressBar newPostProgress;

    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    private String current_user_id;
    String property_id;

    private Bitmap compressedImageFile;

    private static final String TAG = "NewPostActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        current_user_id = firebaseAuth.getCurrentUser().getUid();


        newPostToolbar = findViewById(R.id.new_post_toolbar);
        setSupportActionBar(newPostToolbar);
        getSupportActionBar().setTitle("Add New Post");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        newPostImage = findViewById(R.id.new_post_image);
        newPostDesc = findViewById(R.id.new_post_desc);
        address_line_1 = findViewById(R.id.new_post_address_1);
        address_line_2 = findViewById(R.id.new_post_address_2);
        extra_info = findViewById(R.id.new_post_info);
        monthly_rental = findViewById(R.id.new_post_rental);
        newPostBtn = findViewById(R.id.post_btn);
        newPostProgress = findViewById(R.id.new_post_progress);


        if (getIntent().hasExtra("property_profile_id")) {
            property_id = getIntent().getStringExtra("property_profile_id");
            Toast.makeText(NewPostActivity.this, property_id, Toast.LENGTH_LONG).show();

            //------------------------Testing--------------------------//
            firebaseFirestore.collection("Posts").document(property_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if (task.isSuccessful()) {
                        if (task.getResult().exists()) {

                            // Assign the name and image URI. It must be the same as stated in Firestore
                            String Desc = task.getResult().getString("desc");
                            String Address_line_1 = task.getResult().getString("address_line_1");
                            String Address_line_2 = task.getResult().getString("address_line_2");
                            String Monthly_rental = task.getResult().getString("monthly_rental");
                            String Extra_info = task.getResult().getString("extra_info");

                            newPostDesc.setText(Desc);
                            address_line_1.setText(Address_line_1);
                            address_line_2.setText(Address_line_2);
                            monthly_rental.setText(Monthly_rental);
                            extra_info.setText(Extra_info);

                        } else {
                            Toast.makeText(NewPostActivity.this, "Data Doesn't Exists ", Toast.LENGTH_LONG).show();
                        }
                    }

                }

            });


        }


        newPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setMinCropResultSize(512, 512)
                        .setAspectRatio(1, 1)
                        .start(NewPostActivity.this);
            }
        });

        newPostBtn.setEnabled(true);

        newPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String desc = newPostDesc.getText().toString();

                // check if description is not empty
                if (!TextUtils.isEmpty(desc) && postImageUri != null) {
                    newPostBtn.setEnabled(false);
                    newPostProgress.setVisibility(View.VISIBLE);

                    // Because there can be multiple images for a user. We create a random ID for each image
                    final String randomName = UUID.randomUUID().toString();
                    final String Address_line_1 = address_line_1.getText().toString();
                    final String Address_line_2 = address_line_2.getText().toString();
                    final String Monthly_rental = monthly_rental.getText().toString();
                    final String Extra_info = extra_info.getText().toString();

                    // Create a new folder/path called post_images and store images in there
                    StorageReference filePath = storageReference.child("post_images").child(randomName + ".jpg");
                    filePath.putFile(postImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull final Task<UploadTask.TaskSnapshot> task) {

                            final String downloadUri = task.getResult().getDownloadUrl().toString();

                            if (task.isSuccessful()) {

                                // Create thumbnail by compressing images
                                File newImageFile = new File(postImageUri.getPath());

                                try {
                                    compressedImageFile = new Compressor(NewPostActivity.this)
                                            .setMaxHeight(100)
                                            .setMaxWidth(100)
                                            .setQuality(2)
                                            .compressToBitmap(newImageFile);


                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                compressedImageFile.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                byte[] thumbData = baos.toByteArray();

                                UploadTask uploadTask = storageReference.child("post_images/thumbs")
                                        .child(randomName + ".jpg").putBytes(thumbData);

                                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                    // if the task is successful
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        String downloadthumbUri = taskSnapshot.getDownloadUrl().toString();


                                        //Create an object
                                        final Map<String, Object> postMap = new HashMap<>();
                                        postMap.put("image_url", downloadUri);
                                        postMap.put("image_thumb", downloadthumbUri);
                                        postMap.put("desc", desc);
                                        postMap.put("address_line_1", Address_line_1);
                                        postMap.put("address_line_2", Address_line_2);
                                        postMap.put("monthly_rental", Monthly_rental);
                                        postMap.put("extra_info", Extra_info);
                                        postMap.put("user_id", current_user_id);
                                        postMap.put("timestamp", FieldValue.serverTimestamp());

                                        firebaseFirestore.collection("Posts")
                                                .add(postMap)
                                                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentReference> task) {

                                                        if (task.isSuccessful()) {

                                                            Toast.makeText(NewPostActivity.this, "Post was added", Toast.LENGTH_LONG).show();
                                                            Intent mainIntent = new Intent(NewPostActivity.this, MainActivity.class);
                                                            startActivity(mainIntent);
                                                            finish();


                                                        } else {

                                                        }

                                                        newPostProgress.setVisibility(View.INVISIBLE);
                                                    }
                                                }).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                String propertyID = documentReference.getId();
                                                Log.d(TAG, "DocumentSnapshot added with ID: " + propertyID);

                                                Map<String, Object> postMap = new HashMap<>();
                                                postMap.put("property_id", propertyID);

                                                firebaseFirestore.collection("Posts").document(propertyID)
                                                        .set(postMap, SetOptions.merge());

                                            }
                                        });


                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Error Handling here

                                    }
                                });

                            } else {
                                newPostProgress.setVisibility(View.INVISIBLE);
                            }
                        }
                    });


                }

            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                postImageUri = result.getUri();
                newPostImage.setImageURI(postImageUri);


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

}
