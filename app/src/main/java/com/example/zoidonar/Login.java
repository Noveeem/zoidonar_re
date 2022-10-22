package com.example.zoidonar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    TextInputLayout tilEmail, tilPassword;
    TextInputEditText etEmail, etPassword;

    Button btnSignup, btnLogin;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //TIL
        tilEmail = (TextInputLayout) findViewById(R.id.tilEmail);
        tilPassword = (TextInputLayout) findViewById(R.id.tilPassword);

        //et
        etEmail = (TextInputEditText) findViewById(R.id.etEmail);
        etPassword = (TextInputEditText) findViewById(R.id.etPassword);

        //Buttons
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignup = (Button) findViewById(R.id.btnSignup);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() !=null){
            Intent main = new Intent(Login.this, MainActivity.class);
            startActivity(main);
            finish();
            return;
        }

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSignup();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateEmail() | !validatePassword()){return;}
                loginUser();
            }
        });
    }

    public void loginUser(){
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            reload();
                        } else {
                            Toast.makeText(Login.this, "Credentials are invalid, try again!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void reload(){
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private boolean validateEmail(){
        String email = etEmail.getText().toString().trim();

        if (email.isEmpty()){
            tilEmail.setError("Field can't be empty!");
            requestFocus(etEmail);
            return false;
        } else {
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                tilEmail.setError("Please provide valid email address!");
                requestFocus(etEmail);
                return false;
            } else {
                tilEmail.setErrorEnabled(false);
            }
        }
        return true;
    }

    private boolean validatePassword(){
        String password = etPassword.getText().toString().trim();

        if (password.isEmpty()){
            tilPassword.setError("Field can't be empty!");
            requestFocus(etPassword);
            return false;
        } else {
           tilPassword.setErrorEnabled(false);
        }
        return true;
    }

    public void clickSignup(){
        Intent signup = new Intent(Login.this, Register.class);
        startActivity(signup);
    }

    public void requestFocus(View view){
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}