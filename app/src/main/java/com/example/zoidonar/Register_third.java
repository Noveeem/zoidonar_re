package com.example.zoidonar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Patterns;
import android.view.LayoutInflater;
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
    Button btnCreate, btnOK;
    ImageView btnClose;

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
                tilEmailAddress.setEnabled(false);
                tilPasswordd.setEnabled(false);
                btnCreate.setEnabled(false);
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
        String status = "Pending";


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(firstName, lastName, Address, Dob, email, Mobile, Age, status);
                            Toast.makeText(Register_third.this, status, Toast.LENGTH_SHORT).show();
                            FirebaseDatabase.getInstance().getReference("donors")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {;
                                            alertSuccess();
                                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    reload();
                                                }
                                            }, 2000);
                                            Toast.makeText(Register_third.this, "Successfully created a new account.", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        } else {
                            alertWarning();
                            Toast.makeText(Register_third.this, "Ooops! Something went wrong, try again later.", Toast.LENGTH_SHORT).show();
                            tilEmailAddress.setEnabled(true);
                            tilPasswordd.setEnabled(true);
                            btnCreate.setEnabled(true);
                        }
                    }
                });


    }
    public void reload(){
        Intent intent = new Intent(Register_third.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }
    public void alertWarning(){
        View alertCustomDialog = LayoutInflater.from(Register_third.this).inflate(R.layout.alert_failed, null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Register_third.this);
        alertDialog.setView(alertCustomDialog);
        final AlertDialog dialog = alertDialog.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        btnOK = (Button) alertCustomDialog.findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btnClose = (ImageView) alertCustomDialog.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    public void alertSuccess(){
        View alertCustomDialog = LayoutInflater.from(Register_third.this).inflate(R.layout.alert_succes, null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Register_third.this);
        alertDialog.setView(alertCustomDialog);
        final AlertDialog dialog = alertDialog.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
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
                tilPasswordd.setError("Password should be more than 8 characters!");
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