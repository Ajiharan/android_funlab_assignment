package com.example.funlap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btn_login;
    private EditText email_address,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_login=findViewById(R.id.btn_login);
        email_address=findViewById(R.id.email_address);
        password=findViewById(R.id.password);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

    }
    private void loginUser(){
        if(!email_address.getText().toString().isEmpty() && !password.getText().toString().isEmpty()){
            if(email_address.getText().toString().equals("admin@gmail.com") && password.getText().toString().equals("111111")){
                Intent intent=new Intent(MainActivity.this,AdminHomeScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }else{
                Intent intent=new Intent(MainActivity.this,UserHomeScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        }else{
            Toast.makeText(this,"Some fields are missing",Toast.LENGTH_SHORT).show();
        }

    }
}