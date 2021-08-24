package com.example.funlap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.funlap.model.VideoFile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import javax.annotation.Nullable;

public class VideoAddScreen extends AppCompatActivity {
    private MediaController mediaController;
    private EditText video_desc,video_title;
    private Button uploadv,upload_video,btn_back;
    private ProgressDialog progressDialog;
    private FirebaseFirestore db;
    private Uri videouri;
    private VideoView display_video;
    private AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_add_screen);
        db=FirebaseFirestore.getInstance();
        mediaController = new MediaController(this);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.video_title,"[\\w\\W\\s]+",R.string.video_title);
        awesomeValidation.addValidation(this,R.id.video_desc,"[\\w\\W\\s]+",R.string.video_desc);
        progressDialog = new ProgressDialog(VideoAddScreen.this);
        video_desc=findViewById(R.id.video_desc);
        video_title=findViewById(R.id.video_title);
        btn_back=findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(VideoAddScreen.this,AdminHomeScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
        upload_video=findViewById(R.id.upload_video);
        upload_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addVideo();
            }
        });
        // initialise layout
        display_video=findViewById(R.id.display_video);
        display_video.setMediaController(mediaController);
        uploadv = findViewById(R.id.uploadv);
        uploadv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                choosevideo();
            }
        });
    }

    // choose a video from phone storage
    private void choosevideo() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 5);
    }



    // startActivityForResult is used to receive the result, which is the selected video.
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            videouri = data.getData();

            display_video.setVideoURI(videouri);
            display_video.pause();

        }
    }

    private String getfiletype(Uri videouri) {
        ContentResolver r = getContentResolver();
        // get the file type ,in this case its mp4
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(r.getType(videouri));
    }

    private void uploadvideo() {
        if (videouri != null) {
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            // save the selected video in Firebase storage
            final StorageReference reference = FirebaseStorage.getInstance().getReference("Files/" + System.currentTimeMillis() + "." + getfiletype(videouri));
            reference.putFile(videouri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful()) ;
                    // get the link of video
                    String downloadUri = uriTask.getResult().toString();
                    VideoFile videofile=new VideoFile(video_title.getText().toString(),video_desc.getText().toString(),downloadUri);
                    db.collection("videos")
                            .add(videofile.setVideo())
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    resetData();
                                    progressDialog.dismiss();
                                    Toast.makeText(VideoAddScreen.this, "Video Uploaded sucessfully!!", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    resetData();
                                    progressDialog.dismiss();
                                    Toast.makeText(VideoAddScreen.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Error, Image not uploaded
                    progressDialog.dismiss();
                    resetData();
                    Toast.makeText(VideoAddScreen.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                // Progress Listener for loading
                // percentage on the dialog box
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    // show the progress bar
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                }
            });
        }else{
            Toast.makeText(VideoAddScreen.this, "please upload video", Toast.LENGTH_SHORT).show();
        }
    }

    private void  addVideo(){
        if (awesomeValidation.validate()) {

            uploadvideo();
        }

    }

    private void resetData(){
        video_desc.setText("");
        video_title.setText("");
        videouri=null;
    }
}