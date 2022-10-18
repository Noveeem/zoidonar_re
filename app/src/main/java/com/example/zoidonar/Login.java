package com.example.zoidonar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.*;
import android.os.Bundle;

public class Login extends AppCompatActivity {

    Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSignup = (Button) findViewById(R.id.btnSignup);



        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSignup();
            }
        });
    }

    public void clickSignup(){
        Intent signup = new Intent(Login.this, Register.class);
        startActivity(signup);
    }
}