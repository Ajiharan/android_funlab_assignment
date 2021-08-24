package com.example.funlap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.funlap.model.Story;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class StoryAddScreen extends AppCompatActivity {
    private EditText txtTitle,txtAuthor,txtDesc;
    private Button btnAddStory,btn_back;
    private FirebaseFirestore db;
    private AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_add_screen);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        db=FirebaseFirestore.getInstance();
        txtTitle=findViewById(R.id.story_title);
        txtAuthor=findViewById(R.id.story_author);
        txtDesc=findViewById(R.id.story_desc);
        btn_back=findViewById(R.id.btn_back);
        awesomeValidation.addValidation(this,R.id.story_title,"[\\w\\W\\s]+",R.string.story_title);
        awesomeValidation.addValidation(this,R.id.story_author,"[\\w\\W\\s]+",R.string.story_author);
        awesomeValidation.addValidation(this,R.id.story_desc,"[\\w\\W\\s]+",R.string.story_desc);

        btnAddStory=findViewById(R.id.btnadd_story);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StoryAddScreen.this,AdminHomeScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        btnAddStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });
    }
    private void addData(){
        if (awesomeValidation.validate()) {
        btnAddStory.setEnabled(false);

            Story story=new Story(txtTitle.getText().toString(),txtAuthor.getText().toString(),txtDesc.getText().toString());

            try{

                db.collection("stories")
                        .add(story.setStory())
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                            btnAddStory.setEnabled(true);
                                txtTitle.setText("");
                                txtAuthor.setText("");
                                txtDesc.setText("");
                                Toast.makeText(StoryAddScreen.this,"Successfully added",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            btnAddStory.setEnabled(true);
                                txtTitle.setText("");
                                txtAuthor.setText("");
                                txtDesc.setText("");
                                Toast.makeText(StoryAddScreen.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
            }catch (Exception e){
                btnAddStory.setEnabled(true);
                System.out.println("error"+e.getMessage());
            }



        }
    }
};

