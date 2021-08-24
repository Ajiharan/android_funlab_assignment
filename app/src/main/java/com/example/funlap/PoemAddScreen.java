package com.example.funlap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.funlap.model.Poem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class PoemAddScreen extends AppCompatActivity {
    private EditText poem_title,poem_desc,poem;
    private Button btnadd_poem,btn_back;
    private FirebaseFirestore db;
    private AwesomeValidation awesomeValidation;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poem_add_screen);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        db=FirebaseFirestore.getInstance();
        poem_title=findViewById(R.id.poem_title);
        poem_desc=findViewById(R.id.poem_desc);
        poem=findViewById(R.id.poem);
        btnadd_poem=findViewById(R.id.btnadd_poem);
        btn_back=findViewById(R.id.btn_back);
        progressDialog = new ProgressDialog(PoemAddScreen.this);
        awesomeValidation.addValidation(this,R.id.poem_title,"[\\w\\W\\s]+",R.string.poem_title);
        awesomeValidation.addValidation(this,R.id.poem_desc,"[\\w\\W\\s]+",R.string.poem_desc);
        awesomeValidation.addValidation(this,R.id.poem,"[\\w\\W\\s]+",R.string.poem);

        btnadd_poem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PoemAddScreen.this,AdminHomeScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

    }

    private void addData(){
        if (awesomeValidation.validate()) {

            btnadd_poem.setEnabled(false);
            progressDialog.setTitle("loading...");
            progressDialog.show();

            Poem poemObj=new Poem(poem_title.getText().toString(),poem_desc.getText().toString(),poem.getText().toString());

            try{
                db.collection("poems")
                        .add(poemObj.setPoem())
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                resetData();
                                Toast.makeText(PoemAddScreen.this,"Successfully added",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                resetData();
                                Toast.makeText(PoemAddScreen.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
            }catch (Exception e){
                resetData();
                System.out.println("error"+e.getMessage());
            }

        }
    }
    private void resetData(){
        progressDialog.dismiss();
        btnadd_poem.setEnabled(true);
        poem_title.setText("");
        poem_desc.setText("");
        poem.setText("");
    }
}