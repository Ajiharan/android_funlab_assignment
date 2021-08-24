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
import com.example.funlap.model.Fun;
import com.example.funlap.model.Story;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FunAddScreen extends AppCompatActivity {
    private EditText fun_title,fun_category,fun_desc;
    private Button btnadd_fun,btn_back;
    private FirebaseFirestore db;
    private AwesomeValidation awesomeValidation;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fun_add_screen);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        db=FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(FunAddScreen.this);
        fun_category=findViewById(R.id.fun_category);
        fun_title=findViewById(R.id.fun_title);
        btn_back=findViewById(R.id.btn_back);
        fun_desc=findViewById(R.id.fun_desc);
        btnadd_fun=findViewById(R.id.btnadd_fun);
        btnadd_fun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FunAddScreen.this,AdminHomeScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
    }
    private void addData(){
        if (awesomeValidation.validate()) {
            btnadd_fun.setEnabled(false);
            progressDialog.setTitle("loading...");
            progressDialog.show();

            Fun fun=new Fun(fun_title.getText().toString(),fun_category.getText().toString(),fun_desc.getText().toString());

            try{

                db.collection("funs")
                        .add(fun.setFun())
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                resetData();
                                Toast.makeText(FunAddScreen.this,"Successfully added",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                resetData();
                                Toast.makeText(FunAddScreen.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
            }catch (Exception e){
                resetData();
                System.out.println("error"+e.getMessage());
            }



        }
    }
    private void resetData(){
        btnadd_fun.setEnabled(true);
        progressDialog.dismiss();
        fun_title.setText("");
        fun_category.setText("");
        fun_desc.setText("");
    }
}