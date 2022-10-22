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
import com.google.firebase.database.FirebaseDatabase;

public class Register_third extends AppCompatActivity {

    TextInputEditText etEmailAddress, etPasswordd;
    TextInputLayout tilEmailAddress, tilPasswordd;
    Button btnCreate;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_third);

        //et
        etEmailAddress = (TextInputEditText) findViewById(R.id.etEmailAddress);
        etPasswordd = (TextInputEditText) findViewById(R.id.etPasswordd);

        //TIL
        tilEmailAddress = (TextInputLayout) findViewById(R.id.tilEmailAddress);
        tilPasswordd = (TextInputLayout) findViewById(R.id.tilPasswordd);

        //Button
        btnCreate = (Button) findViewById(R.id.btnCreate);

        mAuth = FirebaseAuth.getInstance();

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateEmail() | !validatePassword()){return;}
                CreateNewUser();
            }
        });

    }

    public void CreateNewUser(){
        Intent i = getIntent();
        String firstName = i.getStringExtra("firstName");
        String lastName = i.getStringExtra("lastName");
        String Address = i.getStringExtra("Address");
        String Dob = i.getStringExtra("Dob");
        String Mobile = i.getStringExtra("Mobile");
        String Age = i.getStringExtra("Age");
        String email = etEmailAddress.getText().toString().trim();
        String password = etPasswordd.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(firstName, lastName, Address, Dob, email, Mobile, Age);
                            FirebaseDatabase.getInstance().getReference("donors")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(Register_third.this, "Success!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(Register_third.this, "Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validateEmail(){
        String email = etEmailAddress.getText().toString().trim();

        if (email.isEmpty()){
            tilEmailAddress.setError("Field can't be empty!");
            requestFocus(etEmailAddress);
            return false;
        } else {
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                tilEmailAddress.setError("Please provide valid email address");
                requestFocus(etEmailAddress);
                return false;
            } else {
                tilEmailAddress.setErrorEnabled(false);
            }
        }
        return true;
    }

    private boolean validatePassword(){
        String password = etPasswordd.getText().toString().trim();

        if (password.isEmpty()){
            tilPasswordd.setError("Field can't be empty!");
            requestFocus(etPasswordd);
            return false;
        } else {
            if (password.length() < 8){
                tilPasswordd.setError("Field can't be empty!");
                requestFocus(etPasswordd);
                return false;
            } else {
                tilPasswordd.setErrorEnabled(false);
            }
        }
        return true;
    }

    public void requestFocus(View view){
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}